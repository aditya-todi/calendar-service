package com.soulai.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.soulai.api.dto.AvailableSlot;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@NamedNativeQuery(
        name = "User.getAvailableSlots",
        query = """
                with all_used_slots as (
                    select
                        distinct start_time,
                        end_time
                    from
                        calendar.attendee
                        left join calendar.event on attendee.event_id = event.event_id
                    where
                        attendee.user_id in :userIds
                        and event.end_time >= cast(CURRENT_TIMESTAMP as timestamp without time zone)
                    order by
                        start_time
                ),
                slot_rn as (
                    select
                        start_time,
                        end_time,
                        coalesce(
                            lag(end_time, 1) over (
                                order by
                                    start_time
                            ),
                            date_trunc('hour', cast(CURRENT_TIMESTAMP as timestamp without time zone) + interval '1 hour')
                        ) as "prev_slot_end_time",
                        lead(start_time, 1) over (
                            order by
                                start_time
                        ) as "next_slot_start_time"
                    from
                        all_used_slots
                ),
                slot_available_seconds as (
                    select
                        start_time,
                        end_time,
                        prev_slot_end_time,
                        extract(
                            epoch
                            from
                                (end_time - prev_slot_end_time)
                        ) as "available_seconds",
                        case
                            when next_slot_start_time is null then end_time + :minSecs * interval '1 seconds'
                            else null
                        end as "end_slot_available"
                    from
                        slot_rn
                ),
                available_slots as (
                    select
                        case
                            when end_slot_available is null then prev_slot_end_time
                            else end_time
                        end as "startTime",
                        case
                            when end_slot_available is null then start_time
                            else end_slot_available
                        end as "endTime"
                    from
                        slot_available_seconds
                    where
                        available_seconds >= :minSecs
                        or not end_slot_available is null
                )
                select
                    cast("startTime" as text) as "startTime",
                    cast("endTime" as text) as "endTime"
                from
                    available_slots
                limit 5;
                """,
        resultSetMapping = "Mapping_AvailableSlots"
)
@SqlResultSetMapping(
        name = "Mapping_AvailableSlots",
        classes = @ConstructorResult(
                targetClass = AvailableSlot.class,
                columns = {@ColumnResult(name = "startTime"), @ColumnResult(name = "endTime")}
        )
)
@Entity
@Table(name = "user")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private UUID id;

    private String name;

    @Column(name = "email_id", nullable = false)
    private String email;

    @OneToMany(targetEntity = Attendee.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private List<Attendee> invitations;

    public User() {
    }

    public User(UUID id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<Attendee> getInvitations() {
        return invitations;
    }
}
