/**
 * LoginModel.java - MAMMBA Application
 * 2018 All rights reserved.
 *
 */
package src.main.java.mammba.model;

import java.time.LocalDate;

/**
 * Getter setter for Login data.
 *
 * @author Mardolfh Del Rosario
 *
 */
public class LoginModel {

	private String userEmail;
	private String password;
	private String source;
	private LocalDate localDate;

	public String getUserEmail() {
		return this.userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getPassword() {
		return this.password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSource() {
		return this.source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public LocalDate getLocalDate() {
		return this.localDate;
	}
	public void setLocalDate(LocalDate localDate) {
		this.localDate = localDate;
	}

}
