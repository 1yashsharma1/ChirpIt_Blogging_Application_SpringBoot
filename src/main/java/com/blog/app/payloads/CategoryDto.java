package com.blog.app.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {

	private Integer categoryId;

	@NotEmpty
	@Size(min = 2, max = 100, message = "Title length should be between 2 and 100 characters!!")
	private String categoryTitle;

	@NotEmpty
	@Size(min = 2, max = 200, message = "Description length should be between 2 and 200 characters!!")
	private String categoryDescription;

}
