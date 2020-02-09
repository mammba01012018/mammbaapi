package src.main.java.mammba.core.dao;

import java.util.List;

import src.main.java.mammba.core.exception.DaoException;
import src.main.java.mammba.model.Tour;

public interface TourDao {

	Integer addTour(Tour tour) throws DaoException;
	
	Boolean updateTour(Tour tour) throws DaoException;
	
	Boolean updateTourSlot(Integer tourId, Integer tourSlot) throws DaoException;
	
	List<Tour> getTours() throws DaoException;
	
	List<Tour> searchTours(Tour tour) throws DaoException;
	
	List<Tour> searchTours(Tour tour, List<Integer> tourIdList) throws DaoException;
	
	Tour getTour(Integer tourId) throws DaoException;
	
	Integer removeTours() throws DaoException;
}
