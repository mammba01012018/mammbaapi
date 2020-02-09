package src.main.java.mammba.core.dao;

import java.util.List;

import src.main.java.mammba.core.exception.DaoException;
import src.main.java.mammba.model.TourInclusions;

public interface TourInclusionsDao {
	Integer addTourInclusions(TourInclusions tourInclusions) throws DaoException;

	List<TourInclusions> getTourInclusions() throws DaoException;

	List<TourInclusions> findByTourInclusionsDesc(List<String> tourInclusion) throws DaoException;

	List<TourInclusions> findByTourId(Integer tourId)  throws DaoException;
}
