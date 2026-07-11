package com.mindScrub.dtos;

import java.time.LocalDateTime;

import com.mindScrub.entities.PostStatus;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

	private int id;
	@NotEmpty(message = "Title cannot be empty")
	@Size(min = 5,max = 100,message = "Title must be between 5 and 100 characters")
	private String title;

	private String url;
	@NotEmpty(message = "Short Description cannot be empty")
	private String shortDesc;
	@NotEmpty(message = "Content cannot be empty")
	private String content;
	
	private PostStatus status;

	private String blogImageName;
	
	private LocalDateTime createAt;
	private LocalDateTime updateAt;
	@NotNull(message = "Please select the category!!")
	private Long categoryId;
	private String categoryName;
}
