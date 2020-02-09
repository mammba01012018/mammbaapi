package src.main.java.mammba.core.controller;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import src.main.java.mammba.core.exception.ServiceException;
import src.main.java.mammba.core.service.TourService;
import src.main.java.mammba.model.Tour;

@RestController
public class TourController {
	
	@Autowired
	private TourService tourService;

	private static final Logger LOGGER = Logger.getLogger(TourController.class);

	@PostMapping("tour/add-tour-package")
	public ResponseEntity<?> addTour(@RequestBody Tour tour) {
		LOGGER.info("Add Tour -start");
		String errorMsg = "";
		try {
			this.tourService.addTour(tour);
			JSONObject obj = new JSONObject(tour);

			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(obj.toString());
		} catch (ServiceException e) {
			LOGGER.error("Error adding Tour : " + tour.getTourPackageName(), e);
			errorMsg = e.getMessage();
		}

		LOGGER.info("Add Tour -end");
		return ResponseEntity.status(404)
				.body("Unable to add tour: " + tour.getTourPackageName()+ " due to " + errorMsg);

	}
	@GetMapping("tour/get-tour")
	public ResponseEntity<?> getTours(@RequestParam Integer tourId) {
		LOGGER.info("Get Tour -start");
		String errorMsg = "";
	
		try {
			Tour tour = this.tourService.getTour(tourId);
			JSONObject obj = new JSONObject(tour);
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(obj.toString());
		}catch(ServiceException e) {
			LOGGER.error("Error getting Tour : " + tourId, e);
			errorMsg = e.getMessage();
		}

		LOGGER.info("Get Tour -end");
		return ResponseEntity.status(404)
				.body("Unable to add tour: " + tourId+ " due to " + errorMsg);

	}
	
	@GetMapping("tour/get-all-tours")
	public ResponseEntity<?> getAllTours() {
		LOGGER.info("Get All Tours -start");
		String errorMsg = "";

		try {
			List<Tour> tourList = this.tourService.getTours();

			JSONArray obj = new JSONArray(tourList);

			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(obj.toString());
		} catch (ServiceException e) {
			LOGGER.error("Error getting All Tours : ", e);
			errorMsg = e.getMessage();
		}

		LOGGER.info("Get All Tours -end");
		return ResponseEntity.status(404).body("Unable to get  All Tours" + "due to " + errorMsg);

	}
	
	@PostMapping("tour/searchTours")
	public ResponseEntity<?> searchTours(@RequestBody Tour tour) {
		LOGGER.info("Search Tours -start");
		String errorMsg = "";

		try {
			List<Tour> tourList = this.tourService.searchTour(tour);

			JSONArray obj = new JSONArray(tourList);

			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(obj.toString());
		} catch (ServiceException e) {
			LOGGER.error("Error Searching Tours : ", e);
			errorMsg = e.getMessage();
		}

		LOGGER.info("Search Tours -end");
		return ResponseEntity.status(404).body("Unable to get  All Tours due to " + errorMsg);

	}

}
