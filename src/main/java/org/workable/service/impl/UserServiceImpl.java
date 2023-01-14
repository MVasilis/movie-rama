package org.workable.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.workable.CustomUserDetails;
import org.workable.controller.DTO.UserDTO;
import org.workable.entity.User;
import org.workable.mapper.UserMapper;
import org.workable.repository.UserRepository;
import org.workable.service.UserService;
import org.workable.service.exception.DataNotFoundException;
import org.workable.service.exception.ServiceException;

import java.util.List;

/**
 * This is user service implementation
 */
@Service
public class UserServiceImpl implements UserDetailsService, UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		return new CustomUserDetails(user);
	}

	public List<UserDTO> retrieveAllUsers(){
		return userMapper.usersEntityToUserDTOs(userRepo.findAll());
	}

	/**
	 * Save user in database
	 * @param user
	 */
	@Override
	public void save(UserDTO user) {
		try{
			userRepo.save(userMapper.userDTOToUserEntity(user));
		} catch (Exception ex) {
			throw new ServiceException(ex, "Something went wrong while saving user");
		}
	}

	/**
	 * Find user by provided e-mail
	 * @param email
	 * @return
	 */
	@Override
	public User findByEmail(String email) {
		try {
			return userRepo.findByEmail(email);
		} catch (Exception ex) {
			throw new DataNotFoundException("User was not found with e-mail : " + email);
		}
	}

}
