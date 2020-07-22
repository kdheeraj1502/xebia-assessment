package com.xebia.articles.model;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ArticlesModel {
	
//    private int articleId;

	private String uuid;
	
	private String slug;
	
    @JsonProperty("title")
    @NotBlank
	private String title;
	
    @JsonProperty("description")
    @NotBlank
	private String description;
	
    @JsonProperty("body")
    @NotBlank
	private String body;
	
    @JsonProperty("tags")
    @NotBlank
	private List<String> tags;
}
