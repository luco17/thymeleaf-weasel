package com.tamingthymeleaf.application.team.web;

import com.tamingthymeleaf.application.infrastructure.web.EditMode;
import com.tamingthymeleaf.application.team.*;
import com.tamingthymeleaf.application.user.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/teams")
public class TeamController {

    private final TeamService teamService;
    private final UserService userService;

    public TeamController(TeamService teamService, UserService userService) {
        this.teamService = teamService;
        this.userService = userService;
    }

    @GetMapping
    public String index(Model model, @SortDefault.SortDefaults(@SortDefault("name")) Pageable pageable) {
        model.addAttribute("teams", teamService.getTeams(pageable));
        return "teams/list";
    }

    @GetMapping("/create")
    @Secured("ROLE_ADMIN")
    public String createTeamForm(Model model) {
        model.addAttribute("team", new CreateTeamFormData());
        model.addAttribute("users", userService.getAllUsersNameAndId());
        model.addAttribute("positions", PlayerPosition.values());
        return "teams/edit";
    }

    @PostMapping("/create")
    @Secured("ROLE_ADMIN")
    public String doCreateTeam(@Valid @ModelAttribute("team") CreateTeamFormData formData, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("editMode", EditMode.CREATE);
            model.addAttribute("users", userService.getAllUsersNameAndId());
            return "teams/edit";
        }

        teamService.createTeam(formData.toParameters());

        return "redirect:/teams";
    }

    @GetMapping("/{id}")
    public String editTeamForm(@PathVariable("id") TeamId teamId, Model model) {
        Team team = teamService.getTeamWithPlayers(teamId).orElseThrow(() -> new TeamNotFoundException(teamId));
        model.addAttribute("team", EditTeamFormData.fromTeam(team));
        model.addAttribute("users", userService.getAllUsersNameAndId());
        model.addAttribute("positions", PlayerPosition.values());
        model.addAttribute("editMode", EditMode.UPDATE);
        return "teams/edit";
    }

    @PostMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public String doEditTeam(@PathVariable("id") TeamId teamId,
                             @Valid @ModelAttribute("team") EditTeamFormData formData,
                             BindingResult bindingResult,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("editMode", EditMode.UPDATE);
            model.addAttribute("users", userService.getAllUsersNameAndId());
            model.addAttribute("positions", PlayerPosition.values());
            return "teams/edit";
        }
        Team team = teamService.getTeam(teamId).orElseThrow(() -> new TeamNotFoundException(teamId));

        teamService.editTeam(teamId, formData.toParameters());

        redirectAttributes.addFlashAttribute("editedTeam", team.getName());

        return "redirect:/teams";
    }

    @PostMapping("/{id}/delete")
    @Secured("ROLE_ADMIN")
    public String doDeleteTeam(@PathVariable("id") TeamId teamId, RedirectAttributes redirectAttributes) {
        Team team = teamService.getTeam(teamId).orElseThrow(() -> new TeamNotFoundException(teamId));

        teamService.deleteTeam(teamId);

        redirectAttributes.addFlashAttribute("deletedTeamName", team.getName());

        return "redirect:/teams";
    }

    @GetMapping("/edit-teamplayer-fragment")
    @Secured("ROLE_ADMIN")
    public String getEditTeamPlayerFragment(Model model, @RequestParam("index") int index) {
        model.addAttribute("index", index);
        model.addAttribute("users", userService.getAllUsersNameAndId());
        model.addAttribute("positions", PlayerPosition.values());
        model.addAttribute("teamObjectName", "dummyTeam");
        model.addAttribute("dummyTeam", new DummyTeamForTeamPlayerFragment());
        return "teams/edit-teamplayer-fragment :: teamplayer-form";
    }

    private static class DummyTeamForTeamPlayerFragment {
        private TeamPlayerFormData[] players;

        public TeamPlayerFormData[] getPlayers() {
            return players;
        }

        public void setPlayers(TeamPlayerFormData[] players) {
            this.players = players;
        }
    }
}
