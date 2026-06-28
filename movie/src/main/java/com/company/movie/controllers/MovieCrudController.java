package com.company.movie.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
        return "redirect:/";
    }


}
