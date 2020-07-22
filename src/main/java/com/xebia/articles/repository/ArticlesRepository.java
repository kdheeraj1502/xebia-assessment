package com.xebia.articles.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.xebia.articles.entity.ArticlesEntity;

/**
 * 
 * @author dhekumar2
 *
 */
@Repository
public interface ArticlesRepository extends PagingAndSortingRepository<ArticlesEntity, Integer> {

	/**
	 * 
	 * @param uuid
	 * @return
	 */
	@Query("from ArticlesEntity WHERE UUID=:uuid")
	ArticlesEntity findByUuid(@Param("uuid") String uuid);

	/**
	 * 
	 * @param articleId
	 * @param title
	 */
	@Modifying
	@Query("update ArticlesEntity article set article.title =:title where article.articleId =:articleId")
	void updateTitle(@Param("articleId") int articleId, @Param("title") String title);

	/**
	 * 
	 * @param articleId
	 * @param slug
	 */
	@Modifying
	@Query("update ArticlesEntity article set article.slug =:slug where article.articleId =:articleId")
	void updateSlug(@Param("articleId") int articleId, @Param("slug") String slug);
}
