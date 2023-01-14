package org.movierama.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.workable.controller.MovieController;
import org.workable.service.MovieReviewService;
import org.workable.service.MovieService;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class MovieControllerTest {

    private static final String BASE_URL = "/movies";
    private final MockHttpServletRequest request = new MockHttpServletRequest();
    @InjectMocks
    private MovieController movieController;

    private MockMvc mvc;

    @Mock
    private MovieService movieService;

    @Mock
    private MovieReviewService movieReviewService;

    @Before
    public void before() {
        mvc = MockMvcBuilders.standaloneSetup(movieController)
                .setViewResolvers(viewResolver())
                .build();
        MockitoAnnotations.openMocks(this);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    public void whenGetMoviesIsCalled_ThenResponseOk() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(BASE_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        assertNotNull(result);
    }

    @Test
    public void whenShowAddMovieForm_ThenResponseOk() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/form"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        assertNotNull(result);
    }

    @Test
    public void whenSaveMovieIsCalled_ThenResponseOk() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(BASE_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        assertNotNull(result);
    }

    @Test
    public void whenSaveMovieReviewIsCalled_ThenResponseOk() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/1/review?userReview=like"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        assertNotNull(result);
    }

    @Test
    public void whenRetrieveMoviesPerUserIsCalled_ThenResponseOk() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        assertNotNull(result);
    }


    private ViewResolver viewResolver()
    {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

        viewResolver.setPrefix("classpath:templates/");
        viewResolver.setSuffix(".html");

        return viewResolver;
    }
}
