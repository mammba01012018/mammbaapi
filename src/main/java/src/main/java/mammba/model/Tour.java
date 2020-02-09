package src.main.java.mammba.model;

import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * @author edgarreyes
 *
 */
/**
 * @author edgarreyes
 *
 */

@Data
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
	private Double tourPrice;	
	private Double tourRatings;
	private Integer tourTotalRate;
	private Integer tourRatePerPax;
	private Integer tourDuration;
	private Boolean promo;	
	private Integer tourMinSlot;
	private Integer tourMaxSlot;
	private Integer tourAvailableSlot;
	
	private Partner partner;
	//- free meals
	//- accommodation
	//- gas/toll (value: free, not included)
	//- places to visit
	
	private TourImages tourImages;
	private List<TourInclusions> tourInclusions;
	
	private String tourType;	
	
	
	
}
