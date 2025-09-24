package com.oocl.bootcampbackend.model;

import java.util.List;

public class RouteRequestBody {
    private List<Point> points;
    private int type;

    public RouteRequestBody(List<Point> points, int type) {
        this.points = points;
        this.type = type;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
