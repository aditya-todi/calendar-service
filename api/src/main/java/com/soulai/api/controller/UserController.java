package com.soulai.api.controller;

import com.soulai.api.dto.AvailableSlot;
import com.soulai.api.dto.ConflictingEvents;
import com.soulai.api.dto.request.AvailableSlotsRequest;
import com.soulai.api.dto.request.CreateUserRequest;
import com.soulai.api.model.Attendee;
import com.soulai.api.model.Event;
import com.soulai.api.model.User;
import com.soulai.api.repository.AttendeeRepository;
import com.soulai.api.repository.EventRepository;
import com.soulai.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AttendeeRepository attendeeRepository;

    @Autowired
    private EventRepository eventRepository;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUserById(@PathVariable String id) {
        return this.userRepository.findById(UUID.fromString(id)).orElse(null);
    }

    @RequestMapping(method = RequestMethod.POST)
    public User createUser(@RequestBody CreateUserRequest request) {
        return this.userRepository.save(request.getUser());
    }

    @RequestMapping(value = "/{id}/events", method = RequestMethod.GET)
    public List<Event> getAllEventsForUser(@PathVariable String id) {
        List<Attendee> userInvitations = attendeeRepository.findByUserId(UUID.fromString(id));
        return eventRepository.findAllById(userInvitations.stream().map(Attendee::getEventId).toList());
    }

    @RequestMapping(value = "/{id}/conflicting_events", method = RequestMethod.GET)
    public List<ConflictingEvents> getUserConflictingEvents(@PathVariable String id) {
        return eventRepository.getConflictingEventsByUserId(UUID.fromString(id));
    }

    @RequestMapping(value = "/available_slots", method = RequestMethod.GET)
    public List<AvailableSlot> getAvailableSlots(@RequestBody AvailableSlotsRequest request) {
        List<AvailableSlot> availableSlots = userRepository.getAvailableSlots(
                request.getUserIds(),
                request.getMinutes() * 60
        );

        if (availableSlots.size() == 0) {
//        means the set of users dont have any upcoming event
            return Collections.singletonList(
                    new AvailableSlot(
                            LocalDateTime.now().plusMinutes(5).toString(),
                            LocalDateTime.now().plusMinutes(5 + request.getMinutes()).toString()
                    )
            );
        }

        return availableSlots;
    }
}
