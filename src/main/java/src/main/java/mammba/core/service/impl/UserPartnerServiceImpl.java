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
import src.main.java.mammba.core.util.ErrorMessage;
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

    @Override
    public void register(MammbaUser mammbaUser) throws ServiceException {

        try {
            if (mammbaUser != null) {
                if (mammbaUser instanceof Partner) {
                    this.registerPartner((Partner) mammbaUser);
                } else {
                    LOGGER.error(ErrorMessage.PROFILE_ERR_NO_USR_TYP);
                    throw new ServiceException(ErrorMessage.PROFILE_ERR_NO_USR_TYP);
                }

            } else {
                LOGGER.error(ErrorMessage.PROFILE_ERR_MBR_NULL);
                throw new ServiceException(ErrorMessage.PROFILE_ERR_MBR_NULL);
            }
        } catch (DaoException e) {
            LOGGER.error(ErrorMessage.PROFILE_ERR_REG_ERR);
            throw new ServiceException(ErrorMessage.PROFILE_ERR_REG_ERR);
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
            LOGGER.error(ErrorMessage.PROFILE_ERR_REG_ERR);
            throw new ServiceException(ErrorMessage.PROFILE_ERR_REG_ERR);
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
            throw new ServiceException(ErrorMessage.PROFILE_ERR_PRTNR_DTL_LOAD);
        }

        return null;
    }

    @Override
    public void updateUser(MammbaUser mammbaUser) throws ServiceException {
        // TODO Auto-generated method stub

    }

    @Override
    public MammbaUser getUserDetails(int userId) throws ServiceException {
    	 MammbaUser user = null;
    	 try {
    		 user = this.userDao.getUserDetails(userId);
    		 
    	 } catch(DaoException e) {
             throw new ServiceException(ErrorMessage.PROFILE_ERR_ACS_DTA);
         }
        return user;
    }

}

