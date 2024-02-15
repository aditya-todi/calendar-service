package com.soulai.api.controller;

import com.soulai.api.dto.request.CreateEventRequest;
import com.soulai.api.model.Attendee;
import com.soulai.api.model.Event;
import com.soulai.api.repository.AttendeeRepository;
import com.soulai.api.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/events")
public class EventController {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private AttendeeRepository attendeeRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<Event> getEvents() {
        return this.eventRepository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Event getEventById(@PathVariable String id) {
        return this.eventRepository.findById(UUID.fromString(id)).orElse(null);
    }

    @RequestMapping(method = RequestMethod.POST)
    public List<Event> createEvents(@RequestBody List<CreateEventRequest> request) {
        List<UUID> eventIds = new ArrayList<>();

        for (CreateEventRequest eventRequest : request) {
            Event event = this.eventRepository.save(eventRequest.getEvent());
            eventIds.add(event.getId());
            this.attendeeRepository.saveAll(eventRequest.getAttendees().stream()
                    .map(userId -> new Attendee(event.getId(), userId))
                    .toList()
            );
        }

        return this.eventRepository.findAllById(eventIds);
    }
}
