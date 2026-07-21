package com.mindScrub.serviceImpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mindScrub.dtos.PostDto;
import com.mindScrub.entities.Category;
import com.mindScrub.entities.Post;
import com.mindScrub.entities.PostStatus;
import com.mindScrub.exceptions.CategoryIdNotFoundException;
import com.mindScrub.exceptions.CategoryNotSelectedException;
import com.mindScrub.exceptions.PostIdNotFoundException;
import com.mindScrub.repository.CategoryRepository;
import com.mindScrub.repository.PostRepository;
import com.mindScrub.service.PostService;

@Service
public class PostServiceImpl implements PostService {

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
		postEntity.setStatus(PostStatus.PENDING);
		Category category = catRepo.findById(postDto.getCategoryId())
				.orElseThrow(() -> new CategoryIdNotFoundException("Category Id Not Found !!"));
		postEntity.setCategory(category);
		Post savedPost = postRepo.save(postEntity);
		return modelMapper.map(savedPost, PostDto.class);
	}

	@Override
	public Page<PostDto> showAllPostsForCurrentUser(int pageNo, int size) {

		Pageable pageable = PageRequest.of(pageNo, size, Sort.by("createAt").descending());

		Page<Post> allPost = postRepo.findAll(pageable);

		Page<PostDto> convertedPost = allPost.map(post -> {
			PostDto dto = modelMapper.map(post, PostDto.class);

			if (post.getCategory() != null) {
				dto.setCategoryId(post.getCategory().getId());
				dto.setCategoryName(post.getCategory().getName());
			}

			return dto;
		});

		return convertedPost;
	}

	@Override
	public void approvePost(int postId) {
		Post post = postRepo.findById(postId).orElseThrow(() -> new PostIdNotFoundException("Post Id not found"));
		post.setStatus(PostStatus.APPROVED);
		postRepo.save(post);
	}

	@Override
	public void rejectPost(int postId) {
		Post post = postRepo.findById(postId).orElseThrow(() -> new PostIdNotFoundException("Post id not found"));
		post.setStatus(PostStatus.REJECTED);
		postRepo.save(post);
	}

	@Override
	public PostDto getPostById(int postId) {
		Post post = postRepo.findById(postId).orElseThrow(() -> new PostIdNotFoundException("Post Id not found"));
		PostDto postDto = modelMapper.map(post, PostDto.class);
		return postDto;
	}

	@Override
	public void deletePostById(int postId) {
		Post post = postRepo.findById(postId).orElseThrow(() -> new PostIdNotFoundException("Post Id not found"));
		postRepo.delete(post);
	}

	@Override
	public PostDto updatePost(PostDto postDto) {
		Post existingPost = postRepo.findById(postDto.getId())
				.orElseThrow(() -> new PostIdNotFoundException("Post Id not found"));
		existingPost.setTitle(postDto.getTitle());
		existingPost.setShortDesc(postDto.getShortDesc());
		existingPost.setContent(postDto.getContent());
		existingPost.setBlogImageName(postDto.getBlogImageName());
		Category category = catRepo.findById(postDto.getCategoryId())
				.orElseThrow(() -> new CategoryIdNotFoundException("Category Id Not Found !!"));
		existingPost.setCategory(category);
		Post entity = postRepo.save(existingPost);
		return modelMapper.map(entity, PostDto.class);
	}

}
