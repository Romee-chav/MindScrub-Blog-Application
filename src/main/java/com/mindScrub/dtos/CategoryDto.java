package com.mindScrub.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
	private Long id;
	@NotEmpty(message = "Category name cannot empty !!")
	private String name;
}
