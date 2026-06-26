package com.company.movie.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.company.movie.service.MovieService;

@Controller
public class ListController {

    private final MovieService movieService;

    public ListController(
        MovieService mopService
    ) {
        this.movieService = mopService;
    }

    @GetMapping("/list")
    public String listMovies(){
        return "list";
    }

}
