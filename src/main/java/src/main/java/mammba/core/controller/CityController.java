package src.main.java.mammba.core.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import src.main.java.mammba.core.exception.ServiceException;
import src.main.java.mammba.core.service.CityService;
import src.main.java.mammba.model.City;

@RestController
public class CityController {
	
	@Autowired
	private CityService cityService;
	
	private static final Logger LOGGER = Logger.getLogger(CityController.class);
	
	
	@GetMapping("city/getCities/{countryId}")
	public ResponseEntity<?> getAllCities(@PathVariable Integer countryId) {
		 String errorMsg = "";
		try {
		 List<City> cityList = cityService.getCityList(countryId);
		 JSONArray obj = new JSONArray(cityList);
		 return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(obj.toString());
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			 LOGGER.error("Error fetching Cities", e);
	         errorMsg = e.getMessage();
		}
		return ResponseEntity.status(404).body("Unable to fetch Cities " + "due to " + errorMsg);
	}
	
}
