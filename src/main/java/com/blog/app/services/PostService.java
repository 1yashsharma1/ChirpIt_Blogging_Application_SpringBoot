package com.blog.app.services;

import java.util.List;

import com.blog.app.payloads.PostDto;
import com.blog.app.payloads.PostResponse;

public interface PostService {

	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

	PostDto updatePost(PostDto postDto, Integer postId);

	void deletePost(Integer postId);

	PostDto getPostById(Integer postId);

	PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

	PostResponse getPostByCategory(Integer categoryId, Integer pageNumber, Integer pageSize);

	PostResponse getPostByUser(Integer userId, Integer pageNumber, Integer pageSize);

	List<PostDto> searchByTitle(String keyword);

}
