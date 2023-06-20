package com.tamingthymeleaf.application.team;

import com.tamingthymeleaf.application.user.User;
import io.github.wimdeblauwe.jpearl.AbstractVersionedEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Team extends AbstractVersionedEntity<TeamId> {

    @NotBlank
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private User coach;

    /**
     * Default constructor for JPA
     */
    protected Team() {
    }

    public Team(TeamId id, String name, User coach) {
        super(id);
        this.name = name;
        this.coach = coach;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getCoach() {
        return coach;
    }

    public void setCoach(User coach) {
        this.coach = coach;
    }
}