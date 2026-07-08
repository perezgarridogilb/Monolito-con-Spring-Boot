package com.company.movie.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.movie.models.Genre;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
 

}
