package src.main.java.mammba.model;

public class Member extends MammbaUser {

	private String firstName;
	private String lastName;
	private String middleInitial;
	private String gender;
	private String rate;

	public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleInitial() {
        return middleInitial;
    }

    public void setMiddleInitial(String middleInitial) {
        this.middleInitial = middleInitial;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

	@Override
    public String toString() {
        return "Member [firstName=" + firstName + ", lastName=" + lastName + ", middleInitial=" + middleInitial
                + ", gender=" + gender + ", rate=" + rate + "], " +
                "MammbaUser [memberId=" + memberId + ", address1=" + address1 + ", address2=" + address2 +
                ", province=" + province + ", country=" + country + "]";
    }

}
