package com.company.movie.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.company.movie.models.Rol;
import com.company.movie.models.User;
import com.company.movie.repositories.AppUserRepository;
import com.company.movie.repositories.RolRepository;

@Controller
public class AdminController {

@Autowired
private AppUserRepository appUserRepo;

@Autowired
private RolRepository roleRepo;

@GetMapping("admin/dashboard")
public String dashboard(Model model) {
    List<User> users = appUserRepo.findAll();
    List<Rol> roles = roleRepo.findAll();
    model.addAttribute("users", users);
    model.addAttribute("roles", roles);
    return "admin/dashboard";
}

@PostMapping("admin/updateRol")
public String updateRole(
    @RequestParam("userId") Integer userId,
            @RequestParam("rol") Integer rolId

    
){
   User user = appUserRepo.findById(userId).orElse(null);
   Rol newR = roleRepo.findById(rolId).orElse(null);
    if (user != null && newR != null ) {
        user.setRol(newR);
        appUserRepo.save(user);
    }
    return "redirect:/admin/dashboard";
}

// Eliminar usuario
@PostMapping("/admin/deleteUser")
public String deleteUser(
    @RequestParam("userId") Integer userId,
    RedirectAttributes redirectAttributes
) {
    User user = appUserRepo.findById(userId).orElse(null);

    if (user != null) {
        appUserRepo.delete(user);
        redirectAttributes.addFlashAttribute("message", "Usuario eliminado exitosamente");
    } else {
        redirectAttributes.addFlashAttribute("error", "Error al eliminar al usuario.");
    }

    return "redirect:/admin/dashboard";

}

}
