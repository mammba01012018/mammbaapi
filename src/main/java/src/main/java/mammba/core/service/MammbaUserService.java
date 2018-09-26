/**
 * MammbaUserService.java - MAMMBA Application
 * 2018 All rights reserved.
 *
 */
package src.main.java.mammba.core.service;

import src.main.java.mammba.core.exception.ServiceException;
import src.main.java.mammba.model.LoginModel;

/**
 * General use for mammba users.
 *
 * @author Mardolfh Del Rosario
 *
 */
public interface MammbaUserService {

    /**
     * Checks and validates user credentials.
     *
     * @param loginModel                LoginModel reference.
     * @return                          true/false.
     * @throws ServiceException         business error logic.
     */
     boolean isLoginValid(LoginModel loginModel) throws ServiceException;

     /**
      * Get the user type: member/partner/admin.
      *
      * @param userName                 User name to get the type.
      * @return                         member/partner/admin.
      * @throws ServiceException        business error logic.
      */
     String getUserType(String userName) throws ServiceException;

}
