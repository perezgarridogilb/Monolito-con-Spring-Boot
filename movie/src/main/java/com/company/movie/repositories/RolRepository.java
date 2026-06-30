package com.company.movie.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.company.movie.models.Rol; 

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
    Rol findByName(String name);
}
