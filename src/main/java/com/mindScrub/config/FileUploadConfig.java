package com.mindScrub.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FileUploadConfig implements WebMvcConfigurer {

	private static final String UPLOAD_DIR = "blogFiles";

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		Path uploadPath = Paths.get(UPLOAD_DIR);
		registry.addResourceHandler("/blogFiles/**")
        .addResourceLocations("file:" + uploadPath + "/");
	}

}
