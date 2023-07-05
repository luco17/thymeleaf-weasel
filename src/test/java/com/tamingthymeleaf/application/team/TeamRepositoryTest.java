package com.tamingthymeleaf.application.team;

import com.tamingthymeleaf.application.user.User;
import com.tamingthymeleaf.application.user.UserName;
import com.tamingthymeleaf.application.user.UserRepository;
import com.tamingthymeleaf.application.user.Users;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TeamRepositoryTest {
    private final TeamRepository repository;
    private final UserRepository userRepository;
    private final JdbcTemplate jdbcTemplate;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    TeamRepositoryTest(TeamRepository repository,
                       UserRepository userRepository,
                       JdbcTemplate jdbcTemplate) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @BeforeEach
    void validatePreconditions() {
        assertThat(repository.count()).isZero();
    }

    @Test
    void testSaveTeam() {
        User user = userRepository.save(Users.createUser(new UserName("Harlan", "Rees")));

        TeamId id = repository.nextId();
        repository.save(new Team(id, "Initiates", user));

        entityManager.flush();

        assertThat(jdbcTemplate.queryForObject("SELECT id FROM team", UUID.class)).isEqualTo(id.getId());
        assertThat(jdbcTemplate.queryForObject("SELECT name FROM team", String.class)).isEqualTo("Initiates");
        assertThat(jdbcTemplate.queryForObject("SELECT coach_id FROM team", UUID.class)).isEqualTo(user.getId().getId());
    }

    @Test
    void testSaveTeamWithPlayers() {
        User coach = userRepository.save(Users.createUser(new UserName("Coach", "1")));
        User player1 = userRepository.save(Users.createUser(new UserName("Player", "1")));
        User player2 = userRepository.save(Users.createUser(new UserName("Player", "2")));
        User player3 = userRepository.save(Users.createUser(new UserName("Player", "3")));

        TeamId id = repository.nextId();
        Team team = new Team(id, "Initiates", coach);
        team.addPlayer(new TeamPlayer(repository.nextPlayerId(), player1, PlayerPosition.POINT_GUARD));
        team.addPlayer(new TeamPlayer(repository.nextPlayerId(), player2, PlayerPosition.SHOOTING_GUARD));
        team.addPlayer(new TeamPlayer(repository.nextPlayerId(), player3, PlayerPosition.CENTER));

        repository.save(team);

        entityManager.flush();
        entityManager.clear();

        assertThat(repository.findById(id))
                .hasValueSatisfying(team1 -> {
                    assertThat(team1.getId()).isEqualTo(id);
                    assertThat(team1.getCoach().getId()).isEqualTo(coach.getId());
                    assertThat(team1.getPlayers()).hasSize(3);
                });

    }
}