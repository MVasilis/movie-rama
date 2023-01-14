package org.workable.service;

import org.workable.controller.DTO.UserDTO;
import org.workable.entity.User;

import java.util.List;

/**
 * This is the interface for user service
 */
public interface UserService {

    List<UserDTO> retrieveAllUsers();

    void save(UserDTO user);

    User findByEmail(String email);
}
