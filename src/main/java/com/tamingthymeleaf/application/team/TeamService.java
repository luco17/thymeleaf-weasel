package com.tamingthymeleaf.application.team;

import com.tamingthymeleaf.application.user.UserId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TeamService {
    Page<TeamSummary> getTeams(Pageable pageable);

    Team createTeam(CreateTeamParameters parameters);

    Team editTeam(TeamId teamId, EditTeamParameters parameters);

    Optional<Team> getTeam(TeamId teamId);

    Optional<Team> getTeamWithPlayers(TeamId teamId);

    void deleteTeam(TeamId teamId);

    void deleteAllTeams();

    Team addPlayer(TeamId id, long version, UserId userId, PlayerPosition position);
}
