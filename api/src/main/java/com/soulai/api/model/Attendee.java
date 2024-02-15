package com.soulai.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "attendee")
@IdClass(AttendeePK.class)
public class Attendee {
    @Id
    @Column(name = "event_id")
    private UUID eventId;

    @Id
    @Column(name = "user_id")
    private UUID userId;

    // TODO: 'status' field can be used to store the attendee's response to the event
    private String status;

    public Attendee() {
    }

    public Attendee(UUID eventId, UUID userId) {
        this.eventId = eventId;
        this.userId = userId;
    }

    @JsonIgnore
    public UUID getEventId() {
        return eventId;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getStatus() {
        return status;
    }
}
