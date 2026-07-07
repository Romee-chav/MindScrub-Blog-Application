package com.mindScrub.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindScrub.dtos.CategoryDto;
import com.mindScrub.entities.Category;
import com.mindScrub.exceptions.CategoryAlreadyExistsException;
import com.mindScrub.repository.CategoryRepository;
import com.mindScrub.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private CategoryRepository catRepo;

	@Override
	public CategoryDto addCategory(CategoryDto categoryDto) {
		if(catRepo.existsByNameIgnoreCase(categoryDto.getName())) {
			throw new CategoryAlreadyExistsException("Category already exists.");
		}
		Category category = modelMapper.map(categoryDto, Category.class);
		Category savedCategory = catRepo.save(category);
		
		return modelMapper.map(savedCategory, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> categorys = catRepo.findAll();
		List<CategoryDto> allCategorysList = categorys.stream().map(categoryEntity -> modelMapper.map(categoryEntity, CategoryDto.class)).collect(Collectors.toList());
		return allCategorysList;
	}
}
