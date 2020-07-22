package com.xebia.articles.api.response;

import java.util.Map;

import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * 
 * @author dhekumar2
 *
 */
@Data
@Component
public class ReadTime {

	private String uuid;
	private Map<String, Long> readtime;
}
