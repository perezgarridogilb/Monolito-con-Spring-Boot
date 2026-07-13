package com.company.movie.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.company.movie.models.User;

@Repository
public interface AppUserRepository extends JpaRepository<User, Integer> {
 Optional<User> findByEmail(String email);
 boolean existsByEmail(String email);
}
