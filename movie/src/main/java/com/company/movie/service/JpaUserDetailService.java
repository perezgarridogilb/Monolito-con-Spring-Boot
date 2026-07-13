package com.company.movie.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.company.movie.models.Rol;
import com.company.movie.models.User;
import com.company.movie.repositories.AppUserRepository;

@Service
public class JpaUserDetailService implements UserDetailsService {

    @Autowired
    private AppUserRepository repository;

    @Override
    // @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = repository.findByEmail(username);

        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException(String.format("Username: No existe en el sistema", username));
        }

        User user = userOptional.orElseThrow();
        Rol role = user.getRol();

        // Crear la lista de autoridades manualmente
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (role != null) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.isEnable(),
                true, // accountNonExpired
                true, // credentialsNonExpired
                true, // accountNonLocked
                authorities);
    }

}
