package src.main.java.mammba.core.service;

import java.util.List;

import src.main.java.mammba.core.exception.ServiceException;
import src.main.java.mammba.model.City;

public interface CityService {

	List<City> getCityList(Integer countryId) throws ServiceException;
	
}
