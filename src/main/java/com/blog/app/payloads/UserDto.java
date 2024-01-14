package com.blog.app.payloads;

import java.util.ArrayList;
import java.util.List;

import com.blog.app.entities.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

	static final String PASSOWRDERRORMSG = """
			It contains at least 8 characters and at most 20 characters.
			It contains at least one digit.
			It contains at least one upper case alphabet.
			It contains at least one lower case alphabet.
			It contains at least one special character which includes !@#$%&*()-+=^.
			It doesn’t contain any white space.
			""";

	private int id;

	@NotEmpty
	@Size(min = 2, message = "Name must be 2 or more characters long!!")
	private String name;

	@Email(message = "Email address is not valid!!")
	private String email;

	@NotEmpty
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?!.*\\s).*$", message = PASSOWRDERRORMSG)
	@Size(min = 8, max = 20)
	private String password;

	@NotEmpty
	@Size(min = 4, max = 100, message = "About should be between 4 to 100 characters long")
	private String about;
	
	private List<RoleDto> roles=new ArrayList<>();

}
