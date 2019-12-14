/**
 * LoginServiceImpl.java - MAMMBA Application
 * 2018 All rights reserved.
 *
 */
package src.main.java.mammba.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import src.main.java.mammba.core.dao.MammbaUserDao;
import src.main.java.mammba.core.dao.SecurityQuestionDao;
import src.main.java.mammba.core.exception.DaoException;
import src.main.java.mammba.core.exception.ServiceException;
import src.main.java.mammba.core.service.LoginService;
import src.main.java.mammba.core.util.ErrorMessage;
import src.main.java.mammba.core.util.ObjectUtility;
import src.main.java.mammba.model.LoginModel;
import src.main.java.mammba.model.MammbaUser;

/**
 * Implements the LoginService interface.
 *
 * @author Mardolfh Del Rosario
 *
 */
@Service
public class LoginServiceImpl implements LoginService {


    @Autowired
    @Qualifier("userDao")
    private MammbaUserDao userDao;

    @Autowired
    private ObjectUtility utility;

    @Autowired
    private SecurityQuestionDao securityQuestionDao;

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
            throw new ServiceException(ErrorMessage.LOG_ERR_INVLD_LOGIN, e);
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
            throw new ServiceException(ErrorMessage.PROFILE_ERR_ACS_DTA, e);
        }

        return null;
    }

    @Override
    public void updatePassword(int userId, String pwd) throws ServiceException {
        try {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(pwd);

            this.securityQuestionDao.updateUserPwd(userId, hashedPassword);
            this.securityQuestionDao.updateUserStatus(userId, 1);
        }  catch(DaoException e) {
            throw new ServiceException(ErrorMessage.LOG_ERR_UPDT_PWD, e);
        }

    }
}
