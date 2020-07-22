package com.xebia.articles.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.dozer.Mapper;

/**
 * 
 * @author dhekumar2
 *
 */
@Component
public class DataPersistenceUtility {
	
	@Autowired
	Mapper mapper;
	
	/**
	 * 
	 * @param <R>
	 * @param <T>
	 * @param vo
	 * @param entityClassRef
	 * @return
	 */
	public <R,T>  R convertVOToEntity(T vo, Class<R> entityClassRef){
		if(vo != null) {
			return mapper.map(vo, entityClassRef);
		}
		return null;
	}
	
	/**
	 * 
	 * @param <R>
	 * @param <T>
	 * @param entity
	 * @param voClassRef
	 * @return
	 */
	public <R,T>  R convertEntityToVO(T entity, Class<R> voClassRef){
		if(entity != null) {
			return mapper.map(entity, voClassRef);
		}
		return null;
	}	
}
