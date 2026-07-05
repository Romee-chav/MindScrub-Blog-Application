package com.mindScrub.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
	public String home() {
		return "index";
	}

	@GetMapping("/users/veiwPost")
	public String viewPost(Model model) {
		model.addAttribute("showHeader", true);
		model.addAttribute("showFooter", true);
		return "users/view-post";
	}
	
	@GetMapping("/users/editPost")
	public String editPost(Model model) {
		model.addAttribute("showHeader", false);
		model.addAttribute("showFooter", false);
		return "users/edit-post";
	}
	
	@GetMapping("/users/allPost")
	public String allPost(Model model) {
		model.addAttribute("showHeader", false);
		model.addAttribute("showFooter", false);
		return "users/all-post";
	}
	
	@GetMapping("/users/page-not-found")
	public String notFound(Model model) {
		model.addAttribute("showHeader", false);
		model.addAttribute("showFooter", false);
		return "users/404";
	}
	
	@GetMapping("/auth/login")
	public String login(Model model) {
		model.addAttribute("showHeader", false);
		model.addAttribute("showFooter", true);
		return "/auth/login";
	}
	
	@GetMapping("/auth/register")
	public String signup(Model model) {
		model.addAttribute("showHeader", false);
		model.addAttribute("showFooter", true);
		return "/auth/signup";
	}
}
