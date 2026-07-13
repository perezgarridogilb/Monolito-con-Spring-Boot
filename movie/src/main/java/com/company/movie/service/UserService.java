package com.company.movie.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.company.movie.repositories.AppUserRepository;

import com.company.movie.models.User;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private AppUserRepository userrepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = userrepository.findByEmail(email);
        User user = userOptional.orElseThrow();

        // if (user != null) {
        String rol = user.getRol().getName().replace("ROLE_", "");

        var springUser = org.springframework.security.core.userdetails.User.withUsername(user.getFirstName())
                .password(user.getPassword())
                .roles(rol)
                .build();

        return springUser;
        // }

        // throw new UsernameNotFoundException("Not found: " + email);

    }

    public Optional<User> existsByEmail(String email) {
        return userrepository.findByEmail(email);
    }

}
