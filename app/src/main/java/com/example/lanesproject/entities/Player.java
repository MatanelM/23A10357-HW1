package com.example.lanesproject.entities;

public class Player {

    private String name;
    private int score;
    private Double longitude;
    private Double latitude;

    public Player(String name, int score, Double longitude, Double latitude) {
        this.name = name;
        this.score = score;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Player() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString(){
        return "Player{" +
                "name=\"" + this.name +"\"" +
                ", score=" + this.score +
                ", longitude=" + this.longitude +
                ", latitude=" + this.latitude +
                "}";
    }
}
