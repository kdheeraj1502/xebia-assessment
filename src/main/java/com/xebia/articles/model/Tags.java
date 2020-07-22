package com.xebia.articles.model;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@Component
public class Tags {
	
	@JsonProperty("id")
	private int id;
	
	@JsonProperty("theme")
	private String theme;
	
	@JsonProperty("articleId")
	private int articleId;
}
