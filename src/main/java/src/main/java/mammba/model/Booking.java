package src.main.java.mammba.model;

import java.util.Date;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
public class Booking {
	
	@JsonInclude(Include.NON_NULL)
	private String bookingNumber;
	
	@JsonIgnore
	private String bookingStatus;
	
	@JsonIgnore
	private String bookingDepositSlip;
	
	private String bookingPickupAddress;
	
	private String bookingMobileNumber;
	
	private Date bookingPickuptime;
		
	private Date bookingDropOfftime;
	
	@JsonIgnore
	private String bookingTypeOfCar;
	
	@JsonIgnore
	private String bookingPlateNumber;
	
	@JsonIgnore
	private String bookingDriverName;
	
	@JsonIgnore
	private String bookingDriverContactNum;
	
	private Integer bookingNoOfGuest;
	
	private Integer memberId;
	
	private Integer tourId;	 
	
}
