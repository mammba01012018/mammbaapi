/**
 * MammbaUser.java - MAMMBA Application
 * 2018 All rights reserved.
 *
 */
package src.main.java.mammba.model;

/**
 * Getter setter for general Mammba user data.
 *
 * @author Michelle Pancipane / Mardolfh Del Rosario
 *
 */
public class MammbaUser {

    protected int memberId;
    protected String address1;
    protected String address2;
    protected String province;
    protected String country;

    public int getMemberId() {
        return this.memberId;
    }
    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }
    public String getAddress1() {
        return this.address1;
    }
    public void setAddress1(String address1) {
        this.address1 = address1;
    }
    public String getAddress2() {
        return this.address2;
    }
    public void setAddress2(String address2) {
        this.address2 = address2;
    }
    public String getProvince() {
        return this.province;
    }
    public void setProvince(String province) {
        this.province = province;
    }
    public String getCountry() {
        return this.country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

}
