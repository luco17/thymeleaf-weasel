package com.tamingthymeleaf.application.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {
    User createUser(CreateUserParameters parameters);

    User createAdministrator(CreateUserParameters parameters);

    User editUser(UserId userId, EditUserParameters parameters);

    Page<User> getUsers(Pageable pageable);

    boolean userWithEmailExists(Email email);

    Optional<User> getUser(UserId userId);

    void deleteUser(UserId userId);
}
