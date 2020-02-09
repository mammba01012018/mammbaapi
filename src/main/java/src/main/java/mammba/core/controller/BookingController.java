package src.main.java.mammba.core.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import src.main.java.mammba.core.exception.ServiceException;
import src.main.java.mammba.core.service.BookingService;
import src.main.java.mammba.model.Booking;

@RestController
public class BookingController {

	@Autowired
	private BookingService bookingService;
	
	private static final Logger LOGGER = Logger.getLogger(BookingController.class);
	
	
	@PostMapping("/book/bookTour")
	public ResponseEntity<?> bookTour(@RequestBody Booking book) {
		 String errorMsg = "";
		 
		try {
			bookingService.bookTour(book);
		} catch (ServiceException e) {
			errorMsg = e.getMessage();
		}
		
		 LOGGER.info("Book Tour End");
	        return ResponseEntity.status(404).body(errorMsg);
	}
	
	public ResponseEntity<?> cancelBookTour(@RequestParam Integer bookId) {
		
		
		return null;
	}
	
	public ResponseEntity<?> getBookTour(@RequestParam Integer bookId) {
		
		
		return null;
	} 
	
	public ResponseEntity<?> getAllBookTour() {
		
		
		return null;
	} 
	
	
	
}
