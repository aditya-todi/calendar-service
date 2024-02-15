package com.soulai.api.dto.request;

import java.util.List;
import java.util.UUID;

public class AvailableSlotsRequest {
    private List<UUID> userIds;
    private Long minutes;

    public AvailableSlotsRequest() {
    }

    public AvailableSlotsRequest(List<UUID> userIds, Long minutes) {
        this.userIds = userIds;
        this.minutes = minutes;
    }

    public List<UUID> getUserIds() {
        return userIds;
    }

    public Long getMinutes() {
        return minutes;
    }
}
