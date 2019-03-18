/**
 * UserPartnerServiceImpl.java - MAMMBA Application
 * 2018 All rights reserved.
 *
 */
package src.main.java.mammba.core.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import src.main.java.mammba.core.dao.MammbaUserDao;
import src.main.java.mammba.core.dao.impl.PartnerDaoImpl;
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
    private MammbaUserDao userDao;


    private static final Logger LOGGER = Logger.getLogger(UserPartnerServiceImpl.class);
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
        if (!this.objectUtility.isNullOrEmpty(partner.getUsername()) &&
            !this.objectUtility.isNullOrEmpty(partner.getPassword()) &&
            !this.objectUtility.isNullOrEmpty(partner.getEmailAddress()) &&
            !this.objectUtility.isNullOrEmpty(partner.getMobileNumber()) &&
            !this.objectUtility.isNullOrEmpty(partner.getAddress1()) &&
            !this.objectUtility.isNullOrEmpty(partner.getAgencyType()) &&
            !this.objectUtility.isNullOrEmpty(partner.getBusinessPermit()) &&
            !this.objectUtility.isNullOrEmpty(partner.getBusinessPermitExpiry()) &&
            !this.objectUtility.isNullOrEmpty(partner.getCompanyName()) &&
            !this.objectUtility.isNullOrEmpty(partner.getContactPersonMobileNum()) &&
            !this.objectUtility.isNullOrEmpty(partner.getContactPersonName()) &&
            !this.objectUtility.isNullOrEmpty(partner.getContactPersonPosition()) &&
            !this.objectUtility.isNullOrEmpty(partner.getContactPersonTelNum()) &&
            !this.objectUtility.isNullOrEmpty(partner.getCountry()) &&
            !this.objectUtility.isNullOrEmpty(partner.getDti()) &&
            //!this.objectUtility.isNullOrEmpty(partner.getFax()) &&
            !this.objectUtility.isNullOrEmpty(partner.getNumOfStaff()) &&
            !this.objectUtility.isNullOrEmpty(partner.getPartnerName()) &&
            !this.objectUtility.isNullOrEmpty(partner.getProvince()) &&
            !this.objectUtility.isNullOrEmpty(partner.getSec()) &&
            !this.objectUtility.isNullOrEmpty(partner.getTelNumber()) &&
            !this.objectUtility.isNullOrEmpty(partner.getTypeOfService())) {

            isPartnerValidated = true;
        }

        if (isPartnerValidated) {
            PartnerDaoImpl userPartnerDao = null;
            userPartnerDao = (PartnerDaoImpl) this.userDao;

            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(partner.getPassword());
            partner.setPassword(hashedPassword);

            int partnerId = userPartnerDao.register(partner);
            partner.setPartnerId(partnerId);
            int userId = userPartnerDao.addUserAcct(partner.getUsername(), partner.getPassword(),
                    partner.getEmailAddress(), partner.getMobileNumber(), "partner", 0, partnerId);
            partner.setUserId(userId);


        } else {
            LOGGER.error(ERR_FOUR);
            throw new ServiceException(ERR_FOUR);
        }

    }

    @Override
    public Partner getUserDetails(String username) throws ServiceException {
        try {
            MammbaUser user = null;
            Partner partnerUser = null;
            if (!this.objectUtility.isNullOrEmpty(username)) {
                user = this.userDao.getUserDetails(username);
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

    @Override
    public void updateUser(MammbaUser mammbaUser) throws ServiceException {
        // TODO Auto-generated method stub

    }

    @Override
    public MammbaUser getUserDetails(int userId) throws ServiceException {
        // TODO Auto-generated method stub
        return null;
    }

}

