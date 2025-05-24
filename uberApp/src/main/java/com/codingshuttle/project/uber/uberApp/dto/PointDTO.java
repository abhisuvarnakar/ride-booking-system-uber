package com.codingshuttle.project.uber.uberApp.dto;

public class PointDTO {

    private double[] coordinates;

    private String type = "Point";

    public PointDTO() {

    }

    public PointDTO(double[] coordinates) {
        this.coordinates = coordinates;
    }

    public double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(double[] coordinates) {
        this.coordinates = coordinates;
    }
}
