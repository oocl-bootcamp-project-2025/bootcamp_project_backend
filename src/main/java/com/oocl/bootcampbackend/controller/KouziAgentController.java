package com.oocl.bootcampbackend.controller;

import com.oocl.bootcampbackend.entity.Attraction;
import com.oocl.bootcampbackend.service.KouziAgentService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/kouzi")
public class KouziAgentController {
    @Autowired
    private KouziAgentService kouziAgentService;

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
        String result = kouziAgentService.streamChatWithCozeAndCollectContent(botId, userId, content);
        Map<String, Object> stringObjectMap = kouziAgentService.parseAndMergeJsonObjectsForAgentV1(result);
        return ResponseEntity.ok(stringObjectMap);
    }

    @PostMapping("/streamCollectV2")
    public ResponseEntity<?> chatAndCollectV2(@RequestBody Map<String, String> request) throws IOException {
        String botId = request.getOrDefault("botId", "7553644953184780303");
        String userId = request.getOrDefault("userId", "1");
        String content = request.getOrDefault("content", "");
        String result = kouziAgentService.streamChatWithCozeAndCollectContent(botId, userId, content);
        List<Attraction> attractions = kouziAgentService.parseJsonToAttractionListForAgentV2(result);
        return ResponseEntity.ok(attractions);
    }
}

