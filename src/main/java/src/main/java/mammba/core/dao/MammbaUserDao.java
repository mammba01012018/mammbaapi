/**
 * UserDao.java - MAMMBA Application
 * 2018 All rights reserved.
 *
 */
package src.main.java.mammba.core.dao;

import src.main.java.mammba.core.exception.DaoException;
import src.main.java.mammba.model.LoginModel;
import src.main.java.mammba.model.MammbaUser;

/**
 * This interface will serve as the User functions.
 *
 * @author Mardolfh Del Rosario
 *
 */
public interface MammbaUserDao {
    /**
     * Checks the login into the database.
     *
     * @param loginModel            LoginModel object reference.
     * @return                      true/false.
     */
	boolean isLoginValid(LoginModel loginModel) throws DaoException;

    /**
     * Register new mammba user.
     *
     * @param MammbaUser            Member/Partner object reference.
     * @return                      number of member/partner inserted
     */
    int register(MammbaUser user) throws DaoException;

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

    /**
     * Get user login details.
     *
     * @param userName              Login username.
     * @return                      MammbaUser - Partner/Member.
     */
	MammbaUser getUserDetails(String userName) throws DaoException;

	/**
     * validates if username exists.
     *
     * @param userName             user name to validate.
     * @return                     true/false
     * @throws DaoException        Db error.
     */
    boolean isUserNameExist(String userName) throws DaoException;

    /**
     * validates if email exists.
     *
     * @param email               email to validate.
     * @param userId               if > 0 then make an exception
     * @return                     true/false
     * @throws DaoException        Db error.
     */
    boolean isEmailExist(String email, int userId) throws DaoException;

    /**
     * validates if mobile number exists.
     *
     * @param mobileNum            mobile number to validate.
     * @param userId               if > 0 then make an exception
     * @return                     true/false
     * @throws DaoException        Db error.
     */
    boolean isMobileNoExist(String mobileNum, int userId) throws DaoException;

    /**
     * Update an existing mammba user.
     *
     * @param MammbaUser            Member/Partner object reference.
     * @return                      number of member/partner inserted
     */
    int update(MammbaUser user) throws DaoException;
}
