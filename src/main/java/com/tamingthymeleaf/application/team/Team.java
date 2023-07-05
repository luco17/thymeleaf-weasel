package com.tamingthymeleaf.application.team;

import com.tamingthymeleaf.application.user.User;
import io.github.wimdeblauwe.jpearl.AbstractVersionedEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Team extends AbstractVersionedEntity<TeamId> {

    @NotBlank
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private User coach;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TeamPlayer> players;

    /**
     * Default constructor for JPA
     */
    protected Team() {
    }

    public Team(TeamId id, String name, User coach) {
        super(id);
        this.name = name;
        this.coach = coach;
        this.players = new HashSet<>();
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

    public void addPlayer(TeamPlayer player) {
        players.add(player);
        player.setTeam(this);
    }

    public Set<TeamPlayer> getPlayers() {
        return players;
    }

    public void setPlayers(Set<TeamPlayer> players) {
        this.players.clear();
        for (TeamPlayer player : players) {
            addPlayer(player);
        }
    }
}