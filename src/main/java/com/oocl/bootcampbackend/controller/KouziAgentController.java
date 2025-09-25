package com.oocl.bootcampbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.bootcampbackend.entity.Attraction;
import com.oocl.bootcampbackend.service.KouziAgentService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/kouzi")
public class KouziAgentController {
    @Autowired
    private KouziAgentService kouziAgentService;

    private static final ObjectMapper mapper = new ObjectMapper();

    // 流式对话接口
    @PostMapping(value = "/streamChat")
    public void streamChat(@RequestBody Map<String, String> request, HttpServletResponse response) throws IOException {
        String botId = request.getOrDefault("botId", "7553289476660428840");
        String userId = request.getOrDefault("userId", "1");
        String content = request.getOrDefault("content", "");
        response.setContentType("text/event-stream;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        kouziAgentService.streamChatWithCoze(botId, userId, content, line -> {
            writer.write("data: " + line + "\n\n");
            writer.flush();
        });
        writer.close();
    }

    @PostMapping("/streamCollect")
    public ResponseEntity<?> chatAndCollect(@RequestBody Map<String, String> request) throws IOException {
        String botId = request.getOrDefault("botId", "7553289476660428840");
        String userId = request.getOrDefault("userId", "1");
        String content = request.getOrDefault("content", "");
        return ResponseEntity.ok(kouziAgentService.getItineraryFromKouziAgent(botId, userId, content));
    }

    @PostMapping("/streamCollectV2")
    public ResponseEntity<?> chatAndCollectV2(@RequestBody Map<String, String> request) throws IOException {
        String botId = request.getOrDefault("botId", "7553644953184780303");
        String userId = request.getOrDefault("userId", "1");
        String content = request.getOrDefault("content", "");
        return ResponseEntity.ok(kouziAgentService.getAttractionsFromKouziAgent(botId, userId, content));
    }

    @PostMapping("/streamCollectV3")
    public ResponseEntity<List<Attraction>> chatAndCollectV3(@RequestBody Map<String, String> request) throws IOException {
        String area = request.getOrDefault("area", "北京");
        List<Integer> preference = Arrays.stream(request.getOrDefault("preference", "1,2,3").split(",")).map(Integer::valueOf).toList();
        int days = Integer.parseInt(request.getOrDefault("days", "2"));
        return ResponseEntity.ok(kouziAgentService.getAttractionsFromKouziAgentForService(area, preference, days));
    }
}

