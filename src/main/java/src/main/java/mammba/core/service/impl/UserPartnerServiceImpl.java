/**
 * UserPartnerServiceImpl.java - MAMMBA Application
 * 2018 All rights reserved.
 *
 */
package src.main.java.mammba.core.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import src.main.java.mammba.core.dao.MammbaUserDao;
import src.main.java.mammba.core.exception.DaoException;
import src.main.java.mammba.core.exception.ServiceException;
import src.main.java.mammba.core.service.UserService;
import src.main.java.mammba.core.util.ObjectUtility;
import src.main.java.mammba.model.MammbaUser;
import src.main.java.mammba.model.Partner;

/**
 * Implements User Partner service.
 *
 * @author Mardolfh Del Rosario
 *
 */
@Service("userPartnerService")
public class UserPartnerServiceImpl implements UserService {

    @Autowired
    private ObjectUtility objectUtility;

    @Autowired
    @Qualifier("userPartnerDao")
    private MammbaUserDao userPartnerDao;


    private static final Logger LOGGER = Logger.getLogger(UserMemberServiceImpl.class);
    private static String ERR_ONE = "No user type exists.";
    private static String ERR_TWO = "Member cannot be null";
    private static String ERR_THREE = "Error register Mammba User";
    private static String ERR_FOUR = "Partner has incomplete details.";
    private static String ERR_FIVE = "Unable to get Partner details.";


    @Override
    public void register(MammbaUser mammbaUser) throws ServiceException {

        try {
            if (mammbaUser != null) {
                if (mammbaUser instanceof Partner) {
                    this.registerPartner((Partner) mammbaUser);
                } else {
                    LOGGER.error(ERR_ONE);
                    throw new ServiceException(ERR_ONE);
                }

            } else {
                LOGGER.error(ERR_TWO);
                throw new ServiceException(ERR_TWO);
            }
        } catch (DaoException e) {
            LOGGER.error(ERR_THREE);
            throw new ServiceException(ERR_THREE);
        }
    }

    /**
     * Registers the new Member.
     *
     * @param member                       Member reference object.
     * @throws DaoException                Database error.
     * @throws ServiceException            Business logic error.
     */
    private void registerPartner(Partner partner) throws DaoException, ServiceException {
        boolean isPartnerValidated = false;



    }

    @Override
    public Partner getUserDetails(String username) throws ServiceException {
        try {
            MammbaUser user = null;
            Partner partnerUser = null;
            if (!this.objectUtility.isNullOrEmpty(username)) {
                user = this.userPartnerDao.getUserDetails(username);
                partnerUser = user instanceof Partner ? (Partner) user : null;
                if (partnerUser != null) {
                    return partnerUser;
                }

            }
        } catch(DaoException e) {
            throw new ServiceException(ERR_FIVE);
        }

        return null;
    }

}

