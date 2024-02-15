package com.soulai.api.repository;

import com.soulai.api.dto.ConflictingEvents;
import com.soulai.api.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {
    @Query(nativeQuery = true)
    List<ConflictingEvents> getConflictingEventsByUserId(@Param(value = "attendee") UUID userId);
}
