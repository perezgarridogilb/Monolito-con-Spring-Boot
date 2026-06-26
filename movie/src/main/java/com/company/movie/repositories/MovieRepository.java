package com.company.movie.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.movie.models.Movie;

public interface MovieRepository extends JpaRepository<Movie, Integer>  {

    
}
