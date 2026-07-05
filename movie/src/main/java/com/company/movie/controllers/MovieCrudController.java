package com.company.movie.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.company.movie.models.Movie;
import com.company.movie.service.MovieService;
import com.company.movie.service.VendorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;

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
    public String saveMovie(@Valid Movie movie, BindingResult result, Model model, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("vendors", vendorService.findVendor());
            redirectAttributes.addFlashAttribute("errorMessage", "Por favor corrige los errores en el formulario");
            return "formMovie";
        }
// msg de exito
String successMessage = (movie.getId() == null) ? "pelicula creada" : "pelicula actualizada";

        movieService.saveMovie(movie);
        redirectAttributes.addFlashAttribute("successMessage", successMessage);

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
