package src.main.java.mammba.core.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import src.main.java.mammba.core.dao.TourDao;
import src.main.java.mammba.core.dao.TourDestinationDao;
import src.main.java.mammba.core.dao.TourInclusionsDao;
import src.main.java.mammba.core.dao.impl.SecurityQuestionDaoImpl;
import src.main.java.mammba.core.exception.DaoException;
import src.main.java.mammba.core.exception.ServiceException;
import src.main.java.mammba.core.service.TourService;
import src.main.java.mammba.core.service.UserService;
import src.main.java.mammba.core.util.ErrorMessage;
import src.main.java.mammba.model.Partner;
import src.main.java.mammba.model.Tour;
import src.main.java.mammba.model.TourDestination;
import src.main.java.mammba.model.TourInclusions;

@Service("TourServiceImpl")
public class TourServiceImpl implements TourService{
	
	@Autowired
	private TourDao tourDao;
	
	@Autowired
	private TourDestinationDao tourDestinationDao;
	
	@Autowired
	private TourInclusionsDao tourInclusionsDao;
	
	@Autowired 
	UserService userPartnerService;
	
		
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
			for(TourInclusions tourInclusions : tour.getTourInclusions()) {
				tourInclusions.setTourId(tourInclusions.getTourId());
				tourInclusions.setTourInclusions(tourInclusions.getTourInclusions());
				Integer id = this.tourInclusionsDao.addTourInclusions(tourInclusions);
				tourInclusions.setTourInclusionId(id);
				
			}
			
		} catch (DaoException e) {
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
			Tour tour =  this.tourDao.getTour(tourId);
			
			List<TourDestination> tourDestination = tourDestinationDao.findByTourId(tour.getTourId());
			List<TourInclusions> tourInclusions = tourInclusionsDao.findByTourId(tour.getTourId());
			tour.setTourDestination(tourDestination);
			tour.setTourInclusions(tourInclusions);
			if(tour.getPartner()!=null) {
				Partner partner = (Partner) userPartnerService.getUserDetails(tour.getPartner().getPartnerId());
				partner.setPassword(null);
				tour.setPartner(partner);
			}
			
			return tour;
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			throw new ServiceException(ErrorMessage.TOUR_ERR_ADD_ERR);
		}
	}

	@Override
	public List<Tour> searchTour(Tour tour) throws ServiceException {
		// TODO Auto-generated method stub
		List<Tour> tourList = null;
		try {
			if (tour != null && tour.getTourDestination() != null) {
				List<String> destinationList = tour.getTourDestination().stream().map(TourDestination :: getTourDestinationDescription).collect(Collectors.toList());
				List<TourDestination> tourDestinationList = tourDestinationDao.findByTourDestinationDesc(destinationList);
				List<Integer> tourIDList = tourDestinationList.stream().map(TourDestination :: getTourId).collect(Collectors.toList());
				
				 tourList = this.tourDao.searchTours(tour,tourIDList);

			} else {
				 tourList = this.tourDao.searchTours(tour);
			}
			
			for(Tour curTour : tourList) {
				
				List<TourDestination> tourDestination = tourDestinationDao.findByTourId(curTour.getTourId());
				List<TourInclusions> tourInclusions = tourInclusionsDao.findByTourId(curTour.getTourId());
				curTour.setTourDestination(tourDestination);
				curTour.setTourInclusions(tourInclusions);
			}			
			
			return tourList;
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			throw new ServiceException(ErrorMessage.TOUR_ERR_ADD_ERR);
		}
	}

}
