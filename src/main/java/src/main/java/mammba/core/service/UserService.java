/**
 * UserService.java - MAMMBA Application
 * 2018 All rights reserved.
 *
 */
package src.main.java.mammba.core.service;

import src.main.java.mammba.core.exception.ServiceException;
import src.main.java.mammba.model.MammbaUser;

/**
 * UserService interface for user functions.
 *
 * @author Mardolfh Del Rosario
 *
 */
public interface UserService {

	/**
	 * Register a Mammbauser.
	 *
	 * @param mammbaUser               Could be any mammba user.
	 * @throws ServiceException        business error logic.
	 */
	void register(MammbaUser mammbaUser) throws ServiceException;

	/**
	 * Get user details.
	 *
	 * @param username                 user name input.
	 * @return                         MammbaUser reference (Member/Partner).
	 * @throws ServiceException        business error logic.
	 */
	MammbaUser getUserDetails(String username) throws ServiceException;
}
