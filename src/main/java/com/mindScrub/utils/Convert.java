package com.mindScrub.utils;

public class Convert {

	public static String titleToUrl(String postTitle) {
		String title = postTitle.trim().toLowerCase();
		String url = title.replaceAll("\\s", "-");
		String urlTitle = url.replaceAll("[^A-Za-z0-9]", "-");
		return urlTitle;
	}
}
