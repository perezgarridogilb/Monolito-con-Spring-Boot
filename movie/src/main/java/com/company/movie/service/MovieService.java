package com.company.movie.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.company.movie.models.Movie;
import com.company.movie.repositories.MovieRepository;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }


    public List<Movie> findMovies() {
        return movieRepository.findAll();
    }

    // public List<Movie> featuresdMovie() {
    //     List<Movie>  features = new ArrayList<>();
    //     return features;
    // }

}
