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
import org.workable.controller.UserController;
import org.workable.service.MovieService;
import org.workable.service.UserService;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    private final MockHttpServletRequest request = new MockHttpServletRequest();

    @InjectMocks
    private UserController userController;
    private MockMvc mvc;

    @Mock
    private UserService userService;

    @Mock
    private MovieService movieService;

    @Before
    public void before() {
        mvc = MockMvcBuilders.standaloneSetup(userController)
                .setViewResolvers(viewResolver())
                .build();
        MockitoAnnotations.openMocks(this);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    public void whenRegisterIsCalled_ThenResponseOk() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/users/form"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        assertNotNull(result);
    }

    @Test
    public void whenProcessRegisterIsCalled_ThenResponseOk() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/users"))
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
