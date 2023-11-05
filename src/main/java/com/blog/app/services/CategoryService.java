package com.blog.app.services;

import java.util.List;

import com.blog.app.payloads.CategoryDto;

public interface CategoryService {

	CategoryDto createCategory(CategoryDto categoryDto);

	CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);

	CategoryDto getCategoryById(Integer categoryId);

	void deleteCategory(Integer categoryId);

	List<CategoryDto> getAllCategories();

}
