package com.tamingthymeleaf.application.team.web;

import com.tamingthymeleaf.application.team.Team;

public class EditTeamFormData extends CreateTeamFormData {
    private String id;
    private long version;

    public static EditTeamFormData fromTeam(Team team) {
        EditTeamFormData result = new EditTeamFormData();
        result.setId(team.getId().asString());
        result.setVersion(team.getVersion());
        result.setName(team.getName());
        result.setCoachId(team.getCoach().getId());
        result.setPlayers(team.getPlayers().stream()
                .map(TeamPlayerFormData::fromTeamPlayer)
                .toArray(TeamPlayerFormData[]::new));
        return result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

}
