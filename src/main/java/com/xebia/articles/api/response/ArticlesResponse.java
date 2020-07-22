package com.xebia.articles.api.response;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * 
 * @author dhekumar2
 *
 */
@Data
@Component
public class ArticlesResponse {
	
	private String id;
	
	private String slug;

	private String title;

	private String description;
	
	private String body;
	
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;

    private boolean favorited;
 
    private int favoritesCount;
    
    private List<String> tags;
	
}
