package com.soulai.api.dto;

import java.util.UUID;

public class ConflictingEvents {
    private UUID eventId;
    private String conflictingEventIds;

    public ConflictingEvents() {
    }

    public ConflictingEvents(UUID eventId, String conflictingEventIds) {
        this.eventId = eventId;
        this.conflictingEventIds = conflictingEventIds;
    }

    public UUID getEventId() {
        return eventId;
    }

    public String getConflictingEventIds() {
        return conflictingEventIds;
    }
}
