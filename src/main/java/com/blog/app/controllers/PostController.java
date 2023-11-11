package com.blog.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.app.payloads.ApiResponse;
import com.blog.app.payloads.PostDto;
import com.blog.app.services.PostService;

@RestController
@RequestMapping("/api/")
public class PostController {

	@Autowired
	private PostService postService;

	@PostMapping("user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId,
			@PathVariable Integer categoryId) {

		PostDto savedPostDto = this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<>(savedPostDto, HttpStatus.CREATED);

	}

	@GetMapping("user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId) {

		List<PostDto> postDtoList = this.postService.getPostByUser(userId);
		return new ResponseEntity<>(postDtoList, HttpStatus.OK);

	}

	@GetMapping("category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId) {

		List<PostDto> postDtoList = this.postService.getPostByCategory(categoryId);
		return new ResponseEntity<>(postDtoList, HttpStatus.OK);

	}

	@GetMapping("/posts")
	public ResponseEntity<List<PostDto>> getAllPost() {

		List<PostDto> allPost = this.postService.getAllPost();
		return new ResponseEntity<>(allPost, HttpStatus.OK);

	}

	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {

		PostDto postDto = this.postService.getPostById(postId);
		return new ResponseEntity<>(postDto, HttpStatus.OK);

	}

	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId) {

		this.postService.deletePost(postId);
		return new ResponseEntity<>(new ApiResponse("Post is Deleted Successfully!!", true), HttpStatus.OK);

	}

	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId) {
		PostDto updatedPost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<>(updatedPost, HttpStatus.OK);
	}

}
