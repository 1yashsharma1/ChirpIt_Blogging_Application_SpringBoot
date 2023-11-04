package com.blog.app.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.blog.app.entities.User;
import com.blog.app.exceptions.ResourceNotFoundException;
import com.blog.app.payloads.UserDto;
import com.blog.app.repositories.UserRepo;
import com.blog.app.services.UserService;

public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDto createUser(UserDto userDto) {

		User user = this.dtoToUser(userDto);
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
		user.setPassword(userDto.getPassword());

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

		User user = new User();

		user.setId(userDto.getId());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		user.setName(userDto.getName());
		user.setPassword(userDto.getPassword());

		return user;

	}

	private UserDto userToDto(User user) {

		UserDto userDto = new UserDto();

		userDto.setId(user.getId());
		userDto.setEmail(user.getEmail());
		userDto.setAbout(user.getAbout());
		userDto.setName(user.getName());
		userDto.setPassword(user.getPassword());

		return userDto;

	}

}
