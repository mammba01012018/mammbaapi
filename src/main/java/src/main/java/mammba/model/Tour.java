package src.main.java.mammba.model;

import java.util.Date;
import java.util.List;

/**
 * @author edgarreyes
 *
 */
/**
 * @author edgarreyes
 *
 */
public class Tour {

	private Integer tourId;
	private String tourPackageName;
	private String placeFrom;
	private String placeTo;
	private Date startDate;
	private Date endDate;
	private Date startTime;
	private Date endTime;
	private List<TourDestination> tourDestination;
	private Date tourValidityStartDate;
	private Date tourValidityEndDate;
	private Integer tourPrice;
	
	private Double tourRatings;
	private Integer tourTotalRate;
	private Integer tourRatePerPax;
	private Integer tourDuration;
	private Boolean promo;
	
	private Integer tourMinSlot;
	private Integer tourMaxSlot;
	
	private Partner partner;
	//- free meals
	//- accommodation
	//- gas/toll (value: free, not included)
	//- places to visit
	
	private TourImages tourImages;
	private TourInclusions tourInclusions;
	
	private String tourType;	
	
	public String getPlaceFrom() {
		return placeFrom;
	}
	public void setPlaceFrom(String placeFrom) {
		this.placeFrom = placeFrom;
	}
	public String getPlaceTo() {
		return placeTo;
	}
	public void setPlaceTo(String placeTo) {
		this.placeTo = placeTo;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public Integer getTourTotalRate() {
		return tourTotalRate;
	}
	public void setTourTotalRate(Integer tourTotalRate) {
		this.tourTotalRate = tourTotalRate;
	}
	public Integer getTourRatePerPax() {
		return tourRatePerPax;
	}
	public void setTourRatePerPax(Integer tourRatePerPax) {
		this.tourRatePerPax = tourRatePerPax;
	}
	public TourImages getTourImages() {
		return tourImages;
	}
	public void setTourImages(TourImages tourImages) {
		this.tourImages = tourImages;
	}
	public TourInclusions getTourInclusions() {
		return tourInclusions;
	}
	public void setTourInclusions(TourInclusions tourInclusions) {
		this.tourInclusions = tourInclusions;
	}
	public Integer getTourId() {
		return tourId;
	}
	public void setTourId(Integer tourId) {
		this.tourId = tourId;
	}
	public Integer getTourDuration() {
		return tourDuration;
	}
	public void setTourDuration(Integer tourDuration) {
		this.tourDuration = tourDuration;
	}
	public Integer getTourMinSlot() {
		return tourMinSlot;
	}
	public void setTourMinSlot(Integer tourMinSlot) {
		this.tourMinSlot = tourMinSlot;
	}
	public Integer getTourMaxSlot() {
		return tourMaxSlot;
	}
	public void setTourMaxSlot(Integer tourMaxSlot) {
		this.tourMaxSlot = tourMaxSlot;
	}
	public Partner getPartner() {
		return partner;
	}
	public void setPartner(Partner partner) {
		this.partner = partner;
	}
	public String getTourPackageName() {
		return tourPackageName;
	}
	public void setTourPackageName(String tourPackageName) {
		this.tourPackageName = tourPackageName;
	}
	public String getTourType() {
		return tourType;
	}
	public void setTourType(String tourType) {
		this.tourType = tourType;
	}
	public Date getTourValidityStartDate() {
		return tourValidityStartDate;
	}
	public void setTourValidityStartDate(Date tourValidityStartDate) {
		this.tourValidityStartDate = tourValidityStartDate;
	}
	public Date getTourValidityEndDate() {
		return tourValidityEndDate;
	}
	public void setTourValidityEndDate(Date tourValidityEndDate) {
		this.tourValidityEndDate = tourValidityEndDate;
	}
	public Integer getTourPrice() {
		return tourPrice;
	}
	public void setTourPrice(Integer tourPrice) {
		this.tourPrice = tourPrice;
	}
	public Boolean getPromo() {
		return promo;
	}
	public void setPromo(Boolean promo) {
		this.promo = promo;
	}
	public Double getTourRatings() {
		return tourRatings;
	}
	public void setTourRatings(Double tourRatings) {
		this.tourRatings = tourRatings;
	}
	public List<TourDestination> getTourDestination() {
		return tourDestination;
	}
	public void setTourDestination(List<TourDestination> tourDestination) {
		this.tourDestination = tourDestination;
	}
	
	
}
