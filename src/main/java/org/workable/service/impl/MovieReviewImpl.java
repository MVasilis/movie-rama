package org.workable.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.workable.common.ReviewEnum;
import org.workable.common.utils.UserUtils;
import org.workable.entity.Movie;
import org.workable.entity.Review;
import org.workable.entity.User;
import org.workable.repository.ReviewRepository;
import org.workable.service.MovieReviewService;
import org.workable.service.MovieService;
import org.workable.service.UserService;
import org.workable.service.exception.DataNotFoundException;
import org.workable.service.exception.ServiceException;

import java.util.Date;
import java.util.Optional;

/**
 * This is Movie review implementation
 */
@Service
public class MovieReviewImpl implements MovieReviewService {


    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MovieService movieService;

    @Autowired
    private UserUtils userUtils;

    @Autowired
    private UserService userService;

    /**
     * Add movie review by user
     * @param movieId
     * @param userReview
     */
    @Override
    public void addMovieReview(String movieId, String userReview) {
        try {
            Optional<Movie> movie = movieService.findMovieById(Long.parseLong(movieId));
            User user = userService.findByEmail(userUtils.getPrincipal());
            // Validate request
            validateReviewRequest(movie, movieId, user);

            Optional<Review> Entity = reviewRepository.findByMovieIdAndUserId(Long.parseLong(movieId), user.getId());
            // Save or update review
            reviewRepository.save(getEntity(Entity, movie, user, userReview));
        } catch (Exception ex) {
            throw new ServiceException(ex, "Error while creating review");
        }
    }

    private Review getEntity(Optional<Review> Entity,Optional<Movie> movie,User user,  String userReview){
        Review entityReview;
        if (Entity.isPresent()){
            entityReview= Entity.get();
            entityReview.setUserLike(!(entityReview.isUserLike() & userReview.equals(ReviewEnum.LIKE.userReview)) && userReview.equals(ReviewEnum.LIKE.userReview));
            entityReview.setUserHate(!(entityReview.isUserHate() & userReview.equals(ReviewEnum.HATE.userReview)) && userReview.equals(ReviewEnum.HATE.userReview));
        } else {
            entityReview = Review.builder()
                    .movie(movie.get())
                    .user(user)
                    .userHate(userReview.equals(ReviewEnum.HATE.userReview))
                    .userLike(userReview.equals(ReviewEnum.LIKE.userReview))
                    .date(new Date()).build();
        }
        return entityReview;
    }

    private void validateReviewRequest(Optional<Movie> movie, String movieId, User user){
        if (movie.isEmpty()){
            throw new DataNotFoundException("Movie not found for Id " + movieId);
        }

        if (movie.get().getAddedBy().getEmail().equals(user.getEmail())){
            throw new ServiceException("You cannot vote for your own movie ");
        }
    }
}
