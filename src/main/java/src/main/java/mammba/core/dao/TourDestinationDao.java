package src.main.java.mammba.core.dao;

import java.util.List;

import src.main.java.mammba.core.exception.DaoException;
import src.main.java.mammba.model.TourDestination;

public interface TourDestinationDao {
	
	Integer addTourDestination(TourDestination tourDestination) throws DaoException;
	
	List<TourDestination> getTourDestination() throws DaoException;
	
	List<TourDestination> findByTourDestinationDesc(List<String> tourDestinations) throws DaoException;

	List<TourDestination> findByTourId(Integer tourId) throws DaoException;
}
