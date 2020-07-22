package com.xebia.articles.entity;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.*;
import javax.validation.constraints.NotBlank;

import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * 
 * @author dhekumar2
 *
 */
@Data
@Entity
@Table(name = "TL_ARTICLES")
@Component
public class ArticlesEntity {

    @Id
    @GeneratedValue
    @Column(name = "ARTICLES_ID", updatable = false, nullable = false)
    private int articleId;

	@Column(name = "uuid", updatable = false, nullable = false)
	private String uuid;
	
	@Column(name = "SLUG")
	private String slug;
	
 	@NotBlank(message = "Start Date is mandatory")
	@Column(name = "TITLE")
	private String title;
	
 	@NotBlank(message = "Start Date is mandatory")
	@Column(name = "DESCRIPTION")
	private String description;
	
 	@NotBlank(message = "Start Date is mandatory")
	@Column(name = "BODY")
	private String body;
    
  //  @Temporal(TemporalType.DATE)
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;
    
   // @Temporal(TemporalType.DATE)
    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @Column(name = "FAVORITED")
    private int favorited;
 
    @Column(name = "FAVORITES_COUNT")
    private int favoritesCount;
    
//	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@Transient
	private List<String> tags;
	
    @PrePersist
    public void prePersist() {
    	createdAt = LocalDateTime.now();
    }
 
    @PreUpdate
    public void preUpdate() {
    	updatedAt = LocalDateTime.now();
    }
}
