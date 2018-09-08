/**
 * Partner.java - MAMMBA Application
 * 2018 All rights reserved.
 */
package src.main.java.mammba.model;

import java.sql.Date;

/**
 * Getter setter for Partner data
 * 
 * @author Michelle Pancipane
 *
 */
public class Partner extends MammbaUser {
	
	private String partnerName;
	private String iataNumber;
	private String companyName;
	private String telNumber;
	private String fax;
	private int numOfStaff;
	private String tinNumber;
	private String agencyType;
	private String typeOfService;
	private String contactPersonName;
	private String contactPersonPosition;
	private String contactPersonTelNum;
	private String contactPersonMobileNum;
	private String businessPermit;
	private Date businessPermitExpiry;
	private String DTI;
	private String SEC;
	
	public String getPartnerName() {
		return partnerName;
	}
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}
	public String getIataNumber() {
		return iataNumber;
	}
	public void setIataNumber(String iataNumber) {
		this.iataNumber = iataNumber;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getTelNumber() {
		return telNumber;
	}
	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public int getNumOfStaff() {
		return numOfStaff;
	}
	public void setNumOfStaff(int numOfStaff) {
		this.numOfStaff = numOfStaff;
	}
	public String getTinNumber() {
		return tinNumber;
	}
	public void setTinNumber(String tinNumber) {
		this.tinNumber = tinNumber;
	}
	public String getAgencyType() {
		return agencyType;
	}
	public void setAgencyType(String agencyType) {
		this.agencyType = agencyType;
	}
	public String getTypeOfService() {
		return typeOfService;
	}
	public void setTypeOfService(String typeOfService) {
		this.typeOfService = typeOfService;
	}
	public String getContactPersonName() {
		return contactPersonName;
	}
	public void setContactPersonName(String contactPersonName) {
		this.contactPersonName = contactPersonName;
	}
	public String getContactPersonPosition() {
		return contactPersonPosition;
	}
	public void setContactPersonPosition(String contactPersonPosition) {
		this.contactPersonPosition = contactPersonPosition;
	}
	public String getContactPersonTelNum() {
		return contactPersonTelNum;
	}
	public void setContactPersonTelNum(String contactPersonTelNum) {
		this.contactPersonTelNum = contactPersonTelNum;
	}
	public String getContactPersonMobileNum() {
		return contactPersonMobileNum;
	}
	public void setContactPersonMobileNum(String contactPersonMobileNum) {
		this.contactPersonMobileNum = contactPersonMobileNum;
	}
	public String getBusinessPermit() {
		return businessPermit;
	}
	public void setBusinessPermit(String businessPermit) {
		this.businessPermit = businessPermit;
	}
	public Date getBusinessPermitExpiry() {
		return businessPermitExpiry;
	}
	public void setBusinessPermitExpiry(Date businessPermitExpiry) {
		this.businessPermitExpiry = businessPermitExpiry;
	}
	public String getDTI() {
		return DTI;
	}
	public void setDTI(String dTI) {
		DTI = dTI;
	}
	public String getSEC() {
		return SEC;
	}
	public void setSEC(String sEC) {
		SEC = sEC;
	}
	
	@Override
	public String toString() {
		return "Partner [partnerName=" + this.partnerName + ", iataNumber=" + this.iataNumber + ", companyName=" + this.companyName
				+ ", telNumber=" + this.telNumber + ", fax=" + this.fax + ", numOfStaff=" + this.numOfStaff
				+ ", tinNumber=" + this.tinNumber + ", agencyType=" + this.agencyType + ", typeOfService=" + this.typeOfService
				+ ", contactPersonName=" + this.contactPersonName + ", contactPersonPosition=" + this.contactPersonPosition
				+ ", contactPersonTelNum=" + this.contactPersonTelNum + ", contactPersonMobileNum=" + this.contactPersonMobileNum
				+ ", businessPermit=" + this.businessPermit + ", businessPermitExpiry=" + this.businessPermitExpiry + ", DTI="
				+ this.DTI + ", SEC=" + this.SEC + ", userId=" + super.userId + ", address1=" + super.address1 + ", address2=" + super.address2
				+ ", province=" + super.province + ", country=" + super.country + ", emailAddress=" + super.emailAddress
				+ ", mobileNumber=" + super.mobileNumber + "]";
	}
	
	

}
