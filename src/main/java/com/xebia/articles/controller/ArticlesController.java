package com.xebia.articles.controller;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.uuid.Generators;
import com.xebia.articles.api.response.ApiResponse;
import com.xebia.articles.api.response.ArticlesResponse;
import com.xebia.articles.api.response.ReadTime;
import com.xebia.articles.entity.ArticlesEntity;
import com.xebia.articles.entity.TagsEntity;
import com.xebia.articles.exception.error.response.NotFoundError;
import com.xebia.articles.exception.error.response.ServerFailedError;
import com.xebia.articles.exception.error.response.ValidationFailedError;
import com.xebia.articles.model.ArticlesModel;
import com.xebia.articles.model.TagsMetrics;
import com.xebia.articles.service.ArticlesService;
import com.xebia.articles.service.TagsService;

/**
 * 
 * @author dhekumar2
 *
 */
@RestController
@RequestMapping("/api/articles")
public class ArticlesController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ArticlesController.class);

	@Autowired
	ArticlesService articlesService;

	@Autowired
	TagsService tagsService;

	/**
	 * 
	 * @param requestString
	 * @return
	 * @throws ServerFailedError
	 * @throws ValidationFailedError
	 * @throws JsonProcessingException
	 * @throws JsonMappingException
	 */
	@PostMapping("/saveArticles")
	public ResponseEntity<ArticlesResponse> saveArticles(@RequestBody ArticlesModel articlesVO)
			throws ServerFailedError, ValidationFailedError, JsonMappingException, JsonProcessingException {
		if (articlesVO != null) {
			String slug = articlesService.titleToSlug(articlesVO.getTitle());
			UUID uuid1 = Generators.timeBasedGenerator().generate();
			String uuid = uuid1.toString().replaceAll("-", "");
			articlesVO.setUuid(uuid);
			articlesVO.setSlug(slug);
			ArticlesEntity articlesEntity = articlesService.saveArticles(articlesVO);
			if (articlesEntity == null) {
				LOGGER.error("Duplicate Entry Found Error");
				throw new ValidationFailedError("Duplicate Entry Found Error");
			}
			if (articlesVO.getTags() != null)
				tagsService.saveTags(articlesVO.getTags(), articlesEntity.getArticleId());
			ArticlesResponse response = articlesService.convertInRespone(articlesEntity);
			return new ResponseEntity<ArticlesResponse>(response, HttpStatus.CREATED);
		} else {
			LOGGER.error("Internal Server Error");
			throw new ServerFailedError("Internal Server Error");
		}
	}

	/**
	 * 
	 * @return
	 * @throws NotFoundError
	 */
	@GetMapping("/allArticles")
	public ResponseEntity<List<ApiResponse>> allArticles(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize) throws NotFoundError {
		List<ApiResponse> allArticles = articlesService.getAllArticles(pageNo, pageSize);
		if (allArticles != null) {
			return new ResponseEntity<List<ApiResponse>>(articlesService.getAllArticles(pageNo, pageSize),
					HttpStatus.OK);
		} else {
			LOGGER.error("Articles Not Found");
			throw new NotFoundError("Articles Not Found");
		}
	}

	/**
	 * 
	 * @param uuid
	 * @return
	 * @throws NotFoundError 
	 */
	@DeleteMapping("/delete/{uuid}")
	public ResponseEntity<String> deleteArticle(@PathVariable String uuid) throws NotFoundError {
		return new ResponseEntity<String>(articlesService.deleteAnArticles(uuid), HttpStatus.OK);
	}

	/**
	 * 
	 * @param uuid
	 * @return
	 */
	@GetMapping("/getArticle/{uuid}")
	public ResponseEntity<ArticlesEntity> findArticle(@PathVariable String uuid) {
		return new ResponseEntity<ArticlesEntity>(articlesService.getAnArticles(uuid), HttpStatus.OK);
	}

	/**
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@GetMapping("/allTags")
	public ResponseEntity<List<TagsEntity>> allTags(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize) {
		List<TagsEntity> details = tagsService.getTags(pageNo, pageSize).getContent();
		return new ResponseEntity<List<TagsEntity>>(details, HttpStatus.OK);
	}

	/**
	 * 
	 * @param articleId
	 * @param title
	 * @return
	 * @throws JsonProcessingException
	 * @throws JsonMappingException
	 */
	@PatchMapping("/updateArticle/{id}")
	public ResponseEntity<String> updateTitleSlug(@PathVariable("id") int articleId, @RequestBody String title)
			throws JsonMappingException, JsonProcessingException {
		articlesService.updateTitleAndSlug(articleId, title);
		return new ResponseEntity<String>("Title  updated successfully", HttpStatus.OK);
	}

	/**
	 * 
	 * @param uuid
	 * @return
	 * @throws NotFoundError
	 */
	@GetMapping("/readtime/{uuid}")
	public ResponseEntity<ReadTime> readTime(@PathVariable String uuid) throws NotFoundError {
		ReadTime readTime = null;
		try {
			readTime = articlesService.getTimeToRead(uuid);
		} catch (Exception ex) {
			LOGGER.error("uuid not Found" + ex.getLocalizedMessage());
			throw new NotFoundError("UUID Not Found");
		}
		return new ResponseEntity<ReadTime>(readTime, HttpStatus.OK);
	}

	/**
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@GetMapping("/tags/count")
	public ResponseEntity<List<TagsMetrics>> tagCount(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize) {
		List<TagsEntity> tagsList = allTags(pageNo, pageSize).getBody();
		return new ResponseEntity<List<TagsMetrics>>(tagsService.findTagCount(tagsList), HttpStatus.OK);
	}

	/*
	 * @PatchMapping("{id}/updateArticle/{title}") public String
	 * updateTitleAndSlug(@PathVariable("id") int articleId, @PathVariable("title")
	 * String title) { articlesService.updateTitleAndSlug(articleId, title); return
	 * "Title  updated successfully..."; }
	 */
}
