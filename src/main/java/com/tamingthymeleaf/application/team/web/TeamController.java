package com.tamingthymeleaf.application.team.web;

import com.tamingthymeleaf.application.infrastructure.web.EditMode;
import com.tamingthymeleaf.application.team.Team;
import com.tamingthymeleaf.application.team.TeamId;
import com.tamingthymeleaf.application.team.TeamNotFoundException;
import com.tamingthymeleaf.application.team.TeamService;
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

        teamService.createTeam(formData.getName(), formData.getCoachId());

        return "redirect:/teams";
    }

    @GetMapping("/{id}")
    public String editTeamForm(@PathVariable("id") TeamId teamId, Model model) {
        Team team = teamService.getTeam(teamId).orElseThrow(() -> new TeamNotFoundException(teamId));
        model.addAttribute("team", EditTeamFormData.fromTeam(team));
        model.addAttribute("users", userService.getAllUsersNameAndId());
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
            return "teams/edit";
        }
        Team team = teamService.getTeam(teamId).orElseThrow(() -> new TeamNotFoundException(teamId));

        teamService.editTeam(teamId, formData.getVersion(), formData.getName(), formData.getCoachId());

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
}
