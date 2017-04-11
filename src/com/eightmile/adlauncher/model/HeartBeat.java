package com.eightmile.adlauncher.model;

public class HeartBeat {
	private String type;

    public HeartBeat(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "heartdata{" +
                "type='" + type + '\'' +
                '}';
    }
}
