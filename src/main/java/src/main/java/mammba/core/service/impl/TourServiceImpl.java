package src.main.java.mammba.core.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import src.main.java.mammba.core.dao.TourDao;
import src.main.java.mammba.core.dao.TourDestinationDao;
import src.main.java.mammba.core.dao.impl.SecurityQuestionDaoImpl;
import src.main.java.mammba.core.exception.DaoException;
import src.main.java.mammba.core.exception.ServiceException;
import src.main.java.mammba.core.service.TourService;
import src.main.java.mammba.core.util.ErrorMessage;
import src.main.java.mammba.model.Tour;
import src.main.java.mammba.model.TourDestination;

@Service("TourServiceImpl")
public class TourServiceImpl implements TourService{
	
	@Autowired
	private TourDao tourDao;
	
	@Autowired
	private TourDestinationDao tourDestinationDao;
	
	  private static final Logger LOGGER = Logger.getLogger(TourServiceImpl.class);


	@Override
	public void addTour(Tour tour) throws ServiceException {
		// TODO Auto-generated method stub
		try {
			Integer tourId = this.tourDao.addTour(tour);
			for(TourDestination tourDestinations : tour.getTourDestination()) {
				tourDestinations.setTourId(tourId);
				tourDestinations.setTourDestinationDescription(tourDestinations.getTourDestinationDescription());
				Integer id = this.tourDestinationDao.addTourDestination(tourDestinations);
				tourDestinations.setTourDestinationId(id);
			}
			
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			throw new ServiceException(ErrorMessage.TOUR_ERR_ADD_ERR);
		}		
	}


	@Override
	public List<Tour> getTours() throws ServiceException {
		// TODO Auto-generated method stub
		try {
			return this.tourDao.getTours();
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			throw new ServiceException(ErrorMessage.TOUR_ERR_ADD_ERR);
		}
	}


	@Override
	public Tour getTour(Integer tourId) throws ServiceException {
		// TODO Auto-generated method stub
		try {
			return this.tourDao.getTour(tourId);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			throw new ServiceException(ErrorMessage.TOUR_ERR_ADD_ERR);
		}
	}

}
