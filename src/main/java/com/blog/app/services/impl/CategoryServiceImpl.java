package com.blog.app.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.app.entities.Category;
import com.blog.app.exceptions.ResourceNotFoundException;
import com.blog.app.payloads.CategoryDto;
import com.blog.app.repositories.CategoryRepo;
import com.blog.app.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {

		Category category = this.dtoToCategory(categoryDto);
		Category savedCategory = this.categoryRepo.save(category);

		return this.categoryToDto(savedCategory);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {

		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));

		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());

		Category savedCategory = this.categoryRepo.save(category);

		return this.categoryToDto(savedCategory);
	}

	@Override
	public CategoryDto getCategoryById(Integer categoryId) {

		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));

		return this.categoryToDto(category);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));

		this.categoryRepo.deleteById(categoryId);

	}

	@Override
	public List<CategoryDto> getAllCategories() {

		List<Category> list = this.categoryRepo.findAll();
		List<CategoryDto> listDto = new ArrayList<>();
		list.stream().forEach(category -> listDto.add(this.categoryToDto(category)));

		return listDto;
	}

	private CategoryDto categoryToDto(Category category) {

		return this.modelMapper.map(category, CategoryDto.class);

	}

	private Category dtoToCategory(CategoryDto categoryDto) {

		return this.modelMapper.map(categoryDto, Category.class);

	}

}
