package com.tamingthymeleaf.application.user;

import io.github.wimdeblauwe.jpearl.AbstractEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tt_user")
public class User extends AbstractEntity<UserId> {

    /**
     * Default constructor for JPA
     */
    protected User() {
    }

    public User(UserId id) {
        super(id);
    }
}