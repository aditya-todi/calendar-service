package com.soulai.api.dto;

public class AvailableSlot {
    private String startTime;
    private String endTime;

    public AvailableSlot() {
    }

    public AvailableSlot(String startTime, String endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }
}
