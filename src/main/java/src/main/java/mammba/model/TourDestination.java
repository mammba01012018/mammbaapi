package src.main.java.mammba.model;

public class TourDestination {

	private Integer tourId;
	private Integer tourDestinationId;
	private String  tourDestinationDescription;
	public Integer getTourId() {
		return tourId;
	}
	public void setTourId(Integer tourId) {
		this.tourId = tourId;
	}
	public Integer getTourDestinationId() {
		return tourDestinationId;
	}
	public void setTourDestinationId(Integer tourDestinationId) {
		this.tourDestinationId = tourDestinationId;
	}
	public String getTourDestinationDescription() {
		return tourDestinationDescription;
	}
	public void setTourDestinationDescription(String tourDestinationDescription) {
		this.tourDestinationDescription = tourDestinationDescription;
	}	
}
