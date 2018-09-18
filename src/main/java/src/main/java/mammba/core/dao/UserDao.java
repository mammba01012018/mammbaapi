/**
 * UserDao.java - MAMMBA Application
 * 2018 All rights reserved.
 *
 */
package src.main.java.mammba.core.dao;

import java.sql.Date;

import src.main.java.mammba.core.exception.DaoException;
import src.main.java.mammba.model.LoginModel;
import src.main.java.mammba.model.Member;
import src.main.java.mammba.model.Partner;

/**
 * This interface will serve as the User functions.
 *
 * @author Mardolfh Del Rosario
 *
 */
public interface UserDao {
    /**
     * Checks the login into the database.
     *
     * @param loginModel            LoginModel object reference.
     * @return                      true/false.
     */
	boolean isLoginValid(LoginModel loginModel) throws DaoException;

	/**
     * Captures new member details.
     *
     * @param member                Member object reference.
     * @return                      number of user inserted
     */
    int registerMember(Member member) throws DaoException;
    
    /**
     * Captures new partner details.
     *
     * @param partner                Partner object reference.
     * @return                      number of partner inserted
     */
    int registerPartner(Partner partner) throws DaoException;
    
    /**
     * Add account details.
     *
     * @param username              accountname in logging in
     * @param password              encrypted String
     * @param email	                emailaddress of user
     * @param creTime               Date created the account
     * @param mobileNumber          String phone number
     * @param userType              Partner / Member
     * @param memberId              member Id if account is member
     * @param partnerId             partner Id if account is partner
     * @return                      number account inserted
     */
    int addUserAcct(String username, String password, String email, String mobileNumber,
			String userType, int memberId, int partnerId ) throws DaoException;
    
}
