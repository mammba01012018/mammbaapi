package src.main.java.mammba.core.dao;

import java.util.List;

import src.main.java.mammba.core.exception.DaoException;
import src.main.java.mammba.model.Tour;

public interface TourDao {

	Integer addTour(Tour tour) throws DaoException;
	
	List<Tour> getTours() throws DaoException;
	
	
	Tour getTour(Integer tourId) throws DaoException;
	
	Integer removeTours() throws DaoException;
}
