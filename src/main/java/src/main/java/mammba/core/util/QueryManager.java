package src.main.java.mammba.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

import org.springframework.stereotype.Component;

@Component
public class QueryManager {
	private static final String propFileName = "sql.properties";
    private static Properties PROPERTIES;

    private Properties getQueries() throws SQLException {
		try(InputStream input = getClass().getClassLoader().getResourceAsStream(propFileName)) {
	    	if(PROPERTIES == null){
	    		PROPERTIES = new Properties();
	    		PROPERTIES.load(input);		
	    	}
		} catch (IOException e) {
			throw new SQLException("Unable to load file.");
		}
    	return PROPERTIES;
    }

    public String getQuery(String query) throws SQLException{
    	return getQueries().getProperty(query);
    }

}
