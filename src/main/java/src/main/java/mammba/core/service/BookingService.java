package src.main.java.mammba.core.service;

import src.main.java.mammba.core.exception.ServiceException;
import src.main.java.mammba.model.Booking;

public interface BookingService {

	public Booking bookTour(Booking book) throws ServiceException;
	
	public Boolean cancelTour(String bookingNumber) throws ServiceException;;
	
	public Booking getBookingDetails(Integer bookId) throws ServiceException;
	
}
