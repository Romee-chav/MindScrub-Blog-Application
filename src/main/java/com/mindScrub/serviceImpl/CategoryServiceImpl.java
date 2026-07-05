package com.mindScrub.serviceImpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindScrub.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
	@Autowired
	private ModelMapper mapper;
}
