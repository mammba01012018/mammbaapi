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

import src.main.java.mammba.core.dao.TourDao;
import src.main.java.mammba.core.exception.DaoException;
import src.main.java.mammba.core.util.QueryManager;
import src.main.java.mammba.model.Tour;

@Repository("tourDaoImpl")
public class TourDaoImpl implements TourDao{
	
	@Autowired
    private QueryManager queryManager;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final Logger LOGGER = Logger.getLogger(TourDaoImpl.class);

    private static class TourMapper implements RowMapper<Tour> {
        @Override
        public Tour mapRow(ResultSet rs, int rowNum) throws SQLException {
            Tour tour = new Tour();
            tour.setTourId(rs.getInt("tour_id"));
            tour.setTourType(rs.getString("tour_type"));
            tour.setStartDate(rs.getDate("tour_startDate"));
            tour.setEndDate(rs.getDate("tour_endDate"));
            tour.setTourPackageName(rs.getString("tour_package"));
            return tour;
        }
    }

	@Override
	public Integer addTour(Tour tour) throws DaoException {
		 try {
	            String sql = this.queryManager.getQuery("addTour");
	            KeyHolder keyHolder = new GeneratedKeyHolder();
	            SqlParameterSource parameters = new MapSqlParameterSource()
	                    .addValue("tourPackage", tour.getTourPackageName())
	                    .addValue("tourType", tour.getTourType())
	                    .addValue("tourStartDate", tour.getStartDate())
	                    .addValue("tourEndDate",tour.getEndDate())
	                    .addValue("tourValidityStartDate", tour.getTourValidityStartDate())
	                    .addValue("tourValidityEndDate", tour.getTourValidityEndDate())
	                    .addValue("tourPrice", tour.getTourPrice())
	                    .addValue("tourDuration", tour.getTourDuration())
	                    .addValue("tourMinSlot", tour.getTourMinSlot())
	                    .addValue("tourMaxSlot", tour.getTourMaxSlot())
	                    .addValue("partnerId", tour.getPartner().getPartnerId());                   

	           this.namedParameterJdbcTemplate.update(sql, parameters,keyHolder);
	           Long tourId = (Long) keyHolder.getKey();
	           return tourId.intValue();
	        } catch (DataAccessException | SQLException e) {
	            LOGGER.error("addTour()-exception", e);
	            throw new DaoException("MAMMBA[AT]-04-Database error");
	        }
		
	}

	@Override
	public List<Tour> getTours() throws DaoException {
	
		 try {
			String sql = this.queryManager.getQuery("getAllTours");
			return  this.namedParameterJdbcTemplate.query(sql, new TourMapper());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		return null;
	}


	@Override
	public Integer removeTours() throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tour getTour(Integer tourId) throws DaoException {
		// TODO Auto-generated method stub
		try {
			
			String sql = this.queryManager.getQuery("getTours");
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("tourId", tourId);

			List<Tour> tourList = this.namedParameterJdbcTemplate.query(sql, params ,new TourMapper());

			if (tourList != null && !tourList.isEmpty()) {
				return tourList.get(0);
			}

		} catch (SQLException | DataAccessException e) {
			LOGGER.error("getTour(TourDAO)-exception", e);
			throw new DaoException("MAMMBA[UDI-C]-01-Database error");

		}
		return null;
	}

	
	
   
}
