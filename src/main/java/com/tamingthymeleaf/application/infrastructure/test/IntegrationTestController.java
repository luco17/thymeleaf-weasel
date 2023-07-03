package com.tamingthymeleaf.application.infrastructure.test;

import com.tamingthymeleaf.application.team.TeamService;
import com.tamingthymeleaf.application.user.*;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/integration-test")
@Profile("integration-test")
public class IntegrationTestController {
    private final UserService userService;
    private final TeamService teamService;

    public IntegrationTestController(UserService userService, TeamService teamService) {
        this.userService = userService;
        this.teamService = teamService;
    }

    @PostMapping("/reset-db")
    public void resetDatabase() {
        userService.deleteAllUsers();

        addUser();
        addAdministrator();
    }

    @PostMapping("/add-test-team")
    public void addTestTeam() {
        UserNameAndId userNameAndId = userService.getAllUsersNameAndId().first();
        teamService.createTeam("Test Team", userNameAndId.getId());
    }

    private void addUser() {
        userService.createUser(
                new CreateUserParameters(
                        new UserName("User", "Last"),
                        "user-pwd",
                        Gender.MALE,
                        LocalDate.parse("2010-04-13"),
                        new Email("user.last@gmail.com"),
                        new PhoneNumber("+555 789 456"),
                        UserRole.USER
                )
        );
    }

    private void addAdministrator() {
        userService.createAdministrator(
                new CreateUserParameters(
                        new UserName("Admin", "Strator"),
                        "admin-pwd",
                        Gender.FEMALE,
                        LocalDate.parse("2008-09-25"),
                        new Email("admin.strator@gmail.com"),
                        new PhoneNumber("+555 123 456"),
                        UserRole.ADMIN
                )
        );
    }
}
