package src.main.java.mammba.core.service;

import java.util.List;

import src.main.java.mammba.core.exception.ServiceException;
import src.main.java.mammba.model.Tour;

public interface TourService {
	
	void addTour(Tour tour) throws ServiceException; 
	
	Tour getTour(Integer tourId) throws ServiceException; 
	
	List<Tour> getTours() throws ServiceException; 
}
