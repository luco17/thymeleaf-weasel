package com.tamingthymeleaf.application.team;

import com.tamingthymeleaf.application.user.User;
import com.tamingthymeleaf.application.user.UserId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TeamService {
    Page<TeamSummary> getTeams(Pageable pageable);

    Team createTeam(String name, User coach);

    Team createTeam(String name, UserId coachId);

    Optional<Team> getTeam(TeamId teamId);

    Team editTeam(TeamId teamId, long version, String name, UserId coachId);

    void deleteTeam(TeamId teamId);

    void deleteAllTeams();
}
