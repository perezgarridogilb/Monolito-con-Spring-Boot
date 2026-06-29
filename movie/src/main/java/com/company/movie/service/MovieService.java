package com.company.movie.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.company.movie.models.Movie;
import com.company.movie.repositories.MovieRepository;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }


    public Page<Movie> findMovies(Pageable pageable) {
        return movieRepository.findMovieByOrder(pageable);
    }
    public Page<Movie> findByVendor(int vendorId, Pageable pageable) {
        return movieRepository.findByVendor(vendorId, pageable);
    }
    public Page<Movie> search(String query, Pageable pageable) {
        return movieRepository.search(query, pageable);
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
