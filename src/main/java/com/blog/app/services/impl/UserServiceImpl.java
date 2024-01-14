package com.blog.app.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.app.configs.AppConstants;
import com.blog.app.entities.Role;
import com.blog.app.entities.User;
import com.blog.app.exceptions.ResourceNotFoundException;
import com.blog.app.payloads.RoleDto;
import com.blog.app.payloads.UserDto;
import com.blog.app.repositories.RoleRepo;
import com.blog.app.repositories.UserRepo;
import com.blog.app.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepo roleRepo;

	@Override
	public UserDto createUser(UserDto userDto) {

		User user = this.dtoToUser(userDto);
		user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
		User savedUser = this.userRepo.save(user);

		return this.userToDto(savedUser);

	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		user.setName(userDto.getName());
		user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
		List<RoleDto> rolesDto = userDto.getRoles();
		List<Role> roles = new ArrayList<>();
		rolesDto.stream().forEach(role -> {
			Role newRole = new Role();
			newRole.setId(role.getId());
			newRole.setName(role.getName());
			roles.add(newRole);
		});
		user.setRoles(roles);

		User savedUser = this.userRepo.save(user);

		UserDto userDto2 = this.userToDto(savedUser);

		return userDto2;

	}

	@Override
	public UserDto getUserById(Integer userId) {

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

		UserDto userDto = this.userToDto(user);

		return userDto;

	}

	@Override
	public void deleteUser(Integer userId) {

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

		this.userRepo.deleteById(userId);

	}

	@Override
	public List<UserDto> getAllUsers() {

		List<User> users = this.userRepo.findAll();
		List<UserDto> usersDto = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());

		return usersDto;

	}

	private User dtoToUser(UserDto userDto) {

		User user = this.modelMapper.map(userDto, User.class);

		return user;

	}

	private UserDto userToDto(User user) {

		UserDto userDto = this.modelMapper.map(user, UserDto.class);

		return userDto;

	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {

		User user = this.dtoToUser(userDto);
		// encoded password
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
		user.getRoles().add(role);
		User newUser = this.userRepo.save(user);
		UserDto newUserDto = this.userToDto(newUser);
		return newUserDto;
	}

}
