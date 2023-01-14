package org.movierama.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.workable.controller.DTO.MovieDTO;
import org.workable.controller.DTO.UserDTO;
import org.workable.entity.Movie;
import org.workable.entity.User;
import org.workable.mapper.MovieMapper;
import org.workable.repository.MovieRepository;
import org.workable.service.exception.DataNotFoundException;
import org.workable.service.impl.MovieServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MovieServiceImplTest {

    private static final String TEST_EMAIL = "test@test.com";
    private static final String TEST_NAME = "Test";
    private static final String TEST_LASTNAME = "lastNAme";

    @InjectMocks
    private MovieServiceImpl movieService;
    @Mock
    private MovieRepository movieRepository;
    @Mock
    private MovieMapper movieMapper;


    @Test
    public void whenCallingRetrieveAllMovies_ThenListOfMoviesIsReturned(){
        Page<Movie> moviePage = new PageImpl(initiliazeMovieList());
        when(movieRepository.findAll(isA(Pageable.class))).thenReturn(moviePage);
        when(movieMapper.moviesEntityToMovieDTOs(initiliazeMovieList())).thenReturn(initiliazeMovieDTOList());
        List<MovieDTO> movieDTOS = movieService.retrieveAll(0, 10, "", "");
        Assert.assertNotNull(movieDTOS);
    }

    @Test(expected = DataNotFoundException.class)
    public void whenCallingRetrieveAllMovies_ANDExceptionIsThrow_ThenDataNotFoundExceptionThrown(){
        when(movieRepository.findAll(isA(Pageable.class))).thenThrow(new RuntimeException("Test exception"));
        movieService.retrieveAll(0, 10, "", "");
    }

    @Test
    public void whenFindMovieById_ThenMovieIsReturned(){
        when(movieRepository.findById(1L)).thenReturn(Optional.ofNullable(initiliazeMovie()));
        Optional<Movie> movie = movieService.findMovieById(1L);
        Assert.assertNotNull(movie.get());
    }

    @Test
    public void whenCallingRetrieveAllMoviesByUserId_ThenListOfMoviesIsReturned(){
        when(movieRepository.findAllByUserId(1L)).thenReturn(initiliazeMovieList());
        when(movieMapper.moviesEntityToMovieDTOs(initiliazeMovieList())).thenReturn(initiliazeMovieDTOList());
        List<MovieDTO> movieDTOS = movieService.findAllByUserId("1");
        Assert.assertNotNull(movieDTOS);
    }

    private Movie initiliazeMovie(){
        return Movie.builder().movieTitle("Test").movieDescription("Description").addedBy(new User()).build();
    }

    private List<Movie> initiliazeMovieList(){
        List<Movie> movies = new ArrayList<>();
        movies.add(initiliazeMovie());
        return movies;
    }

    private MovieDTO initiliazeMovieDTO(){
        return MovieDTO.builder().movieTitle("Test").addedBy(initializeUserDTO(1L, TEST_EMAIL, TEST_NAME, TEST_LASTNAME)).build();
    }

    private List<MovieDTO> initiliazeMovieDTOList(){
        List<MovieDTO> movies = new ArrayList<>();
        movies.add(initiliazeMovieDTO());
        return movies;
    }

    private UserDTO initializeUserDTO(Long userId, String email, String name, String lastName){
        return UserDTO.builder().email(email).id(userId).firstName(name).lastName(lastName).build();
    }

}
