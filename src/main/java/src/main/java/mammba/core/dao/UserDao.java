/**
 * UserDao.java - MAMMBA Application
 * 2018 All rights reserved.
 *
 */
package src.main.java.mammba.core.dao;

import src.main.java.mammba.core.exception.DaoException;
import src.main.java.mammba.model.LoginModel;
import src.main.java.mammba.model.Member;

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
     */
    void registerMember(Member member) throws DaoException;
}
