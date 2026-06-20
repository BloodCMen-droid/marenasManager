package marenas.pe.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import marenas.pe.config.CustomUserDetails;

@Controller
public class UsuarioController {


    @GetMapping("/")
    public String inicio() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/index")
    public String index(Authentication auth, Model model) {

        CustomUserDetails user =
                (CustomUserDetails) auth.getPrincipal();

        model.addAttribute("nombreUsuario",
                user.getNombreEmpleado());

        return "index";
    }

	}


