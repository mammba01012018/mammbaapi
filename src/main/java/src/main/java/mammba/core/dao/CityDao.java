package src.main.java.mammba.core.dao;

import java.util.List;

import src.main.java.mammba.core.exception.DaoException;
import src.main.java.mammba.model.City;

public interface CityDao {
	
	public List<City> getCity(Integer countryId) throws DaoException;
	
}
