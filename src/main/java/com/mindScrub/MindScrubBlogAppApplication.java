package com.mindScrub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MindScrubBlogAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(MindScrubBlogAppApplication.class, args);
	}

}
