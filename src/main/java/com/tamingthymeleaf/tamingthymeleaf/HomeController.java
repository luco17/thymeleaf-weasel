package com.tamingthymeleaf.tamingthymeleaf;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {
    @GetMapping
    public String index(Model model) {
        model.addAttribute("pageTitle", "ThymeWizards");
        model.addAttribute("scientists", List.of("Bohr", "Feynmann", "Pauli", "Dirac", "Heisenberg", "Fermi"));
        return "index";
    }
}
