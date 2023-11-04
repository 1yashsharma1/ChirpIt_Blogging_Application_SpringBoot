package com.blog.app.services;

import java.util.List;

import com.blog.app.payloads.UserDto;

public interface UserService {

	UserDto createUser(UserDto userDto);

	UserDto updateUser(UserDto userDto, Integer userId);

	UserDto getUserById(Integer userId);

	void deleteUser(Integer userId);

	List<UserDto> getAllUsers();

}
