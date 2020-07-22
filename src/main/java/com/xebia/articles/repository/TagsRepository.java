package com.xebia.articles.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.xebia.articles.entity.TagsEntity;

/**
 * 
 * @author dhekumar2
 *
 */
@Repository
public interface TagsRepository extends JpaRepository<TagsEntity, Integer> {

	/**
	 * 
	 * @param articleId
	 * @return
	 */
	@Query("from TagsEntity WHERE articleId=:articleId")
	List<TagsEntity> findByArticleid(@Param("articleId") int articleId);
}
