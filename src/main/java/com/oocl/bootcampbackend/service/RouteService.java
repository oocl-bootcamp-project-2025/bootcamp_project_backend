package com.oocl.bootcampbackend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.oocl.bootcampbackend.controller.dto.RouteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class RouteService {
    @Autowired
    KouziAgentService kouziAgentService;
    @Autowired
    GaodeMapService gaodeMapService;

    public Object processRoute(RouteDTO routeData) throws IOException {
        String content = routeData.getDestination() + routeData.getDuration() + "天推荐景点有哪些?";
        String result = kouziAgentService.streamChatWithCozeAndCollectContent("7553289476660428840", "1", content);
        Map<String, Object> stringObjectMap = kouziAgentService.parseAndMergeJsonObjects(result);

        // 按key顺序遍历，收集所有景点名称
        List<String> nameList = new ArrayList<>();
        for (String key : stringObjectMap.keySet()) {
            Object value = stringObjectMap.get(key);
            if (value instanceof List) {
                List<?> attractions = (List<?>) value;
                for (Object item : attractions) {
                    if (item instanceof Map) {
                        Map<?, ?> itemMap = (Map<?, ?>) item;
                        Object nameObj = itemMap.get("name");
                        if (nameObj != null) {
                            nameList.add(nameObj.toString());
                        }
                    }
                }
            }
        }
        Map<String, String> locationByName = gaodeMapService.getLocationByName(nameList);
        // 保持顺序，将nameList映射为对应的经纬度list
        List<String> locationList = new ArrayList<>();
        for (String name : nameList) {
            String loc = locationByName.get(name);
            if (loc != null) {
                locationList.add(loc);
            }
        }
        // 你可以在这里继续调用高德驾车路线API，或直接返回locationList
        JsonNode drivingRoute = gaodeMapService.getDrivingRoute(locationList);
        return Map.of("attractions", stringObjectMap, "drivingRoute", drivingRoute);
    }
}
