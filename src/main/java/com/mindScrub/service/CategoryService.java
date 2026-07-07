package com.mindScrub.service;

import java.util.List;

import com.mindScrub.dtos.CategoryDto;

public interface CategoryService {
	
	CategoryDto addCategory(CategoryDto categoryDto);
	
	List<CategoryDto> getAllCategory();
}
