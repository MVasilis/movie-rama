package org.workable.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.workable.controller.DTO.MovieDTO;
import org.workable.entity.Movie;
import org.workable.service.MovieReviewService;
import org.workable.service.MovieService;

import java.util.List;

import static org.workable.common.Constant.*;

/**
 * This is the movie controller
 */
//@RestController("/movies")
@Controller
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieReviewService movieReviewService;

    @GetMapping
    public String retrieveAllMovies(Model model,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "100") int size,
                                    @RequestParam(defaultValue = "") String sortBy,
                                    @RequestParam(defaultValue = "") String sorting){
        List<MovieDTO> movies = movieService.retrieveAll(page, size, sortBy, sorting);
        model.addAttribute(MOVIES_LIST_ATTRIBUTE, movies);

        //ResponseEntity.ok().body("{Body}");
        return MOVIES_PAGE;
    }

    @GetMapping("/form")
    public String showAddMovieForm(Model model) {
        model.addAttribute(MOVIE_ATTRIBUTE, new Movie());

        //ResponseEntity.ok().body("{Body}");
        return MOVIE_FORM_PAGE;
    }

    @PostMapping
    public String saveMovie(Model model, MovieDTO movie) {
        movieService.saveMovie(movie);
        List<MovieDTO> movies = movieService.retrieveAll(0, 3, null, null);
        model.addAttribute(MOVIES_LIST_ATTRIBUTE, movies);
        //ResponseEntity.ok().body("{Body}");
        return MOVIES_PAGE;
    }

    @PostMapping("/{movieId}/review")
    public String addMovieReview(Model model,
                                 @PathVariable String movieId,
                                 @RequestParam String userReview) {
        movieReviewService.addMovieReview(movieId, userReview);
        List<MovieDTO> movies = movieService.retrieveAll(0, 3, null, null);
        model.addAttribute(MOVIES_LIST_ATTRIBUTE, movies);
        //ResponseEntity.ok().body("{Body}");
        return MOVIES_PAGE;
    }

    @GetMapping("/{userId}")
    public String retrieveMoviesPerUser(Model model,
                                        @PathVariable String userId) {
        List<MovieDTO> movies = movieService.findAllByUserId(userId);
        model.addAttribute(MOVIES_LIST_ATTRIBUTE, movies);

        //ResponseEntity.ok().body("{Body}");
        return MOVIES_PAGE;
    }
}
