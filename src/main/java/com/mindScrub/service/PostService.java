package com.mindScrub.service;

import org.springframework.data.domain.Page;

import com.mindScrub.dtos.PostDto;

public interface PostService {
	PostDto savePost(PostDto postDto);
	
	// all Post
	Page<PostDto> showAllPostsForCurrentUser(int pageNo, int size); // Used to get all posts based on users and admin role
	
	// Status
	void approvePost(int postId);
	void rejectPost(int postId);
	
	PostDto getPostById(int postId);
	
	void deletePostById(int postId);
	
	PostDto updatePost(PostDto postDto);
}
