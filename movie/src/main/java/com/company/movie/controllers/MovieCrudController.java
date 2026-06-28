package com.company.movie.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.company.movie.models.Movie;
import com.company.movie.service.MovieService;
import com.company.movie.service.VendorService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MovieCrudController {

    private final VendorService vendorService;
    private final MovieService movieService;

    @GetMapping("/movies/create")
    public String showFormMovie(Model model) {
        model.addAttribute("vendors", vendorService.findVendor());
        model.addAttribute("movie", new Movie());
        return "formMovie";
    }
    @PostMapping("/movies/save")
    public String saveMovie(Movie movie) {
        movieService.saveMovie(movie);
        return "redirect:/moviesDetails/" + movie.getId();
    }

    @GetMapping("movies/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        Movie movie = movieService.getMovieById(id);
        if (movie == null) {
            throw new IllegalArgumentException("Movie not found" + id);
        }
                model.addAttribute("vendors", vendorService.findVendor());

    model.addAttribute("movie", movie);  // <--- esto falta

        return "formMovie";
    }

    @PostMapping("movies/delete/{id}")  
public String deleteMovie(@PathVariable Integer id) {
    movieService.deleteMovie(id);
    return "redirect:/";
}


}
