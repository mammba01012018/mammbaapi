/**
 * QueryManager.java - MAMMBA Application
 * 2018 All rights reserved.
 *
 */
package src.main.java.mammba.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

import org.springframework.stereotype.Component;

/**
 * Query Manager handles all sql related utils.
 *
 * @author Mardolfh Del Rosario
 *
 */
@Component
public class QueryManager {
	private static final String propFileName = "sql.properties";
    private static Properties PROPERTIES;

    /**
     * Get specific query in sql properties.
     *
     * @return                              Properties reference.
     * @throws SQLException                 SQL error.
     */
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

    /**
     * Get the actual query string in sql properties.
     *
     * @param query                         key value.
     * @return                              SQL string.
     * @throws SQLException                 SQL error.
     */
    public String getQuery(String query) throws SQLException{
    	return getQueries().getProperty(query);
    }

}
