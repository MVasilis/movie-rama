package org.movierama.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.workable.common.utils.UserUtils;
import org.workable.entity.Movie;
import org.workable.entity.User;
import org.workable.repository.ReviewRepository;
import org.workable.service.MovieService;
import org.workable.service.UserService;
import org.workable.service.exception.ServiceException;
import org.workable.service.impl.MovieReviewImpl;

import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MovieReviewImplTest {

    private static final String TEST_EMAIL = "test@test.com";
    private static final String TEST_EMAIL_2 = "test2@test.com";
    private static final String TEST_NAME = "Test";
    private static final String TEST_LASTNAME = "lastNAme";

    @InjectMocks
    private MovieReviewImpl movieReview;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private MovieService movieService;

    @Mock
    private UserUtils userUtils;

    @Mock
    private UserService userService;

    @Test(expected = ServiceException.class)
    public void whenCallingAddMovieReviewOwnerOfTheMovie_ThenErrorIsShown(){
        when(movieService.findMovieById(1L)).thenReturn(Optional.ofNullable(initiliazeMovie()));
        when(userUtils.getPrincipal()).thenReturn(TEST_EMAIL);
        when(userService.findByEmail(TEST_EMAIL)).thenReturn(initializeUser(1L, TEST_EMAIL, TEST_NAME, TEST_LASTNAME));
        //when(reviewRepository.findByMovieIdAndUserId(1L, 1L)).thenReturn(Optional.ofNullable(null));
        movieReview.addMovieReview("1","like");
    }

    @Test
    public void whenCallingAddMovieReviewFromDifferentUser_ThenReviewIsSaved(){
        when(movieService.findMovieById(1L)).thenReturn(Optional.ofNullable(initiliazeMovie()));
        when(userUtils.getPrincipal()).thenReturn(TEST_EMAIL);
        when(userService.findByEmail(TEST_EMAIL)).thenReturn(initializeUser(1L, TEST_EMAIL_2, TEST_NAME, TEST_LASTNAME));
        when(reviewRepository.findByMovieIdAndUserId(1L, 1L)).thenReturn(Optional.ofNullable(null));
        movieReview.addMovieReview("1","like");
    }

    private Movie initiliazeMovie(){
        return Movie.builder().movieTitle("Test").movieDescription("Description").addedBy(initializeUser(1L, TEST_EMAIL, TEST_NAME, TEST_LASTNAME)).build();
    }

    private User initializeUser(Long userId, String email, String name, String lastName){
        return User.builder().email(email).id(userId).firstName(name).lastName(lastName).build();
    }

}
