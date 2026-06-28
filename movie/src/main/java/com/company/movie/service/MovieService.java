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
        return movieRepository.findMovieByOrder();
    }
    public List<Movie> findByVendor(int vendorId) {
        return movieRepository.findByVendor(vendorId);
    }
    public List<Movie> search(String query) {
        return movieRepository.search(query);
    }
    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }
    public Movie getMovieById(Integer id) {
        return movieRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Movie not found"));
    }
    public void deleteMovie(Integer id) {
        movieRepository.deleteById(id);
    }

    // public List<Movie> featuresdMovie() {
    //     List<Movie>  features = new ArrayList<>();
    //     return features;
    // }

}
