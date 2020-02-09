package src.main.java.mammba.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import src.main.java.mammba.core.dao.CityDao;
import src.main.java.mammba.core.exception.DaoException;
import src.main.java.mammba.core.exception.ServiceException;
import src.main.java.mammba.core.service.CityService;
import src.main.java.mammba.core.util.ErrorMessage;
import src.main.java.mammba.model.City;

@Service("cityServiceImpl")
public class CityServiceImpl implements CityService {
	
	@Autowired
	private CityDao cityDao;

	@Override
	public List<City> getCityList(Integer countryId) throws ServiceException  {
	 try {
		return	cityDao.getCity(countryId);
	} catch (DaoException e) {
		throw new ServiceException(ErrorMessage.CITY_ERR_FETCH_ERR);
	}
		
	}
	
	
}
