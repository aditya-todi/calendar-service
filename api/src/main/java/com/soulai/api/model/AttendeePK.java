package com.soulai.api.model;

import java.util.Objects;
import java.util.UUID;

public class AttendeePK {
    private UUID eventId;
    private UUID userId;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        AttendeePK pk = (AttendeePK) obj;

        return Objects.equals(eventId, pk.eventId) && Objects.equals(userId, pk.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, userId);
    }
}
