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
import org.springframework.stereotype.Service;

import src.main.java.mammba.core.dao.TourInclusionsDao;
import src.main.java.mammba.core.exception.DaoException;
import src.main.java.mammba.core.util.QueryManager;
import src.main.java.mammba.model.TourInclusions;

@Service("tourInclusionsDaoImpl")
public class TourInclusionsDaoImpl implements TourInclusionsDao{
	@Autowired
    private QueryManager queryManager;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final Logger LOGGER = Logger.getLogger(TourInclusionsDaoImpl.class);
 
    private static class TourInclusionsMapper implements RowMapper<TourInclusions> {
        @Override
		public TourInclusions mapRow(ResultSet rs, int rowNum) throws SQLException {
        	TourInclusions tourInclusions = new TourInclusions();
        	tourInclusions.setTourId(rs.getInt("tour_id"));
        	tourInclusions.setTourInclusionId(rs.getInt("tour_inclusion_id"));
        	tourInclusions.setTourInclusions(rs.getString("tour_inclusion"));        	
			return tourInclusions;
		}
    }
	@Override
	public Integer addTourInclusions(TourInclusions tourInclusions) throws DaoException {
		try {
			String sql = this.queryManager.getQuery("addTourInclusions");
			KeyHolder keyHolder = new GeneratedKeyHolder();
			SqlParameterSource parameters = new MapSqlParameterSource()
					.addValue("tourId", tourInclusions.getTourId())
					.addValue("tourInclusionId", tourInclusions.getTourInclusionId())
					.addValue("tourInclusions", tourInclusions.getTourInclusions());				

			this.namedParameterJdbcTemplate.update(sql, parameters,keyHolder);
			 Long tourInclusionId = (Long) keyHolder.getKey();
	         return tourInclusionId.intValue(); 

		} catch (DataAccessException | SQLException e) {
			LOGGER.error("addTour Inclusions()-exception", e);
			throw new DaoException("MAMMBA[AT]-04-Database error");
		}
	}

	@Override
	public List<TourInclusions> getTourInclusions() throws DaoException {
		try {
			String sql = this.queryManager.getQuery("getTourInclusions");
			return  this.namedParameterJdbcTemplate.query(sql, new TourInclusionsMapper());
			
		} catch (DataAccessException | SQLException e) {
			LOGGER.error("TourInclusionsMapper() -exception", e);
			throw new DaoException("MAMMBA[AT]-04-Database error");
		}
	}

	@Override
	public List<TourInclusions> findByTourInclusionsDesc(List<String> tourInclusion) throws DaoException {

		try {
			String sql = this.queryManager.getQuery("searchTourInclusions");
			SqlParameterSource parameters = new MapSqlParameterSource().addValue("tourInclusions", tourInclusion);
			return  this.namedParameterJdbcTemplate.query(sql,parameters,new TourInclusionsMapper());
			
		} catch (DataAccessException | SQLException e) {
			LOGGER.error("findByTourInclusionsDesc()- exception", e);
			throw new DaoException("MAMMBA[AT]-04-Database error");
		}
	
	}

	@Override
	public List<TourInclusions> findByTourId(Integer tourId) throws DaoException {
		try {
			String sql = this.queryManager.getQuery("findByTourIdTourInclusions");
			SqlParameterSource parameters = new MapSqlParameterSource().addValue("tourId", tourId);
			return  this.namedParameterJdbcTemplate.query(sql,parameters,new TourInclusionsMapper());
			
		} catch (DataAccessException | SQLException e) {
			LOGGER.error("findByTourInclusionsDesc()- exception", e);
			throw new DaoException("MAMMBA[AT]-04-Database error");
		}
	}

}
