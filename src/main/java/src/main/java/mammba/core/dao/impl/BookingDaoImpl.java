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

import src.main.java.mammba.core.dao.BookingDao;
import src.main.java.mammba.core.exception.DaoException;
import src.main.java.mammba.core.util.QueryManager;
import src.main.java.mammba.model.Booking;

@Repository("bookingDaoImpl")
public class BookingDaoImpl implements BookingDao{
	
	@Autowired
    private QueryManager queryManager;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final Logger LOGGER = Logger.getLogger(BookingDaoImpl.class);
 
    private static class BookingMapper implements RowMapper<Booking> {
        @Override
		public Booking mapRow(ResultSet rs, int rowNum) throws SQLException {
			Booking b = new Booking();
			b.setBookingNumber(rs.getString("booking_number"));
			b.setBookingNoOfGuest(rs.getInt("booking_numOfGuest"));
			b.setBookingStatus(rs.getString("booking_status"));
			b.setTourId(rs.getInt("tour_id"));
			return b;
		}
    }
	

	@Override
	public Integer saveNewBooking(Booking booking) throws DaoException {
		try {
			String sql = this.queryManager.getQuery("saveBooking");
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("bookingNumber", booking.getBookingNumber());
			params.addValue("bookingPickupAddr", booking.getBookingPickupAddress());
			params.addValue("bookingMobileNum", booking.getBookingMobileNumber());
			params.addValue("bookingNoOfGuest", booking.getBookingNoOfGuest());
			params.addValue("tourId", booking.getTourId());
			params.addValue("memberId", booking.getMemberId());

		  this.namedParameterJdbcTemplate.update(sql,params);
			
		} catch (DataAccessException | SQLException e) {
			LOGGER.error("saveNewBooking()- exception", e);
			throw new DaoException("MAMMBA[AT]-04-Database error");
		}
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Booking updateBooking(Booking booking) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Booking cancelBooking(String bookingNumber) throws DaoException {
		try {
			String sql = this.queryManager.getQuery("cancelBooking");
			MapSqlParameterSource params = new MapSqlParameterSource();
		   params.addValue("bookingNumber", bookingNumber);		
		  this.namedParameterJdbcTemplate.update(sql,params);
			
		} catch (DataAccessException | SQLException e) {
			// TODO Auto-generated catch block
			throw new DaoException("MAMMBA[AT]-04-Database error");
		}
		
		
		return null;
	}

	@Override
	public List<Booking> getAllBooking(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Booking> searchBooking(Booking booking) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Booking getBookingByTourId(Integer tourId, Integer memberId) throws DaoException {
		try {
			String sql = this.queryManager.getQuery("getBookingByTourId");
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("tourId", tourId);
			params.addValue("memberId", memberId);
			List<Booking> result = this.namedParameterJdbcTemplate.query(sql,params,new BookingMapper());
			if (result != null && !result.isEmpty()) {
				return result.get(0);
			}
			return null;
		} catch (DataAccessException | SQLException e) {
			LOGGER.error("getBookingByTourId()-exception", e);
			throw new DaoException("MAMMBA[AT]-04-Database error");
		}
	}

}
