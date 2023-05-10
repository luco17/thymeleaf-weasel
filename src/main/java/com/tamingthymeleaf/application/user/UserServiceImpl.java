package com.tamingthymeleaf.application.user;

import com.google.common.collect.ImmutableSet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User createUser(CreateUserParameters parameters) {
        UserId userId = repository.nextId();
        User user = new User(userId,
                parameters.getUserName(),
                parameters.getGender(),
                parameters.getBirthday(),
                parameters.getEmail(),
                parameters.getPhoneNumber());
        return repository.save(user);
    }

    @Override
    public ImmutableSet<User> getAllUsers() {
        return ImmutableSet.copyOf(repository.findAll());
    }
}
