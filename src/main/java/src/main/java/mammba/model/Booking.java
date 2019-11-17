package src.main.java.mammba.model;

import java.util.Date;

public class Booking {

	private String bookingNumber;
	
	private String bookingStatus;
	
	private String bookingDepositSlip;
	
	private String bookingPickupAddress;
	
	private String bookingMobileNumber;
	
	private Date bookingPickuptime;
	
	private Date bookingDropOfftime;
	
	private String bookingTypeOfCar;
	
	private String bookingPlateNumber;
	
	private String bookingDriverName;
	
	private String bookingDriverContactNum;
	
	private Integer bookingNoOfGuest;
	

	public String getBookingNumber() {
		return bookingNumber;
	}

	public void setBookingNumber(String bookingNumber) {
		this.bookingNumber = bookingNumber;
	}

	public String getBookingStatus() {
		return bookingStatus;
	}

	public void setBookingStatus(String bookingStatus) {
		this.bookingStatus = bookingStatus;
	}

	public String getBookingDepositSlip() {
		return bookingDepositSlip;
	}

	public void setBookingDepositSlip(String bookingDepositSlip) {
		this.bookingDepositSlip = bookingDepositSlip;
	}

	public String getBookingPickupAddress() {
		return bookingPickupAddress;
	}

	public void setBookingPickupAddress(String bookingPickupAddress) {
		this.bookingPickupAddress = bookingPickupAddress;
	}

	public String getBookingMobileNumber() {
		return bookingMobileNumber;
	}

	public void setBookingMobileNumber(String bookingMobileNumber) {
		this.bookingMobileNumber = bookingMobileNumber;
	}

	public Date getBookingPickuptime() {
		return bookingPickuptime;
	}

	public void setBookingPickuptime(Date bookingPickuptime) {
		this.bookingPickuptime = bookingPickuptime;
	}

	public Date getBookingDropOfftime() {
		return bookingDropOfftime;
	}

	public void setBookingDropOfftime(Date bookingDropOfftime) {
		this.bookingDropOfftime = bookingDropOfftime;
	}

	public String getBookingTypeOfCar() {
		return bookingTypeOfCar;
	}

	public void setBookingTypeOfCar(String bookingTypeOfCar) {
		this.bookingTypeOfCar = bookingTypeOfCar;
	}

	public String getBookingPlateNumber() {
		return bookingPlateNumber;
	}

	public void setBookingPlateNumber(String bookingPlateNumber) {
		this.bookingPlateNumber = bookingPlateNumber;
	}

	public String getBookingDriverName() {
		return bookingDriverName;
	}

	public void setBookingDriverName(String bookingDriverName) {
		this.bookingDriverName = bookingDriverName;
	}

	public String getBookingDriverContactNum() {
		return bookingDriverContactNum;
	}

	public void setBookingDriverContactNum(String bookingDriverContactNum) {
		this.bookingDriverContactNum = bookingDriverContactNum;
	}

	public Integer getBookingNoOfGuest() {
		return bookingNoOfGuest;
	}

	public void setBookingNoOfGuest(Integer bookingNoOfGuest) {
		this.bookingNoOfGuest = bookingNoOfGuest;
	}

}
