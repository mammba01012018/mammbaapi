package src.main.java.mammba.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import src.main.java.mammba.core.dao.BookingDao;
import src.main.java.mammba.core.dao.TourDao;
import src.main.java.mammba.core.exception.DaoException;
import src.main.java.mammba.core.exception.ServiceException;
import src.main.java.mammba.core.service.BookingService;
import src.main.java.mammba.core.service.SequenceService;
import src.main.java.mammba.core.util.ErrorMessage;
import src.main.java.mammba.core.util.Status;
import src.main.java.mammba.model.Booking;
import src.main.java.mammba.model.Sequence;

@Service("bookingServiceImpl")
public class BookingServiceImpl implements BookingService {
	
	@Autowired
	private TourDao tourDao;
	
	@Autowired 
	private BookingDao bookingDao;
		
	@Autowired
	private SequenceService sequenceService;

	@Override
	public Booking bookTour(Booking book) throws ServiceException {
		try {
			Booking b = bookingDao.getBookingByTourId(book.getTourId(), book.getMemberId());
			if (b == null || b.getBookingStatus().equals(Status.PAID.toString())) {
				Sequence seq = sequenceService.updateSequence("booking");
				Integer deductedTourSlot = -book.getBookingNoOfGuest();
				book.setBookingNumber(seq.getCurrSequence());
				bookingDao.saveNewBooking(book);
				tourDao.updateTourSlot(book.getTourId(), deductedTourSlot);
				return book;
			}
		} catch (DaoException e) {
		throw new ServiceException(ErrorMessage.BOOKING_ERR);
		}
		throw new ServiceException(ErrorMessage.BOOKING_DUPLICATE_ERR);
	}

	@Override
	public Boolean cancelTour(String bookingNumber) throws ServiceException {
		try {
			bookingDao.cancelBooking(bookingNumber);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			throw new ServiceException(ErrorMessage.BOOKING_CANCEL_ERR);
		}
		return true;
	}

	@Override
	public Booking getBookingDetails(Integer bookId) {
		// TODO Auto-generated method stub
		return null;
	}

}
