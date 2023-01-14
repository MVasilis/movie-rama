package org.workable.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.workable.common.utils.UserUtils;
import org.workable.common.utils.Utils;
import org.workable.controller.DTO.MovieDTO;
import org.workable.controller.DTO.ReviewDTO;
import org.workable.entity.Movie;
import org.workable.entity.User;
import org.workable.mapper.MovieMapper;
import org.workable.mapper.UserMapper;
import org.workable.repository.MovieRepository;
import org.workable.service.MovieService;
import org.workable.service.UserService;
import org.workable.service.exception.DataNotFoundException;
import org.workable.service.exception.ServiceException;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This is the movie service implementation
 */
@Slf4j
@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private UserUtils userUtils;

    @Autowired
    private UserService userService;

    @Autowired
    private MovieMapper movieMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private Utils utils;

    @Override
    public List<MovieDTO> retrieveAll(Integer pageNo, Integer pageSize, String sortBy, String sorting) {
        try {
            Pageable paging = PageRequest.of(pageNo, pageSize);
            Page<Movie> page = movieRepository.findAll(paging);
            List<MovieDTO> movieDTOs = movieMapper.moviesEntityToMovieDTOs(page.getContent());

            movieDTOs = calculateLikesAndHates(movieDTOs);
            setNameOfUser(movieDTOs);

            return utils.sortMovieList(movieDTOs, sortBy);
        } catch (Exception ex) {
            log.error("Error while retrieving alla movies with error" + ex);
            throw new DataNotFoundException("Something went wrong while retrieving movies");
        }
    }

    @Override
    public void saveMovie(MovieDTO movie) {
        try {
            User user = userService.findByEmail(userUtils.getPrincipal());
            Movie entity = movieMapper.movieDTOtoMovieEntity(movie);

            entity.setAddedBy(user);
            entity.setPublicationDate(new Date());
            movieRepository.save(entity);
        } catch (Exception ex) {
            log.error("Error while saving movie with error {} ", ex.getMessage());
            throw new ServiceException( ex, "Error while saving movie");
        }
    }

    @Override
    public Optional<Movie> findMovieById(Long id) {
        try{
            log.info("Request to retrieve movie with id " + id);
            return movieRepository.findById(id);
        } catch (Exception ex){
            log.error("Error while retrieving movie with error" + ex);
            throw new DataNotFoundException("Requested data not found");
        }

    }

    @Override
    public List<MovieDTO> findAllByUserId(String userId) {
        try{
            List<MovieDTO> movieDTOs = movieMapper.moviesEntityToMovieDTOs(movieRepository.findAllByUserId(Long.parseLong(userId)));
            setNameOfUser(movieDTOs);
            return movieDTOs;
        } catch (Exception ex){
            log.error("Error while retrieving all movies with error" + ex);
            throw new DataNotFoundException("Requested data not found");
        }

    }

    private List<MovieDTO> calculateLikesAndHates(List<MovieDTO> movieDTOs){
        movieDTOs.forEach(movieDTO -> {
            if (!ObjectUtils.isEmpty(movieDTO.getReviews()) && !movieDTO.getReviews().isEmpty()){
                movieDTO.setLikes(calculateLikes(movieDTO.getReviews()));
                movieDTO.setHates(calculateHates(movieDTO.getReviews()));
            } else {
                movieDTO.setLikes(0);
                movieDTO.setHates(0);
            }
        });
        return movieDTOs;
    }

    private Integer calculateLikes(List<ReviewDTO> reviews){
        AtomicInteger sumOfLikes = new AtomicInteger();
        reviews.forEach(review -> {
            if(review.isUserLike()){
                sumOfLikes.getAndIncrement();
            }
        });
        return sumOfLikes.get();
    }

    private Integer calculateHates(List<ReviewDTO> reviews){
        AtomicInteger sumOfHates = new AtomicInteger();
        reviews.forEach(review -> {
            if(review.isUserHate()){
                sumOfHates.getAndIncrement();
            }
        });
        return sumOfHates.get();
    }

    private void setNameOfUser(List<MovieDTO> movieDTOS){
        movieDTOS.forEach(movieDTO -> movieDTO.setNameOfUser(movieDTO.getAddedBy().getFirstName() + " " + movieDTO.getAddedBy().getLastName()));
    }

}
