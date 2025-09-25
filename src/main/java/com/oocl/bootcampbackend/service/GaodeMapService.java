package com.oocl.bootcampbackend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class GaodeMapService {
    @Value("${gaode.api.key}")
    private String gaodeApiKey;

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 通过高德地理编码API将景点名称转为经纬度
     */
    public Map<String, String> getLocationByName(List<String> names) throws IOException {
        Map<String, String> result = new LinkedHashMap<>();
        for (String name : names) {
            String url = "https://restapi.amap.com/v3/geocode/geo?address=" +
                    URLEncoder.encode(name, StandardCharsets.UTF_8) +
                    "&key=" + gaodeApiKey;
            Request request = new Request.Builder().url(url).get().build();
            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    JsonNode root = objectMapper.readTree(response.body().string());
                    JsonNode geocodes = root.get("geocodes");
                    if (geocodes != null && geocodes.isArray() && geocodes.size() > 0) {
                        String location = geocodes.get(0).get("location").asText();
                        result.put(name, location); // location格式: lng,lat
                    }
                }
            }
        }
        return result;
    }

    /**
     * 调用高德驾车路径规划API，获取路线详细信息
     * @param locations 按顺序的经纬度字符串列表（lng,lat）
     * @return 路线规划API返回的JsonNode
     */
    public JsonNode getDrivingRoute(List<String> locations) throws IOException {
        if (locations.size() < 2) throw new IllegalArgumentException("至少需要起点和终点");
        String origin = locations.get(0);
        String destination = locations.get(locations.size() - 1);
        StringBuilder waypoints = new StringBuilder();
        if (locations.size() > 2) {
            for (int i = 1; i < locations.size() - 1; i++) {
                if (waypoints.length() > 0) waypoints.append("|");
                waypoints.append(locations.get(i));
            }
        }
        String url = "https://restapi.amap.com/v3/direction/driving?origin=" + origin +
                "&destination=" + destination +
                (waypoints.length() > 0 ? ("&waypoints=" + waypoints) : "") +
                "&key=" + gaodeApiKey + "&extensions=all";
        Request request = new Request.Builder().url(url).get().build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                return objectMapper.readTree(response.body().string());
            }
        }
        return null;
    }
}

