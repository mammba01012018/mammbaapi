/**
 * Member.java - MAMMBA Application
 * 2018 All rights reserved.
 *
 */
package src.main.java.mammba.model;

/**
 * Getter setter for Member user data.
 *
 * @author Michelle Pancipane / Mardolfh Del Rosario
 *
 */
public class Member extends MammbaUser {

	private String firstName;
	private String lastName;
	private String middleInitial;
	private String gender;
	private String rate;

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

    public String getRate() {
        return this.rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

	@Override
    public String toString() {
        return "Member [firstName=" + this.firstName + ", lastName=" + this.lastName +
                ", middleInitial=" + this.middleInitial + ", gender=" + this.gender + ", rate=" + rate + "], " +
                "MammbaUser [memberId=" + super.userId + ", address1=" + super.address1 + ", address2=" +
                super.address2 + ", province=" + super.province + ", country=" + super.country + 
                ", emailAddress=" + super.emailAddress + ", mobileNumber=" + super.mobileNumber + "]";
    }

}
