package org.workable.service;

import org.workable.controller.DTO.MovieDTO;
import org.workable.entity.Movie;

import java.util.List;
import java.util.Optional;

/**
 * This is the interface for movie service
 */
public interface MovieService {

    List<MovieDTO> retrieveAll(Integer pageNo, Integer pageSize, String sortBy, String sorting);

    void saveMovie(MovieDTO movie);

    Optional<Movie> findMovieById(Long id);

    List<MovieDTO> findAllByUserId(String email);
}
