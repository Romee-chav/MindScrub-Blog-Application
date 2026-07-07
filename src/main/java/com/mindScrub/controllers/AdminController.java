package com.mindScrub.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mindScrub.dtos.CategoryDto;
import com.mindScrub.exceptions.CategoryAlreadyExistsException;
import com.mindScrub.service.CategoryService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private CategoryService catService;

	@GetMapping("/dashboard")
	public String dashboard() {
		return "admin/dashboard";
	}

	@GetMapping("/dashboard/category")
	public String showCategoryPage(Model model) {
		model.addAttribute("category", new CategoryDto());
		return "admin/category-page";
	}

	@PostMapping("/savedCategory")
	public String saveCat(@ModelAttribute("category") @Valid CategoryDto categoryDto, BindingResult bindingResult,
			Model model, RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("category", categoryDto);
			return "admin/category-page";
		}
		try {
	        catService.addCategory(categoryDto);
	        redirectAttributes.addFlashAttribute("success",
	                "Category Added Successfully!!");

	    } catch (CategoryAlreadyExistsException e) {
	        redirectAttributes.addFlashAttribute("error",
	                e.getMessage());
	    }
		return "redirect:/admin/dashboard/category";
	}

}
