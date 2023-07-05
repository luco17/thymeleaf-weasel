package com.tamingthymeleaf.application.team.web;

import com.tamingthymeleaf.application.user.UserId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateTeamFormData {
    @NotBlank(message = "Your team needs a name")
    @Size(max = 100)
    private String name;

    @NotNull
    private UserId coachId;

    @NotNull
    @Size(min = 1)
    @Valid
    private TeamPlayerFormData[] players;

    public CreateTeamFormData() {
        this.players = new TeamPlayerFormData[]{new TeamPlayerFormData()};
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserId getCoachId() {
        return coachId;
    }

    public void setCoachId(UserId coachId) {
        this.coachId = coachId;
    }

    public TeamPlayerFormData[] getPlayers() {
        return players;
    }

    public void setPlayers(TeamPlayerFormData[] players) {
        this.players = players;
    }
}
