package de.darfichraus.dto;

import org.springframework.data.geo.Point;

public class NearGeoData {

    private Point point;
    private int distanceInMeters;

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public int getDistanceInMeters() {
        return distanceInMeters;
    }

    public void setDistanceInMeters(int distanceInMeters) {
        this.distanceInMeters = distanceInMeters;
    }
}
