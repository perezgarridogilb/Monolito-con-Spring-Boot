package com.company.movie.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.company.movie.dto.RegisterDTO;
import com.company.movie.models.Rol;
import com.company.movie.models.User;
import com.company.movie.repositories.AppUserRepository;
import com.company.movie.repositories.RolRepository;

import jakarta.validation.Valid;

@Controller
public class AccountController {

    @Autowired
    private AppUserRepository userrepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String register(Model model) {
        RegisterDTO registerDTO = new RegisterDTO();
model.addAttribute("registerDto", new RegisterDTO());
        model.addAttribute("success", false);
        return "register";
    }


        @PostMapping("/register")
    public String register(Model model, @Valid @ModelAttribute RegisterDTO registerDto, BindingResult result) {
 
        if (!registerDto.getPassword().equals(registerDto.getConfirmPassword())) {
            result.addError(
                new FieldError("registerDto", "confirmPassword", "Password and Confirm Password do not match")
            );
 }
 
            if (result.hasErrors()) {
                return "register";
            }

            try {
                Rol userRol = rolRepository.findByName("ROLE_USER");

                if (userRol == null) {
                    throw new RuntimeException("Role 'ROLE_USER' not found in database");
                }

                User newUser = new User();
                newUser.setFirstName(registerDto.getFirstName());
                newUser.setLastName(registerDto.getLastName());
                newUser.setEmail(registerDto.getEmail());
                newUser.setPhone(registerDto.getPhone());
                newUser.setAddress(registerDto.getAddress());
                newUser.setCreatedAt(new Date());

                newUser.setPassword(passwordEncoder.encode(registerDto.getPassword()));

                userrepository.save(newUser);
                
                model.addAttribute("registerDto", new RegisterDTO());
                model.addAttribute("success", true);
            } catch (IllegalArgumentException e) {
                result.addError(new FieldError("registerDto", "firstName", e.getMessage()));
            }

       
        return "register";
    }

}
