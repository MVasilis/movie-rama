package org.workable.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.workable.controller.DTO.MovieDTO;
import org.workable.entity.Movie;

import java.util.List;

@Mapper(componentModel = "spring",  uses = {ReviewMapper.class, UserMapper.class})
public interface MovieMapper {

    @Mapping(target = "reviews", source = "reviews")
    //@Mapping(target = "likes", ignore = true)
    List<MovieDTO> moviesEntityToMovieDTOs(List<Movie> source);

    @Mapping(target = "reviews", source = "reviews")
    MovieDTO movieEntityToMovieDTO(Movie source);

    @Mapping(target = "reviews", source = "reviews")
    Movie movieDTOtoMovieEntity(MovieDTO source);

}
