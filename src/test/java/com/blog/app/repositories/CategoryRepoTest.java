package com.blog.app.repositories;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import com.blog.app.entities.Category;
import com.blog.app.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@DataJpaTest
class CategoryRepoTest {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	CategoryRepo categoryRepo;

	private Category category;
	private Integer existId = 1;
	private Integer nonExistId = 2147483647;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		category = new Category(existId, "sample test title", "sample test description", new ArrayList<>());
	}

	@AfterEach
	void tearDown() throws Exception {
		categoryRepo.deleteAll();
	}

	@Test
	@Sql("classpath:SqlScripts/category_id_reset.sql")
	@Transactional
	void testFindCategoryById_Found() {
		categoryRepo.save(category);

		Category foundCategory = categoryRepo.findById(existId).orElse(null);
		assertNotNull(foundCategory);
		assertEquals(1, foundCategory.getCategoryId());

	}

	@Test
	@Sql("classpath:SqlScripts/category_id_reset.sql")
	@Transactional
	void testFindCategoryById_NotFound() {
		categoryRepo.save(category);
		Category foundCategory = categoryRepo.findById(nonExistId).orElse(null);
		assertNull(foundCategory);

	}

	@Test
	@Sql("classpath:SqlScripts/category_id_reset.sql")
	@Transactional
	void testSaveCategory() {
		Category savedCategory = categoryRepo.save(category);

		assertNotNull(savedCategory);
		assertEquals(1, savedCategory.getCategoryId());

	}

	@Test
	@Sql("classpath:SqlScripts/category_id_reset.sql")
	@Transactional
	void testSaveAndGetCategory() {
		Category savedCategory = categoryRepo.save(category);
		Category getCategory = categoryRepo.findById(existId).orElse(null);

		assertNotNull(savedCategory);
		assertNotNull(getCategory);
		assertEquals(savedCategory.getCategoryId(), getCategory.getCategoryId());

	}

	@Test
	@Sql("classpath:SqlScripts/category_id_reset.sql")
	@Transactional
	void testSaveMultipleCategory() {
		Category savedCategory1 = categoryRepo.save(category);
		Category savedCategory2 = categoryRepo
				.save(new Category(existId + 1, "sample test title", "sample test description", new ArrayList<>()));

		assertNotNull(savedCategory1);
		assertNotNull(savedCategory2);
		assertNotEquals(savedCategory1.getCategoryId(), savedCategory2.getCategoryId());

	}

	@Test
	@Sql("classpath:SqlScripts/category_id_reset.sql")
	@Transactional
	void testUpdateCategory_Success() {
		categoryRepo.save(category);
		String updatedTitle = "Updated Title";

		Category categoryToUpdate = categoryRepo.findById(existId).orElse(null);
		assertNotNull(categoryToUpdate);

		categoryToUpdate.setCategoryTitle(updatedTitle);

		Category updatedCategory = categoryRepo.save(categoryToUpdate);

		Category retrievedCategory = categoryRepo.findById(existId).orElse(null);

		assertNotNull(retrievedCategory);
		assertEquals(updatedTitle, retrievedCategory.getCategoryTitle());

	}

	@Test
	@Sql("classpath:SqlScripts/category_id_reset.sql")
	@Transactional
	void testUpdateCategory_Failure() {
		categoryRepo.save(category);

		Category categoryToUpdate = categoryRepo.findById(nonExistId).orElse(null);

		assertThrows(ResourceNotFoundException.class, () -> {
			categoryRepo.findById(nonExistId).orElseThrow(() -> new ResourceNotFoundException(null, null, 0));
			categoryRepo.save(categoryToUpdate);
		});

	}

	@Test
	@Sql("classpath:SqlScripts/category_id_reset.sql")
	@Transactional
	void testDeleteCategory_Success() {
		categoryRepo.save(category);

		categoryRepo.deleteById(existId);

		assertThrows(ResourceNotFoundException.class, () -> {
			categoryRepo.findById(nonExistId).orElseThrow(() -> new ResourceNotFoundException(null, null, 0));
		});
	}

	@Test
	@Disabled("Disabled because condition is not implemented yet")
	@Sql("classpath:SqlScripts/category_id_reset.sql")
	@Transactional
	void testDeleteCategory_Failure() {
		categoryRepo.save(category);

		assertThrows(ResourceNotFoundException.class, () -> {
			categoryRepo.deleteById(nonExistId);
		});
	}

	@Test
	@Sql("classpath:SqlScripts/category_id_reset.sql")
	@Transactional
	void testGetAllCategory_Success() {
		categoryRepo.save(category);
		Category category2 = new Category(existId + 1, "sample test title1", "sample test description",
				new ArrayList<>());
		categoryRepo.save(category2);

		List<Category> categoryList = categoryRepo.findAll();
		assertEquals(2, categoryList.size());
		assertEquals("sample test title", categoryList.get(0).getCategoryTitle());
		assertEquals("sample test title1", categoryList.get(1).getCategoryTitle());

	}

	@Test
	@Sql("classpath:SqlScripts/category_id_reset.sql")
	@Transactional
	void testGetAllCategory_Failure() {
		categoryRepo.deleteAll();

		List<Category> categoryList = categoryRepo.findAll();
		assertEquals(0, categoryList.size());
	}

}
