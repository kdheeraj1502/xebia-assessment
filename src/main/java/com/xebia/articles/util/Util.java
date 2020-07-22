package com.xebia.articles.util;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.xebia.articles.model.ArticlesModel;

/**
 * 
 * @author dhekumar2
 *
 */
public class Util {
    private static final Logger LOGGER = LoggerFactory.getLogger(Util.class);

    /**
     * 
     * @param requestString
     * @return
     */
    public static ArticlesModel getAPIRequest(String requestString)  {
        ObjectMapper mapper = new ObjectMapper();
        ArticlesModel apiRequest = null;
        String str = "";
        String tag = "";
        if(requestString.contains("tags")) {
        	int index = requestString.indexOf("tags");
        	str = requestString.substring(0, index);
        	tag = new StringBuilder().append("{")
        			.append("\"\\")
        			.append(requestString.substring(index, requestString.length())).toString();
        	int in = tag.indexOf("[");
        	int last = tag.indexOf("]");
        	tag = tag.substring(in, last + 1);
        	//tag = tag.replace('[', '{');
        	//tag = tag.replace(']', '}');
        	StringBuilder sb = new StringBuilder(str);
        	str = sb.replace(index - 4, index - 1, "}").toString();
        }
        try {
            apiRequest = mapper.readValue(str, ArticlesModel.class);

        } catch (UnrecognizedPropertyException e){
          LOGGER.error("Unrecognized field " + e.getPropertyName() + " present in body." );
        } catch (IOException e){
            LOGGER.info(e.getLocalizedMessage());
        }
        return apiRequest;
    }
}
