package com.company.movie.controllers;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public String listMovies(Model model, @RequestParam(defaultValue = "0") int page){
        int pageSize = 4;
         Page<Movie> moviePage = movieService.findMovies(PageRequest.of(page, pageSize));
         model.addAttribute( "movies", moviePage.getContent());
                  model.addAttribute( "currentPage", page);
         model.addAttribute( "totalPages", moviePage.getTotalPages());
         model.addAttribute( "vendorId", null);

        return "list";
    }

        @GetMapping("/movieByVendor")
    public String listMoviesByVendor(@RequestParam int vendorId,@RequestParam(defaultValue = "0") int page, Model model){
                int pageSize = 4;

         Page<Movie> moviePage = movieService.findByVendor(vendorId, PageRequest.of(page, pageSize));
         model.addAttribute( "movies", moviePage.getContent());
                  model.addAttribute( "currentPage", page);
         model.addAttribute( "totalPages", moviePage.getTotalPages());
         model.addAttribute( "vendorId", vendorId); // provedor
        return "list";
    }

    @GetMapping("/search")
       public String search(
        @RequestParam("q") String query, 
        @RequestParam(defaultValue = "0") int page,
        Model model
    ){
                            int pageSize = 4;

         Page<Movie> moviePage = movieService.search(query, PageRequest.of(page, pageSize));
                  model.addAttribute( "movies", moviePage.getContent());
                  model.addAttribute( "currentPage", page);
         model.addAttribute( "totalPages", moviePage.getTotalPages());
         model.addAttribute( "query", query); // Para mantener la busqueda en la vista
        return "list";
    }

    @GetMapping("/moviesDetails/{id}")
       public String viewMovieDetails(@PathVariable Integer id, Model model){
        Movie movie = movieService.getMovieById(id);
         model.addAttribute( "movie", movie);
        return "movie-detail";
    }

}
