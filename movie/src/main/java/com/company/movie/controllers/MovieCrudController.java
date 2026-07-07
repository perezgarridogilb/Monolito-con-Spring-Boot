package com.company.movie.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public String saveMovie(
        @Valid Movie movie, 
        BindingResult result, 
        @RequestParam("imageFile") MultipartFile imagFile,
        Model model, 
        RedirectAttributes redirectAttributes
    ) {

        if (result.hasErrors()) {
            model.addAttribute("vendors", vendorService.findVendor());
            redirectAttributes.addFlashAttribute("errorMessage", "Por favor corrige los errores en el formulario");
            return "formMovie";
        }

        if (!imagFile.isEmpty()) {
            String titleFormatted = movie.getName().replaceAll("[^a-zA-Z0-9_]", "_");
            String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            String extension = imagFile.getOriginalFilename() != null
                                ? imagFile.getOriginalFilename().substring(imagFile.getOriginalFilename().lastIndexOf("."))
                                : "";
            String nuevoNombreArchivo = titleFormatted + timestamp + extension;

            String rutaImagenes = "iamges/";
            Path rutaCompleta = Paths.get(rutaImagenes + nuevoNombreArchivo);

            try {
                Files.write(rutaCompleta, imagFile.getBytes());
                movie.setImg(nuevoNombreArchivo);
            } catch (Exception e) {
                e.printStackTrace();
                redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "Errer, vuelve a intentar"
                );
                return "formMovie";
                // TODO: handle exception
            }
        }
// msg de exito
String successMessage = (movie.getId() == null) ? "pelicula creada" : "pelicula actualizada";

        movieService.saveMovie(movie);
        redirectAttributes.addFlashAttribute("successMessage", successMessage);

        return "redirect:/moviesDetails/" + movie.getId();
    }

    @GetMapping("movies/update")
public String updateMovie(
        @Valid Movie movie,
        BindingResult result,
        @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
        Model model,
        RedirectAttributes redirectAttributes){
        // obtener url si ya hay una
        String existingImageUrl = movieService.getMovieById(movie.getId()).getImg();
        if (imageFile == null || imageFile.isEmpty()) {
            movie.setImg(existingImageUrl);
        } else {
            String titleFormatted = movie.getName().replaceAll("[^a-zA-Z0-9_]", "_");
            String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            String extension = imageFile.getOriginalFilename() != null
                    ? imageFile.getOriginalFilename().substring(imageFile.getOriginalFilename().lastIndexOf("."))
                    : "";

            String nuevoNombreArchivo = titleFormatted + timestamp + extension;
            // ruta
            String imageDir = "images/";
            Path fulPath = Paths.get(imageDir + nuevoNombreArchivo);

            try {
                Files.write(fulPath, imageFile.getBytes());
                movie.setImg(nuevoNombreArchivo);
            } catch (Exception e) {
                e.printStackTrace();
                redirectAttributes.addFlashAttribute(
                        "errorMessage",
                        "Error al guardar imagen");
                return "formMovie";

            }

        }

        if (result.hasErrors()) {
            model.addAttribute("vendors", vendorService.findVendor());
            return "formMovie";
        }

        movieService.saveMovie(movie);

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Pelicula actualizada");

        return "redirect:/moviesDetails/" + movie.getId();

    }

    @PostMapping("/movies/delete/{id}")
    public String deleteMovie(@PathVariable Integer id) {
        Movie movie = movieService.getMovieById(id);
        if (movie != null && movie.getImg() != null) {
            String imagePath = "images/" + movie.getImg();
            try {
                Path path = Paths.get(imagePath);
                Files.deleteIfExists(path);
            } catch (Exception e) {
e.printStackTrace();
}
        }
movieService.deleteMovie(id);


return "redirect:/";
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

//     @PostMapping("movies/delete/{id}")  
// public String deleteMovie(@PathVariable Integer id) {
//     movieService.deleteMovie(id);
//     return "redirect:/";
// }


}
