package com.mindScrub.serviceImpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindScrub.service.PostService;

@Service
public class PostServiceImpl implements PostService{

	@Autowired
	private ModelMapper mapper;
	
	
}
