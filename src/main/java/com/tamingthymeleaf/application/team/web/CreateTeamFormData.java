package com.tamingthymeleaf.application.team.web;

import com.tamingthymeleaf.application.team.CreateTeamParameters;
import com.tamingthymeleaf.application.team.TeamPlayerParameters;
import com.tamingthymeleaf.application.user.UserId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

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

    public CreateTeamParameters toParameters() {
        return new CreateTeamParameters(name, coachId, getTeamPlayerParameters());
    }

    @Nonnull
    protected Set<TeamPlayerParameters> getTeamPlayerParameters() {
        return Arrays.stream(players)
                .map(teamPlayerFormData -> new TeamPlayerParameters(
                        teamPlayerFormData.getPlayerId(),
                        teamPlayerFormData.getPosition())
                )
                .collect(Collectors.toSet());
    }

    public void removeEmptyTeamPlayerForms() {
        setPlayers(Arrays.stream(players)
                .filter(this::isNotEmptyTeamPlayerForm)
                .toArray(TeamPlayerFormData[]::new));
    }

    private boolean isNotEmptyTeamPlayerForm(TeamPlayerFormData formData) {
        return formData != null
                && formData.getPlayerId() != null
                && formData.getPosition() != null;
    }
}
