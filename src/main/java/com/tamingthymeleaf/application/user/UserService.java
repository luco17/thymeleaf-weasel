package com.tamingthymeleaf.application.user;

import com.google.common.collect.ImmutableSet;

public interface UserService {
    User createUser(CreateUserParameters parameters);

    public ImmutableSet<User> getAllUsers();
}
