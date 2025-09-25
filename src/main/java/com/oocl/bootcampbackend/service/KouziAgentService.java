package com.oocl.bootcampbackend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.bootcampbackend.entity.Attraction;
import com.oocl.bootcampbackend.repository.AttractionRepository;
import com.oocl.bootcampbackend.repository.AttractionRepositoryDBImplementation;
import okhttp3.*;
import okhttp3.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;

@Service
public class KouziAgentService {
    @Value("${kouzi.api.url}")
    private String kouziApiUrl;

    @Value("${kouzi.api.key:}")
    private String kouziApiKey;

    @Autowired
    private AttractionRepository attractionRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * 使用OkHttp流式请求Coze智能体API，逐步返回数据
     */
    public void streamChatWithCoze(String botId, String userId, String content, Consumer<String> onData) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String url = kouziApiUrl != null && !kouziApiUrl.isEmpty() ? kouziApiUrl : "https://api.coze.cn/v3/chat?";
        String apiKey = kouziApiKey != null && !kouziApiKey.isEmpty() ? kouziApiKey : "pat_YYDHIzVdqWVzwbLS3HlZITrMHJtEGI2SVOGNqp5gkWDc6GxF7O6KYM90zE4we10p";
        String json = "{" +
                "\"bot_id\": \"" + botId + "\"," +
                "\"user_id\": \"" + userId + "\"," +
                "\"stream\": true," +
                "\"additional_messages\": [" +
                "{\"role\":\"user\",\"type\":\"question\",\"content_type\":\"text\",\"content\":\"" + content + "\"}" +
                "]}";
        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();
        Call call = client.newCall(request);
        Response response = call.execute();
        if (!response.isSuccessful() || response.body() == null) {
            throw new IOException("Unexpected code " + response);
        }
        try (okio.BufferedSource source = response.body().source()) {
            while (!source.exhausted()) {
                String line = source.readUtf8LineStrict();
                onData.accept(line);
            }
        }
    }

    /**
     * 流式请求Coze智能体API，拼接所有content字段并返回完整内容
     */
    public String streamChatWithCozeAndCollectContent(String botId, String userId, String content) throws IOException {
        StringBuilder sb = new StringBuilder();
        ObjectMapper objectMapper = new ObjectMapper();
        streamChatWithCoze(botId, userId, content, line -> {
            try {
                String jsonLine = line;
                // 兼容SSE格式，去掉前缀"data: "（注意有空格）
                if (jsonLine.startsWith("data:")) {
                    jsonLine = jsonLine.substring(5);
                }
                if (jsonLine.trim().isEmpty() || jsonLine.trim().equals("[DONE]")) return;
                JsonNode node = objectMapper.readTree(jsonLine);
                if (!node.has("type") || !node.get("type").asText().equals("answer")) {
                    return; // 忽略心跳包
                }
                if (node.has("content")) {
                    String part = node.get("content").asText();
                    if (part != null && !part.isEmpty()) {
                        sb.append(part);
                    }
                }
            } catch (Exception e) {
                // 忽略解析异常，防止流中非JSON内容导致中断
//                System.out.println("Error parsing line: " + e.getMessage());
            }
        });
        return sb.toString();
    }

    /**
     * 将多个拼接的JSON对象字符串合并为一个Map（如{"day1": [...] }{"day1": [...] }）
     */
    public Map<String, Object> parseAndMergeJsonObjectsForAgentV1(String result) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> merged = new HashMap<>();
        // 用正则分割，提取每个JSON对象
        String[] jsonObjects = result.trim().split("\\}\\s*\\{");
        for (int i = 0; i < jsonObjects.length; i++) {
            String json = jsonObjects[i].trim();
            // 补全被分割掉的括号
            if (!json.startsWith("{")) json = "{" + json;
            if (!json.endsWith("}")) json = json + "}";
            try {
                Map<String, Object> map = objectMapper.readValue(json, Map.class);
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    if (merged.containsKey(key) && value instanceof ArrayList && merged.get(key) instanceof ArrayList) {
                        ((ArrayList) merged.get(key)).addAll((ArrayList) value);
                    } else {
                        merged.put(key, value);
                    }
                }
            } catch (Exception e) {
                // 可选：打印日志
                // e.printStackTrace();
            }
        }
        return merged;
    }

    /**
     * 解析JSON数组字符串并返回Attraction对象列表
     */
    public List<Attraction> parseJsonToAttractionListForAgentV2(String result) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Attraction> attractions = new ArrayList<>();
        try {
            Attraction[] attractionArray = objectMapper.readValue(result, Attraction[].class);
            attractions.addAll(Arrays.asList(attractionArray));
        } catch (Exception e) {
            // 如果解析失败，尝试作为JsonNode处理
            try {
                JsonNode jsonArray = objectMapper.readTree(result);
                if (jsonArray.isArray()) {
                    for (JsonNode item : jsonArray) {
                        Attraction attraction = new Attraction();
                        if (item.has("area")) attraction.setArea(item.get("area").asText());
                        if (item.has("name")) attraction.setName(item.get("name").asText());
                        if (item.has("description")) attraction.setDescription(item.get("description").asText());
                        if (item.has("location")) attraction.setLocation(item.get("location").asText());
                        if (item.has("longitude")) attraction.setLongitude(Double.parseDouble(item.get("longitude").asText()));
                        if (item.has("latitude")) attraction.setLatitude(Double.parseDouble(item.get("latitude").asText()));
                        if (item.has("duration")) {
                            try {
                                attraction.setDuration((int) Double.parseDouble(item.get("duration").asText()));
                            } catch (Exception ex) {
                                attraction.setDuration(0);
                            }
                        }
                        if (item.has("images")) attraction.setImages(item.get("images").asText());
                        attractions.add(attraction);
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return attractions;
    }

    public List<Attraction> getAttractionsFromKouziAgent(String botId, String userId, String prompt) {
        try {
            String result = streamChatWithCozeAndCollectContent(botId, userId, prompt);
            List<Attraction> attractions = parseJsonToAttractionListForAgentV2(result);
            for (Attraction attraction : attractions) {
                List<Attraction> existing = attractionRepository.findByName(attraction.getName());
                if (existing.isEmpty()) {
                    attractionRepository.save(attraction);
                }
            }
            return attractions;
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public Map<String, Object> getItineraryFromKouziAgent(String botId, String userId, String prompt) {
        try {
            String result = streamChatWithCozeAndCollectContent(botId, userId, prompt);
            return parseAndMergeJsonObjectsForAgentV1(result);
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }
    }

    public List<Attraction> getAttractionsFromKouziAgentForService(String area, List<Integer> preference, int days) {
        // id-label 映射
        Map<Integer, String> preferenceLabels = new HashMap<>();
        preferenceLabels.put(1, "小众探索");
        preferenceLabels.put(2, "文化历史");
        preferenceLabels.put(3, "自然风光");
        preferenceLabels.put(4, "美食购物");
        preferenceLabels.put(5, "休闲娱乐");
        preferenceLabels.put(6, "拍照出片");

        // 拼接label字符串
        List<String> labels = new ArrayList<>();
        for (Integer id : preference) {
            if (preferenceLabels.containsKey(id)) {
                labels.add(preferenceLabels.get(id));
            }
        }
        String labelStr = String.join("， ", labels);

        // 拼接prompt
        String prompt = area + days + "日" + labelStr + "类型景点推荐";

        String botId = "7553644953184780303";
        String userId = "1";
        return getAttractionsFromKouziAgent(botId, userId, prompt);
    }
}
