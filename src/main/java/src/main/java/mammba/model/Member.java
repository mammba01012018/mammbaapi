/**
 * Member.java - MAMMBA Application
 * 2018 All rights reserved.
 *
 */
package src.main.java.mammba.model;

import java.util.Date;

/**
 * Getter setter for Member user data.
 *
 * @author Michelle Pancipane / Mardolfh Del Rosario
 *
 */
public class Member extends MammbaUser {

    private int memberId;
	private String firstName;
	private String lastName;
	private String middleInitial;
	private String gender;
	private String address2;
	private String rate;
	private Date birthDate;
	protected String province;
    protected String country;



	public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleInitial() {
        return this.middleInitial;
    }

    public void setMiddleInitial(String middleInitial) {
        this.middleInitial = middleInitial;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress2() {
        return this.address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getRate() {
        return this.rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public int getMemberId() {
        return this.memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

	public Date getBirthDate() {
        return this.birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Override
	public String toString() {
		return "Member [firstName=" + firstName + ", lastName=" + lastName + ", middleInitial=" + middleInitial
				+ ", gender=" + gender + ", address2=" + address2 + ", province=" + province + ", country=" + country
				+ ", rate=" + rate + ", memberId=" + memberId + ", address1=" + address1 + ", username=" + username
				+ ", password=" + password + ", mobileNumber=" + mobileNumber + ", emailAddress=" + emailAddress + "]";
	}
}
