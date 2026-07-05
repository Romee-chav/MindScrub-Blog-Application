package com.mindScrub.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/post")
public class PostController {

	@GetMapping("/createPost")
	public String showCreatePost(Model model) {
		model.addAttribute("showHeader", false);
		model.addAttribute("showFooter", false);
		return "common/create-post";
	}
}
