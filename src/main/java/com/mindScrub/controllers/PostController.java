package com.mindScrub.controllers;

import java.io.File;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mindScrub.dtos.CategoryDto;
import com.mindScrub.dtos.PostDto;
import com.mindScrub.service.CategoryService;
import com.mindScrub.service.PostService;
import com.mindScrub.utils.Convert;
import com.mindScrub.utils.FileUpload;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/post")
@Slf4j
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private CategoryService catService;

	@GetMapping("/createPost")
	public String showCreatePost(Model model) {
		model.addAttribute("showHeader", false);
		model.addAttribute("showFooter", false);
		List<CategoryDto> allCategories = catService.getAllCategory();
		PostDto postDto = new PostDto();
		model.addAttribute("post", postDto);
		model.addAttribute("categories", allCategories);
		return "common/create-post";
	}

	@PostMapping("/saved")
	public String savePost(@Valid @ModelAttribute("post") PostDto postDto, BindingResult bindingResult,
			RedirectAttributes redirectAttributes, @RequestParam("image") MultipartFile imagePart, Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("showHeader", false);
			model.addAttribute("showFooter", false);
			model.addAttribute("post", postDto);
			return "common/create-post";
		}

		// Validate image type
		if (!imagePart.isEmpty()) {

			String contentType = imagePart.getContentType();

			if (!("image/jpeg".equals(contentType) || "image/png".equals(contentType))) {
				model.addAttribute("showHeader", false);
				model.addAttribute("showFooter", false);
				List<CategoryDto> allCategories = catService.getAllCategory();
				model.addAttribute("post", postDto);
				model.addAttribute("categories", allCategories);
				model.addAttribute("error", "Only JPG, JPEG and PNG images are allowed.");
				return "common/create-post";
			}

			String fileName = imagePart.getOriginalFilename();
			String extension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();

			if (!(extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png"))) {
				model.addAttribute("showHeader", false);
				model.addAttribute("showFooter", false);
				model.addAttribute("post", postDto);
				model.addAttribute("error", "Only JPG, JPEG and PNG images are allowed.");
				return "common/create-post";
			}
		}

		// Convert Title to URL
		String convertedTitle = Convert.titleToUrl(postDto.getTitle());
		postDto.setUrl(convertedTitle);

		String imageName = "";
		if (!imagePart.isEmpty()) {
			imageName = StringUtils.cleanPath(Objects.requireNonNull(imagePart.getOriginalFilename()));
			postDto.setBlogImageName(imageName);
		} else {
			redirectAttributes.addFlashAttribute("error", "please upload the image!!!");
			return "redirect:/post/createPost";
		}

		PostDto savedPostDto = postService.savePost(postDto);

		if (savedPostDto != null) {
			String uploadDirectory = "blogFiles/" + savedPostDto.getId();
			boolean isImageSaved = FileUpload.save(uploadDirectory, imageName, imagePart);

			if (isImageSaved) {
				redirectAttributes.addFlashAttribute("success", "Blog Added!");
			} else {
				redirectAttributes.addFlashAttribute("error", "Image could not be saved.");
			}
		} else {
			redirectAttributes.addFlashAttribute("error", "Something went wrong.");
		}

		return "redirect:/post/createPost";
	}

	@GetMapping("/allpost")
	public String allPost(@RequestParam(defaultValue = "0") int page, Model model) {
		int size = 4;
		Page<PostDto> allPostsForCurrentUser = postService.showAllPostsForCurrentUser(page, size);
		model.addAttribute("showHeader", false);
		model.addAttribute("showFooter", false);
		model.addAttribute("allPostsForCurrentUser", allPostsForCurrentUser);
		model.addAttribute("currentPage", page);
		return "common/all-post";
	}

	@GetMapping("/veiwPostData")
	public String viewPost(@RequestParam("postId") int postId, Model model) {
		PostDto postDto = postService.getPostById(postId);
		model.addAttribute("showHeader", true);
		model.addAttribute("showFooter", true);
		model.addAttribute("post", postDto);
		return "common/view-post";
	}

	@GetMapping("/deletePost")
	public String deletePost(@RequestParam("postId") int postId) {
		String folderPath = "blogFiles/" + postId;
		File file = new File(folderPath);
		if (file.exists()) {
			for (File f : file.listFiles()) {
				f.delete();
			}
			file.delete();
		}
		postService.deletePostById(postId);
		return "redirect:/post/allpost";
	}

	@GetMapping("/editPost")
	public String editPost(@RequestParam("postId") int postId, Model model) {
		List<CategoryDto> categories = catService.getAllCategory();
		PostDto postDto = postService.getPostById(postId);
		model.addAttribute("showHeader", false);
		model.addAttribute("showFooter", false);
		model.addAttribute("post", postDto);
		model.addAttribute("categories", categories);
		return "common/edit-post";
	}

	@PostMapping("/update")
	public String updatePost(@Valid @ModelAttribute("post") PostDto postDto, BindingResult bindingResult,
			RedirectAttributes redirectAttributes, @RequestParam("image") MultipartFile imagePart, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("showHeader", false);
			model.addAttribute("showFooter", false);
			model.addAttribute("post", postDto);
			return "common/edit-post";
		}
		if (imagePart != null && !imagePart.isEmpty()) {
			if (postDto.getBlogImageName() != null) {
				String oldImagePath = "blogFiles/" + postDto.getId() + "/" + postDto.getBlogImageName();
				File oldImageFile = new File(oldImagePath);
				if (oldImageFile.exists()) {
					log.info("Old Image is deleted !!");
					oldImageFile.delete();
				}
			}

			String newImageName = StringUtils.cleanPath(Objects.requireNonNull(imagePart.getOriginalFilename()));
			postDto.setBlogImageName(newImageName);
			String uploadDirectory = "blogFiles/" + postDto.getId();
			boolean isImageSaved = FileUpload.save(uploadDirectory, newImageName, imagePart);

			if (isImageSaved) {
				redirectAttributes.addFlashAttribute("success", "Blog Added!");
			} else {
				redirectAttributes.addFlashAttribute("error", "Image could not be saved.");
			}

		}
		PostDto updatePost = postService.updatePost(postDto);
		if(updatePost != null) {
			redirectAttributes.addFlashAttribute("success", "Blog Updated !!");
		}else {
			redirectAttributes.addFlashAttribute("error", "Blot Not Updated !!");
		}
		return "redirect:/post/allpost";
	}
}
