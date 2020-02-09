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
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import src.main.java.mammba.core.dao.TourDestinationDao;
import src.main.java.mammba.core.exception.DaoException;
import src.main.java.mammba.core.util.QueryManager;
import src.main.java.mammba.model.TourDestination;

@Repository("tourDestinationDaoImpl")
public class TourDestinationDaoImpl implements TourDestinationDao{
	
	@Autowired
    private QueryManager queryManager;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final Logger LOGGER = Logger.getLogger(TourDestinationDaoImpl.class);
 
    private static class TourDestinationMapper implements RowMapper<TourDestination> {
        @Override
		public TourDestination mapRow(ResultSet rs, int rowNum) throws SQLException {
			TourDestination tourDestination = new TourDestination();
			tourDestination.setTourId(rs.getInt("tour_id"));
			tourDestination.setTourDestinationId(rs.getInt("tour_destination_id"));
			tourDestination.setTourDestinationDescription(rs.getString("tour_destination"));

			return tourDestination;
		}
    }

	@Override
	public Integer addTourDestination(TourDestination tourDestination) throws DaoException {
		try {
			String sql = this.queryManager.getQuery("addTourDestination");
			KeyHolder keyHolder = new GeneratedKeyHolder();
			SqlParameterSource parameters = new MapSqlParameterSource()
					.addValue("tourId", tourDestination.getTourId())
					.addValue("tourDestination", tourDestination.getTourDestinationDescription());

			this.namedParameterJdbcTemplate.update(sql, parameters,keyHolder);
			 Long tourId = (Long) keyHolder.getKey();
	         return tourId.intValue(); 

		} catch (DataAccessException | SQLException e) {
			LOGGER.error("addTourDestination()-exception", e);
			throw new DaoException("MAMMBA[AT]-04-Database error");
		}

	}

	@Override
	public List<TourDestination> getTourDestination() throws DaoException {
		try {
			String sql = this.queryManager.getQuery("getTours");
			return  this.namedParameterJdbcTemplate.query(sql, new TourDestinationMapper());
			
		} catch (DataAccessException | SQLException e) {
			LOGGER.error("getTourDestination()-exception", e);
			throw new DaoException("MAMMBA[AT]-04-Database error");
		}
	}

	@Override
	public List<TourDestination> findByTourDestinationDesc(List<String> destinations) throws DaoException {
		try {
			String sql = this.queryManager.getQuery("searchTourDestination");
			SqlParameterSource parameters = new MapSqlParameterSource().addValue("tourDestination", destinations);
			return  this.namedParameterJdbcTemplate.query(sql,parameters,new TourDestinationMapper());
			
		} catch (DataAccessException | SQLException e) {
			LOGGER.error("findByTourDestinationDesc()- exception", e);
			throw new DaoException("MAMMBA[AT]-04-Database error");
		}
	}

	@Override
	public List<TourDestination> findByTourId(Integer tourId) throws DaoException {
		try {
			String sql = this.queryManager.getQuery("findByTourIdTourDestination");
			SqlParameterSource parameters = new MapSqlParameterSource().addValue("tourId", tourId);
			return  this.namedParameterJdbcTemplate.query(sql,parameters,new TourDestinationMapper());
			
		} catch (DataAccessException | SQLException e) {
			LOGGER.error("findByTourDestinationDesc()- exception", e);
			throw new DaoException("MAMMBA[AT]-04-Database error");
		}
	}

}
