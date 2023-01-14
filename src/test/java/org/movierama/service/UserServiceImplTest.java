package org.movierama.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.workable.controller.DTO.UserDTO;
import org.workable.entity.User;
import org.workable.mapper.UserMapper;
import org.workable.repository.UserRepository;
import org.workable.service.exception.DataNotFoundException;
import org.workable.service.impl.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    private static final String TEST_EMAIL = "test@test.com";
    private static final String TEST_NAME = "Test";
    private static final String TEST_LASTNAME = "lastNAme";

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;

    @Test
    public void givenUserEmail_WhenCallingGetUserByEmail_ThenTheUserIsReturned(){
        when(userRepository.findByEmail(any()))
                .thenReturn(initializeUser(1L, TEST_EMAIL, TEST_NAME, TEST_LASTNAME));

        User user = userService.findByEmail(TEST_EMAIL);
        Assert.assertNotNull(user);
        Assert.assertEquals(TEST_NAME, user.getFirstName());
    }

    @Test(expected = DataNotFoundException.class)
    public void givenUserEmail_WhenCallingGetUserByEmailAndErrorIsThrown_ThenServiceExceptionIsThrown(){
        when(userRepository.findByEmail(any())).thenThrow(new RuntimeException("Test exception"));
        userService.findByEmail(TEST_EMAIL);
    }

    @Test
    public void whenRetrieveAllUsersIsCalled_ThenTheListOfUsersIsReturned(){
        when(userRepository.findAll()).thenReturn(listOfUsers());
        when(userMapper.usersEntityToUserDTOs(listOfUsers())).thenReturn(listOfUserDTOs());
        List<UserDTO> users = userService.retrieveAllUsers();
        Assert.assertNotNull(users);
        Assert.assertEquals(1, users.size());
    }

    private List<User> listOfUsers(){
        List<User> users = new ArrayList<>();
        users.add(initializeUser(1L, TEST_EMAIL, TEST_NAME, TEST_LASTNAME));
        return users;
    }

    private User initializeUser(Long userId, String email, String name, String lastName){
        return User.builder().email(email).id(userId).firstName(name).lastName(lastName).build();
    }

    private List<UserDTO> listOfUserDTOs(){
        List<UserDTO> users = new ArrayList<>();
        users.add(initializeUserDTO(1L, TEST_EMAIL, TEST_NAME, TEST_LASTNAME));
        return users;
    }

    private UserDTO initializeUserDTO(Long userId, String email, String name, String lastName){
        return UserDTO.builder().email(email).id(userId).firstName(name).lastName(lastName).build();
    }
}
