package src.main.java.mammba.model;

import java.util.Date;

import lombok.Data;

@Data
public class City {

	public Integer id;
	public Integer region_id;
	public Integer country_id;
	public String name;
	public String createdBy;
	public String updatedBy;
	public Date createdTimeStamp;
	public Date updatedTimeStamp;

}
