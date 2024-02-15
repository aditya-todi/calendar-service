package com.soulai.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.soulai.api.dto.ConflictingEvents;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@NamedNativeQuery(
        name = "Event.getConflictingEventsByUserId",
        query = """
                with user_invitations as (
                    select
                        event.*
                    from
                        calendar.attendee
                        left join calendar.event on attendee.event_id = event.event_id
                    where
                        user_id = :attendee
                ),
                conflicting_events as (
                    select
                        inv_l.event_id,
                        inv_r.event_id as "conflicting_event_id"
                    from
                        user_invitations inv_l
                        left join user_invitations inv_r on inv_l.event_id != inv_r.event_id
                        and (
                            (
                                inv_r.start_time between inv_l.start_time
                                and inv_l.end_time
                            )
                            or (
                                inv_l.start_time between inv_r.start_time
                                and inv_r.end_time
                            )
                        )
                )
                select
                    event_id as "eventId",
                    string_agg(cast(conflicting_event_id as text), ',') as "conflictingEventIds"
                from
                    conflicting_events
                group by
                    1;
                """,
        resultSetMapping = "Mapping_ConflictingEvents"
)
@SqlResultSetMapping(
        name = "Mapping_ConflictingEvents",
        classes = @ConstructorResult(
                targetClass = ConflictingEvents.class,
                columns = {@ColumnResult(name = "eventId"), @ColumnResult(name = "conflictingEventIds")}
        )
)
@Entity
@Table(name = "event")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "event_id")
    private UUID id;

    private String title;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @OneToMany(targetEntity = Attendee.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private List<Attendee> attendees;

    public Event() {
    }

    public Event(String title, LocalDateTime startTime, LocalDateTime endTime) {
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public List<Attendee> getAttendees() {
        return attendees;
    }
}
