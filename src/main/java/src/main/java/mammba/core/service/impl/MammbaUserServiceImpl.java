/**
 * MammbaUserServiceImpl.java - MAMMBA Application
 * 2018 All rights reserved.
 *
 */
package src.main.java.mammba.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import src.main.java.mammba.core.dao.MammbaUserDao;
import src.main.java.mammba.core.exception.DaoException;
import src.main.java.mammba.core.exception.ServiceException;
import src.main.java.mammba.core.service.MammbaUserService;
import src.main.java.mammba.core.util.ObjectUtility;
import src.main.java.mammba.model.LoginModel;
import src.main.java.mammba.model.MammbaUser;

/**
 * Implements the MammbaUserService interface.
 *
 * @author Mardolfh Del Rosario
 *
 */
@Service
public class MammbaUserServiceImpl implements MammbaUserService {

    private static String ERR_ONE = "Invalid Mammba login";

    @Autowired
    @Qualifier("userDao")
    private MammbaUserDao userDao;

    @Autowired
    private ObjectUtility utility;

    /**
     * Checks and validates user credentials.
     *
     * @param loginModel                LoginModel reference.
     * @return                          true/false.
     * @throws ServiceException         business error logic.
     */
    @Override
    public boolean isLoginValid(LoginModel loginModel) throws ServiceException {
        try {
            return this.userDao.isLoginValid(loginModel);
        } catch(DaoException e) {
            throw new ServiceException(ERR_ONE);
        }
    }

    @Override
    public String getUserType(String userName) throws ServiceException {
        try {
            MammbaUser user = this.userDao.getUserDetails(userName);
            if (!this.utility.isNullOrEmpty(user)) {
                return user.getUserType();
            }
        } catch(DaoException e) {
            throw new ServiceException("Unable to acquire mammba user.");
        }

        return null;
    }
}
