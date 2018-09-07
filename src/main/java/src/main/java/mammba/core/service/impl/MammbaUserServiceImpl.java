/**
 * MammbaUserServiceImpl.java - MAMMBA Application
 * 2018 All rights reserved.
 *
 */
package src.main.java.mammba.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import src.main.java.mammba.core.dao.UserDao;
import src.main.java.mammba.core.exception.DaoException;
import src.main.java.mammba.core.exception.ServiceException;
import src.main.java.mammba.core.service.MammbaUserService;
import src.main.java.mammba.model.LoginModel;

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
    private UserDao userDao;

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
}
