package com.soulai.api.dto.request;

import com.soulai.api.model.Event;

import java.util.List;
import java.util.UUID;

public class CreateEventRequest {
    private Event event;
    private List<UUID> attendees;

    public CreateEventRequest() {
    }

    public CreateEventRequest(Event event, List<UUID> attendees) {
        this.event = event;
        this.attendees = attendees;
    }

    public Event getEvent() {
        return event;
    }

    public List<UUID> getAttendees() {
        return attendees;
    }
}
