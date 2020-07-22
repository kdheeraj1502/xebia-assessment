package com.xebia.articles.service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xebia.articles.api.response.ApiResponse;
import com.xebia.articles.api.response.ArticlesResponse;
import com.xebia.articles.api.response.ReadTime;
import com.xebia.articles.controller.ArticlesController;
import com.xebia.articles.entity.ArticlesEntity;
import com.xebia.articles.entity.TagsEntity;
import com.xebia.articles.exception.error.response.NotFoundError;
import com.xebia.articles.model.ArticlesModel;
import com.xebia.articles.repository.ArticlesRepository;
import com.xebia.articles.similarity.JaroWinklerStrategy;
import com.xebia.articles.similarity.SimilarityStrategy;
import com.xebia.articles.similarity.StringSimilarityService;
import com.xebia.articles.similarity.StringSimilarityServiceImpl;
import com.xebia.articles.util.DataPersistenceUtility;

/**
 * 
 * @author dhekumar2
 *
 */
@Service
public class ArticlesService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ArticlesService.class);

	@Autowired
	ArticlesRepository tutorialRepo;

	@Autowired
	DataPersistenceUtility utility;

	@Autowired
	ArticlesResponse articlesResponse;

	@Autowired
	TagsService tagsService;

	@Value("${read.speed}")
	private double readSpeed;

	@Autowired
	ReadTime readTime;

	/**
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public List<ApiResponse> getAllArticles(Integer pageNo, Integer pageSize) {
		Pageable paging = PageRequest.of(pageNo, pageSize);
		Page<ArticlesEntity> articles = tutorialRepo.findAll(paging);
		return articles.stream().map(art -> {
			ApiResponse response = new ApiResponse();
			if (art.getFavorited() == 1)
				articlesResponse.setFavorited(Boolean.TRUE);
			else
				articlesResponse.setFavorited(Boolean.FALSE);
			response.setResonse(convertInRespone(art));
			return response;
		}).collect(Collectors.toList());
	}
	/**
	 * 
	 * @param uuid
	 * @return
	 */
	public ArticlesEntity getAnArticles(String uuid) {
		return findById(uuid).get();
	}
	/**
	 * 
	 * @param uuid
	 * @return
	 * @throws NotFoundError 
	 */
	public String deleteAnArticles(String uuid) throws NotFoundError {
		try {
			tutorialRepo.delete(findById(uuid).get());
			return "Article with id " + uuid + " successfully deleted";
		} catch (Exception ex) {
			LOGGER.error("UUID Not Found");
			throw new NotFoundError("UUID Not Found");
		}
	}
	/**
	 * 
	 * @param uuid
	 * @return
	 */
	public ReadTime getTimeToRead(String uuid) {
		String body = findById(uuid).get().getBody();
		readTime.setUuid(uuid);
		double wordCount = 0;
		if (!body.isEmpty()
				&& (!(" ".equals(body.substring(0, 1))) || !(" ".equals(body.substring(body.length() - 1))))) {
			for (int i = 0; i < body.length(); i++) {
				if (body.charAt(i) == ' ') {
					wordCount++;
				}
			}
			wordCount += 1;
		}
		long time = (long) Math.ceil(wordCount / readSpeed);

		long minutes = TimeUnit.SECONDS.toMinutes(time);
		time -= TimeUnit.MINUTES.toMillis(minutes);

		long seconds = TimeUnit.SECONDS.toSeconds(time);
		Map<String, Long> readTimemap = new HashMap<>();
		readTimemap.put("mins", minutes);
		readTimemap.put("seconds", seconds);
		readTime.setReadtime(readTimemap);
		return readTime;
	}
	/**
	 * 
	 * @param article
	 * @return
	 */
	public ArticlesEntity saveArticles(ArticlesModel article) {
		ArticlesEntity entitySaved = null;
		if (checkDuplicateBody(article.getBody(), article.getUuid())) {
			ArticlesEntity articlesEntity = utility.convertVOToEntity(article, ArticlesEntity.class);
			Optional<ArticlesEntity> details = findById(articlesEntity.getUuid());
			if (details != null && details.isPresent()) {
				ArticlesEntity art = details.get();
				articlesEntity.setFavorited(art.getFavorited());
				articlesEntity.setFavoritesCount(art.getFavoritesCount() + 1);
				articlesEntity.setUpdatedAt(LocalDateTime.now());
			} else {
				articlesEntity.setFavorited(1);
				articlesEntity.setFavoritesCount(1);
				articlesEntity.setUpdatedAt(LocalDateTime.now());
			}
			entitySaved = tutorialRepo.save(articlesEntity);
		}
		return entitySaved;
	}
	/**
	 * 
	 * @param uuid
	 * @return
	 */
	public Optional<ArticlesEntity> findById(String uuid) {
		try {
			return Optional.of(tutorialRepo.findByUuid(uuid));
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 
	 * @param articleId
	 * @param title
	 * @throws JsonProcessingException 
	 * @throws JsonMappingException 
	 */
	@Transactional
	public void updateTitleAndSlug(int articleId, String title) throws JsonMappingException, JsonProcessingException {
		tutorialRepo.updateTitle(articleId, jsonTitleTotext(title));
		try {
			title = titleToSlug(title);
			tutorialRepo.updateSlug(articleId, title);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param title
	 * @return
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	public String jsonTitleTotext(String title) throws JsonMappingException, JsonProcessingException {
		if(title.contains("title")) {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(title);
			return jsonNode.get("title").asText();
		}else {
			return title;
		}
	}
	
	/**
	 * 
	 * @param title
	 * @return
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	public String titleToSlug(String title) throws JsonMappingException, JsonProcessingException {
		if(title.contains("title")) {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(title);
			return jsonNode.get("title").asText().replace(" ", "-");
		}else {
			return title.replace(" ", "-");
		}
	}
	/**
	 * 
	 * @param articles
	 * @return
	 */
	public ArticlesResponse convertInRespone(ArticlesEntity articles) {
		articlesResponse.setId(articles.getUuid());
		articlesResponse.setBody(articles.getBody());
		articlesResponse.setCreatedAt(articles.getCreatedAt());
		articlesResponse.setDescription(articles.getDescription());
		articlesResponse.setFavoritesCount(articles.getFavoritesCount());
		articlesResponse.setSlug(articles.getSlug());
		articlesResponse.setTitle(articles.getTitle());
		articlesResponse.setUpdatedAt(articles.getUpdatedAt());
		List<TagsEntity> tags = tagsService.findByid(articles.getArticleId());
		articlesResponse.setTags(tags.stream().map(t -> t.getTheme()).collect(Collectors.toList()));
		return articlesResponse;
	}
	
	/**
	 * 
	 * @param target
	 * @param uuid
	 * @return
	 */
	private boolean checkDuplicateBody(String target, String uuid) {
		SimilarityStrategy strategy = new JaroWinklerStrategy();
		StringSimilarityService service = new StringSimilarityServiceImpl(strategy);
		List<ArticlesEntity> articles = (List<ArticlesEntity>) tutorialRepo.findAll();
		if (articles.size() > 0) {
			for (ArticlesEntity res : articles) {
				double score = service.score(res.getBody(), target);
				if ((score * 100) > 70)
					return false;
				if (res.getUuid().equalsIgnoreCase(uuid))
					return false;
			}
		}
		return true;
	}
}
