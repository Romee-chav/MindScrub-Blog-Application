package com.mindScrub.serviceImpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindScrub.dtos.PostDto;
import com.mindScrub.entities.Category;
import com.mindScrub.entities.Post;
import com.mindScrub.exceptions.CategoryIdNotFoundException;
import com.mindScrub.exceptions.CategoryNotSelectedException;
import com.mindScrub.repository.CategoryRepository;
import com.mindScrub.repository.PostRepository;
import com.mindScrub.service.PostService;

@Service
public class PostServiceImpl implements PostService{

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PostRepository postRepo;
	
	@Autowired
	private CategoryRepository catRepo;

	@Override
	public PostDto savePost(PostDto postDto) {
		if (postDto.getCategoryId() == null) {
	        throw new CategoryNotSelectedException("Please select a category.");
	    }
		Post postEntity = modelMapper.map(postDto, Post.class);
		postEntity.setStatus("PENDING");
		Category category = catRepo.findById(postDto.getCategoryId()).orElseThrow(()->new CategoryIdNotFoundException("Category Not Id !!"));
		postEntity.setCategory(category);
		Post savedPost = postRepo.save(postEntity);
		return modelMapper.map(savedPost, PostDto.class);
	}	
}
