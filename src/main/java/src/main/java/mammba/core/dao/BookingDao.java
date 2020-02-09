package src.main.java.mammba.core.dao;

import java.util.List;

import src.main.java.mammba.core.exception.DaoException;
import src.main.java.mammba.model.Booking;

public interface BookingDao {

	Integer saveNewBooking(Booking booking) throws DaoException;

	Booking getBookingByTourId(Integer tourId,Integer memberId) throws DaoException;

	Booking updateBooking(Booking booking) throws DaoException;

	Booking cancelBooking(String bookingNumber) throws DaoException;

	List<Booking> getAllBooking(Integer userId) throws DaoException;

	List<Booking> searchBooking(Booking booking) throws DaoException;

}
