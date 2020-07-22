package com.xebia.articles.controller;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.xebia.articles.api.response.ArticlesResponse;
import com.xebia.articles.model.ArticlesModel;
import com.xebia.articles.service.ArticlesService;
import com.xebia.articles.service.TagsService;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes={ArticlesController.class})
public class ArticlesControllerTest {
	
	@Autowired
	ArticlesController controller;
	
	@MockBean
	ArticlesService service;
	
	@MockBean
	TagsService tagService;
	
	@Test
	public void saveArticlesTest() throws Exception{
		ArticlesModel articlesVO = new ArticlesModel();
		articlesVO.setTitle("funny title");
		articlesVO.setDescription("its descpriction");
		articlesVO.setBody("it is body");
		articlesVO.setTags(Arrays.asList("tag1","tag2"));
		ResponseEntity<ArticlesResponse> response = controller.saveArticles(articlesVO);
		String body = response.getBody().getBody();
		assertEquals(body, "it is body");
	}
 
}