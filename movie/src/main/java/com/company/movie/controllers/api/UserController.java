package com.company.movie.controllers.api;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.movie.models.Rol;
import com.company.movie.models.User;

import jakarta.validation.Valid;
import com.company.movie.repositories.AppUserRepository;
import com.company.movie.repositories.RolRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Integer id) {
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasFieldErrors()) {
            return validation(result);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(new Date());
        user.setEnable(true);
        if (user.getRol() == null) {
            Rol defaultRol = rolRepository.findByName("ROLE_USER").orElse(null);
            user.setRol(defaultRol);
        }
        User saved = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

    // @PutMapping("/{id}")
    // public ResponseEntity<User> update(@PathVariable Integer id, @RequestBody User user) {
    //     return userRepository.findById(id).map(existing -> {
    //         if (user.getFirstName() != null) existing.setFirstName(user.getFirstName());
    //         if (user.getLastName() != null) existing.setLastName(user.getLastName());
    //         if (user.getEmail() != null) existing.setEmail(user.getEmail());
    //         if (user.getPhone() != null) existing.setPhone(user.getPhone());
    //         if (user.getAddress() != null) existing.setAddress(user.getAddress());
    //         if (user.getUsername() != null) existing.setUsername(user.getUsername());
    //         if (user.getPassword() != null && !user.getPassword().isEmpty()) {
    //             existing.setPassword(passwordEncoder.encode(user.getPassword()));
    //         }
    //         if (user.getRol() != null) {
    //             Rol rol = rolRepository.findById(user.getRol().getId()).orElse(null);
    //             existing.setRol(rol);
    //         }
    //         if (user.isEnable() != existing.isEnable()) {
    //             existing.setEnable(user.isEnable());
    //         }
    //         User saved = userRepository.save(existing);
    //         return ResponseEntity.ok(saved);
    //     }).orElse(ResponseEntity.notFound().build());
    // }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
