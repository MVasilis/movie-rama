package org.workable.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.workable.controller.DTO.UserDTO;
import org.workable.entity.User;
import org.workable.service.MovieService;
import org.workable.service.UserService;

import java.util.List;

import static org.workable.common.Constant.*;

/**
 * This is user controller
 */
//@RestController("/users")
@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private MovieService movieService;

	@GetMapping("/form")
	public String showRegistrationForm(Model model) {
		model.addAttribute(USER_ATTRIBUTE, new User());
		
		return SIGNUP_FORM;
	}
	
	@PostMapping("/form/process")
	public String processRegister(UserDTO user) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);

		userService.save(user);

		//ResponseEntity.ok().body("{Body}");
		return REGISTER_SUCCESS_PAGE;
	}
	
	@GetMapping
	public String listUsers(Model model) {
		List<UserDTO> listUsers = userService.retrieveAllUsers();
		model.addAttribute(USERS_ATTRIBUTE_LIST, listUsers);

		//ResponseEntity.ok().body("{Body}");
		return USER_PAGE;
	}
}
