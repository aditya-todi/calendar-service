package com.soulai.api.dto.request;

import com.soulai.api.model.User;

public class CreateUserRequest {
    private User user;

    public CreateUserRequest() {
    }

    public User getUser() {
        return user;
    }
}
