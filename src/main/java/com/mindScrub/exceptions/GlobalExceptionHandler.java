package com.mindScrub.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CategoryAlreadyExistsException.class)
	public String handleCategoryAlreadyExists(CategoryAlreadyExistsException ex,
			RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("error", ex.getMessage());
		return "redirect:/admin/dashboard/category";
	}

	@ExceptionHandler(CategoryIdNotFoundException.class)
    public String handleCategoryNotFound(
            CategoryIdNotFoundException ex,
            RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("error", ex.getMessage());

        return "redirect:/post/createPost";
    }
	
	@ExceptionHandler(CategoryNotSelectedException.class)
    public String handleCategoryNotSelected(
            CategoryNotSelectedException ex,
            RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("error", ex.getMessage());

        return "redirect:/post/createPost";
    }
	
}
