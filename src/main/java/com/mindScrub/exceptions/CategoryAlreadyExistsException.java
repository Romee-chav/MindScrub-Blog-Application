package com.mindScrub.exceptions;

public class CategoryAlreadyExistsException extends RuntimeException{
	public CategoryAlreadyExistsException(String message) {
        super(message);
    }
}
