package com.oocl.bootcampbackend.repository;

import com.oocl.bootcampbackend.entity.Viewpoint;
import com.oocl.bootcampbackend.model.Point;
import com.oocl.bootcampbackend.repository.dao.ViewpointJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ViewpointRepositoryDBImplementation implements ViewpointRepository {

    @Autowired
    private ViewpointJpaRepository viewpointJpaRepository;

    public Viewpoint save(Viewpoint viewpoint) {
        return viewpointJpaRepository.save(viewpoint);
    }

    public List<String> findArea(String area) {
        return viewpointJpaRepository
                .findByAreaContaining(area)
                .stream()
                .map(Viewpoint::getArea)
                .toList();
    }

    public List<String> findAllArea() {
        return viewpointJpaRepository
                .findAll()
                .stream()
                .map(Viewpoint::getArea)
                .distinct()
                .toList();
    }

    public List<Viewpoint> findViewPointsByPreference(int[] preference) {
        return viewpointJpaRepository
                .findAll()
                .stream()
                .filter(viewpoint -> {
                    String prefs = viewpoint.getPreference();
                    for (int pref: preference) {
                        if (prefs.contains(String.valueOf(pref))) {
                            return true;
                        }
                    }
                    return false;
                })
                .toList();
    }

    public List<String> findAllAreaTemp() {
        List<String> areas = new ArrayList<>();
        areas.add("北京");
        areas.add("上海");
        areas.add("安徽");
        areas.add("西安");
        areas.add("成都");
        return areas;
    }

    public String findViewpointByAreaTemp() {
        return "[\n" +
                "  {\n" +
                "    \"name\": \"天安門廣場\",\n" +
                "    \"area\": \"北京\",\n" +
                "    \"location\": \"北京市中心\",\n" +
                "    \"longitude\": 39.5412,\n" +
                "    \"latitude\": 116.2330,\n" +
                "    \"description\": \"天安門廣場是位於中華人民共和國北京市中心的城市廣場，是世界上最大的城市廣場之一。\",\n" +
                "    \"images\": [\n" +
                "      \"https://example.com/image1.jpg\",\n" +
                "      \"https://example.com/image2.jpg\"\n" +
                "    ],\n" +
                "    \"duration\": 3\n" +
                "  },\n" +
                "  {\n" +
                "    \"name\": \"長城\",\n" +
                "    \"area\": \"北京\",\n" +
                "    \"location\": \"八達嶺\",\n" +
                "    \"longitude\": 40.4319,\n" +
                "    \"latitude\": 116.5704,\n" +
                "    \"description\": \"長城是中國最著名的歷史遺跡之一，沿著北方邊界延伸超過13,000英里。\",\n" +
                "    \"images\": [\n" +
                "      \"https://example.com/image3.jpg\",\n" +
                "      \"https://example.com/image4.jpg\"\n" +
                "    ],\n" +
                "    \"duration\": 4\n" +
                "  },\n" +
                "  {\n" +
                "    \"name\": \"故宮博物院\",\n" +
                "    \"area\": \"北京\",\n" +
                "    \"location\": \"北京市中心\",\n" +
                "    \"longitude\": 39.9163,\n" +
                "    \"latitude\": 116.3972,\n" +
                "    \"description\": \"故宮是中國明清兩代的皇宮，擁有豐富的文化和歷史，現為博物館。\",\n" +
                "    \"images\": [\n" +
                "      \"https://example.com/image5.jpg\",\n" +
                "      \"https://example.com/image6.jpg\"\n" +
                "    ],\n" +
                "    \"duration\": 5\n" +
                "  }]";
    }
}
