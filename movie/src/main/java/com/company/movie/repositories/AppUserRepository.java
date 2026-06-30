package com.company.movie.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.company.movie.models.User;

@Repository
public interface AppUserRepository extends JpaRepository<User, Integer> {
 public User findByEmail(String email);
}
