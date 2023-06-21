package com.tamingthymeleaf.application.team;

import com.tamingthymeleaf.application.user.User;
import com.tamingthymeleaf.application.user.UserId;
import com.tamingthymeleaf.application.user.UserNotFoundException;
import com.tamingthymeleaf.application.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class TeamServiceImpl implements TeamService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TeamServiceImpl.class);
    private final TeamRepository repository;
    private final UserService userService;

    public TeamServiceImpl(TeamRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TeamSummary> getTeams(Pageable pageable) {
        return repository.findAllSummary(pageable);
    }

    @Override
    public Team createTeam(String name, User coach) {
        LOGGER.info("Creating team {} with coach {} ({})", name, coach.getUserName().getFullName(), coach.getId());
        return repository.save(new Team(repository.nextId(), name, coach));
    }

    @Override
    public Team createTeam(String name, UserId coachId) {
        User coach = getCoach(coachId);
        return createTeam(name, coach);
    }

    @Override
    public Optional<Team> getTeam(TeamId teamId) {
        return repository.findById(teamId);
    }

    @Override
    public Team editTeam(TeamId teamId, long version, String name, UserId coachId) {
        Team team = getTeam(teamId).orElseThrow(() -> new TeamNotFoundException(teamId));
        if (team.getVersion() != version) {
            throw new ObjectOptimisticLockingFailureException(User.class, team.getId().asString());
        }

        team.setName(name);
        team.setCoach(getCoach(coachId));

        return team;
    }

    @Override
    public void deleteTeam(TeamId teamId) {
        repository.deleteById(teamId);
    }

    @Override
    public void deleteAllTeams() {
        repository.deleteAll();
    }

    private User getCoach(UserId coachId) {
        return userService.getUser(coachId).orElseThrow(() -> new UserNotFoundException(coachId));
    }

}
