package com.xebia.articles.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * 
 * @author dhekumar2
 *
 */
@Data
@Entity
@Table(name="TL_TAGS")
@Component
public class TagsEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", updatable = false, nullable = false)
	private int id;
	
	@Column(name = "THEME", updatable = false, nullable = false)
	private String theme;
	
	@Column(name = "ARTICLES_ID", updatable = false, nullable = false)
	private int articleId;
}

