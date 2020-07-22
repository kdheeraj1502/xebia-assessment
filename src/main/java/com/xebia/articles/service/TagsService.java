package com.xebia.articles.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.xebia.articles.entity.TagsEntity;
import com.xebia.articles.model.TagsMetrics;
import com.xebia.articles.repository.TagsRepository;
import com.xebia.articles.util.DataPersistenceUtility;

/**
 * 
 * @author dhekumar2
 *
 */
@Service
public class TagsService {

	@Autowired
	TagsRepository tagsRepo;

	@Autowired
	DataPersistenceUtility utility;

	/*
	 * public void saveTags(Tags tag) { TagsEntity tagsEntity =
	 * utility.convertVOToEntity(tag, TagsEntity.class); tagsRepo.save(tagsEntity);
	 * }
	 */
	
	/**
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page<TagsEntity> getTags(Integer pageNo, Integer pageSize) {
		Pageable paging = PageRequest.of(pageNo, pageSize);
		return tagsRepo.findAll(paging);
	}

	/**
	 * 
	 * @param articleId
	 * @return
	 */
	public List<TagsEntity> findByid(int articleId) {
		return tagsRepo.findByArticleid(articleId);
	}
	

	/**
	 * 
	 * @param tags
	 * @param articleId
	 */
	public void saveTags(List<String> tags, int articleId) {
		for (String tag : tags) {
			//Tags t = new Tags();
			TagsEntity tagsEntity = new TagsEntity();
			tagsEntity.setTheme(tag);
			tagsEntity.setArticleId(articleId);
			//saveTags(t);
			tagsRepo.save(tagsEntity);
		}
	}
	/**
	 * 
	 * @param tags
	 * @return
	 */
	public List<TagsMetrics> findTagCount(List<TagsEntity> tags) {
		Map<String, Integer> tagMetricsMap = new HashMap<>();
		for(TagsEntity data : tags) {
			tagMetricsMap.put(data.getTheme(), tagMetricsMap.getOrDefault(data.getTheme(), 0) + 1);
		}
		return tagMetricsMap.entrySet().stream().map(m -> {
			TagsMetrics metrics = new TagsMetrics();
			metrics.setTag(m.getKey());
			metrics.setOccurance(String.valueOf(m.getValue()));
			return metrics;
		}).collect(Collectors.toList());
	}
}
