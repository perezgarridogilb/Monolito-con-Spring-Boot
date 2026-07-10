package com.company.movie.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.company.movie.models.Genre;
import com.company.movie.service.GenreService;

@Controller
public class GenreController {
    @Autowired
    private GenreService genreService;

    @GetMapping("/genres/list")
    public String listGenres(Model model) {
        model.addAttribute("genres", genreService.findAll());
        return "genres/list";
    }
    @GetMapping("/genres/add")
    public String showAddGenreForm(Model model) {
        model.addAttribute("genres", new Genre());
        return "genres/add";
    }

    @PostMapping("/genres/add")
public String saveGenre(@ModelAttribute Genre genre, RedirectAttributes redirectAttributes) {
    genreService.save(genre);
    redirectAttributes.addFlashAttribute("message", "Género agregado");
    return "redirect:/genres/list";
}

    @GetMapping("/genres/edit/{id}")
    public String showEditGenreForm(@PathVariable Integer id, Model model) {
        Genre genre = genreService.findById(id).orElse(null);
        model.addAttribute("genre", genre);
                return "/genres/edit";

    }

    @PostMapping("/genres/edit/{id}")
    public String editGenre(@PathVariable Integer id, @ModelAttribute Genre genre, RedirectAttributes redirectAttributes) {
        genre.setId(id);
        genreService.save(genre);
        redirectAttributes.addFlashAttribute("message","genero actualizado");
                return "redirect:/genres/list";

    }

    @PostMapping("/genres/delete/{id}")
    public String deleteGenre(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        genreService.deleteById(id);
        redirectAttributes.addFlashAttribute("message","genero actualizado");
        return "redirect:/genres/list";
    }

}
