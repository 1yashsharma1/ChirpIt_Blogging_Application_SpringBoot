package com.blog.app.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.app.entities.Category;
import com.blog.app.entities.Post;
import com.blog.app.entities.User;
import com.blog.app.exceptions.ResourceNotFoundException;
import com.blog.app.payloads.PostDto;
import com.blog.app.payloads.PostResponse;
import com.blog.app.repositories.CategoryRepo;
import com.blog.app.repositories.PostRepo;
import com.blog.app.repositories.UserRepo;
import com.blog.app.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));

		Post post = this.dtoToPost(postDto);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setCategory(category);
		post.setUser(user);

		Post savedPost = this.postRepo.save(post);

		return this.modelMapper.map(savedPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "PostId", postId));
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		post.setTitle(postDto.getTitle());

		Post updatedPost = this.postRepo.save(post);
		return this.postToDto(updatedPost);
	}

	@Override
	public void deletePost(Integer postId) {

		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "PostId", postId));
		this.postRepo.delete(post);

	}

	@Override
	public PostDto getPostById(Integer postId) {

		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "PostId", postId));

		return this.postToDto(post);
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

		Sort sort = null;
		if (sortDir.equalsIgnoreCase("desc")) {
			sort = Sort.by(sortBy).descending();
		} else {
			sort = Sort.by(sortBy).ascending();
		}

		Pageable page = PageRequest.of(pageNumber, pageSize, sort);

		Page<Post> pageList = this.postRepo.findAll(page);
		List<Post> postList = pageList.getContent();

		List<PostDto> postDtoList = postList.stream().map(post -> this.postToDto(post)).toList();

		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtoList);
		postResponse.setPageNumber(pageList.getNumber());
		postResponse.setPageSize(pageList.getSize());
		postResponse.setTotalElements(pageList.getTotalElements());
		postResponse.setTotalPages(pageList.getTotalPages());
		postResponse.setLastPage(pageList.isLast());

		return postResponse;
	}

	@Override
	public PostResponse getPostByCategory(Integer categoryId, Integer pageNumber, Integer pageSize) {

		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));
		Pageable page = PageRequest.of(pageNumber, pageSize);

		Page<Post> pageList = this.postRepo.findByCategory(category, page);
		List<Post> postList = pageList.getContent();
		List<PostDto> postDtoList = postList.stream().map(post -> this.postToDto(post)).toList();

		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtoList);
		postResponse.setPageNumber(pageList.getNumber());
		postResponse.setPageSize(pageList.getSize());
		postResponse.setTotalElements(pageList.getTotalElements());
		postResponse.setTotalPages(pageList.getTotalPages());
		postResponse.setLastPage(pageList.isLast());

		return postResponse;

	}

	@Override
	public PostResponse getPostByUser(Integer userId, Integer pageNumber, Integer pageSize) {

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "UserId", userId));

		Pageable page = PageRequest.of(pageNumber, pageSize);
		Page<Post> pageList = this.postRepo.findByUser(user, page);
		List<Post> postList = pageList.getContent();
		List<PostDto> postDtoList = postList.stream().map(post -> this.postToDto(post)).toList();

		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtoList);
		postResponse.setPageNumber(pageList.getNumber());
		postResponse.setPageSize(pageList.getSize());
		postResponse.setTotalElements(pageList.getTotalElements());
		postResponse.setTotalPages(pageList.getTotalPages());
		postResponse.setLastPage(pageList.isLast());

		return postResponse;

	}

	@Override
	public List<PostDto> searchByTitle(String keyword) {

		List<Post> postList = this.postRepo.findByTitleContaining(keyword);
		List<PostDto> postDtoList = postList.stream().map(post -> this.postToDto(post)).toList();

		return postDtoList;
	}

	private Post dtoToPost(PostDto postDto) {
		return this.modelMapper.map(postDto, Post.class);
	}

	private PostDto postToDto(Post post) {
		return this.modelMapper.map(post, PostDto.class);
	}

}
