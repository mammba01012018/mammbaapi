package src.main.java.mammba.core.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import org.springframework.stereotype.Repository;

import src.main.java.mammba.core.dao.CityDao;
import src.main.java.mammba.core.exception.DaoException;
import src.main.java.mammba.core.util.QueryManager;
import src.main.java.mammba.model.City;

@Repository("cityDaoImpl")
public class CityDaoImpl implements CityDao {
	
	
	@Autowired
    private QueryManager queryManager;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final Logger LOGGER = Logger.getLogger(TourDestinationDaoImpl.class);
 
    private static class CityMappper implements RowMapper<City> {
        @Override
		public City mapRow(ResultSet rs, int rowNum) throws SQLException {
				City city = new City();
				city.setId(rs.getInt("city_id"));
				city.setName(rs.getString("city_name"));;
			return city;
		}
    }


	@Override
	public List<City> getCity(Integer countryId) throws DaoException  {
		try {
			String sql = this.queryManager.getQuery("getCities");
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("countryId", countryId);

			return  this.namedParameterJdbcTemplate.query(sql,params,new CityMappper());
			
		} catch (DataAccessException | SQLException e) {
			LOGGER.error("getCity()- exception", e);
			throw new DaoException("MAMMBA[AT]-04-Database error");
		}
	}

	
	
}
