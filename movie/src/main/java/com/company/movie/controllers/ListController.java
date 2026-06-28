package com.company.movie.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.company.movie.models.Movie;
import com.company.movie.service.MovieService;

import org.springframework.ui.Model;

@Controller
public class ListController {

    private final MovieService movieService;

    public ListController(
        MovieService mopService
    ) {
        this.movieService = mopService;
    }
    @GetMapping({"/", "/list"})
    public String listMovies(Model model){
         List<Movie> featured = movieService.findMovies();
         model.addAttribute( "movies", featured);
        return "list";
    }

        @GetMapping("/movieByVendor")
    public String listMoviesByVendor(int vendorId, Model model){
         List<Movie> movies = movieService.findByVendor(vendorId);
         model.addAttribute( "movies", movies);
        return "list";
    }

    @GetMapping("/search")
       public String search(@RequestParam("q") String query, Model model){
         List<Movie> movies = movieService.search(query);
         model.addAttribute( "movies", movies);
        return "list";
    }

    @GetMapping("/moviesDetails/{id}")
       public String viewMovieDetails(@PathVariable Integer id, Model model){
        Movie movie = movieService.getMovieById(id);
         model.addAttribute( "movie", movie);
        return "movie-detail";
    }

}
