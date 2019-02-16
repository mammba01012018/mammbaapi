/**
 * UserMemberServiceImpl.java - MAMMBA Application
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
import src.main.java.mammba.core.dao.impl.MemberDaoImpl;
import src.main.java.mammba.core.exception.DaoException;
import src.main.java.mammba.core.exception.ServiceException;
import src.main.java.mammba.core.service.UserService;
import src.main.java.mammba.core.util.ObjectUtility;
import src.main.java.mammba.model.MammbaUser;
import src.main.java.mammba.model.Member;

/**
 * Implements User Member service.
 *
 * @author Mardolfh Del Rosario
 *
 */
@Service("userMemberService")
public class UserMemberServiceImpl implements UserService {

    @Autowired
    private ObjectUtility objectUtility;

    @Autowired
    @Qualifier("userMemberDao")
    private MammbaUserDao userDao;

    private static final Logger LOGGER = Logger.getLogger(UserMemberServiceImpl.class);
    private static String ERR_ONE = "No user type exists.";
    private static String ERR_TWO = "Member cannot be null";
    private static String ERR_THREE = "Error register Mammba User";
    private static String ERR_FOUR = "Member has incomplete details.";
    private static String ERR_FIVE = "Unable to get Member details.";
    private static String ERR_SIX = "Username already exist!";
    private static String ERR_SEVEN = "Error Accessing Data.";
    private static String ERR_EIGHT = "Email is already registered! Please use a different email.";
    private static String ERR_NINE = "Mobile number already registered!";
    private static String ERR_TEN = "Error Updating Member: ";

    @Override
    public void register(MammbaUser mammbaUser) throws ServiceException {

        try {
            if (mammbaUser != null) {
                if (mammbaUser instanceof Member) {
                    this.registerMember((Member) mammbaUser);
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

    @Override
    public void updateUser(MammbaUser mammbaUser) throws ServiceException {

        try {
            if (mammbaUser != null) {
                if (mammbaUser instanceof Member) {
                    this.updateMember((Member) mammbaUser);
                } else {
                    LOGGER.error(ERR_ONE);
                    throw new ServiceException(ERR_ONE);
                }

            } else {
                LOGGER.error(ERR_TWO);
                throw new ServiceException(ERR_TWO);
            }
        } catch (DaoException e) {
            LOGGER.error(ERR_TEN);
            throw new ServiceException(ERR_TEN + mammbaUser.getUserId());
        }
    }

    /**
     * Registers the new Member.
     *
     * @param member                       Member reference object.
     * @throws DaoException                Database error.
     * @throws ServiceException            Business logic error.
     */
    private void registerMember(Member member) throws DaoException, ServiceException {
        boolean isMemberValidated = false;

        //check required fields
        if (!this.objectUtility.isNullOrEmpty(member.getUsername()) &&
            !this.objectUtility.isNullOrEmpty(member.getPassword()) &&
            !this.objectUtility.isNullOrEmpty(member.getMobileNumber()) &&
            !this.objectUtility.isNullOrEmpty(member.getEmailAddress()) &&
            !this.objectUtility.isNullOrEmpty(member.getFirstName()) &&
            !this.objectUtility.isNullOrEmpty(member.getLastName()) &&
            !this.objectUtility.isNullOrEmpty(member.getAddress1()) &&
            !this.objectUtility.isNullOrEmpty(member.getProvince()) &&
            !this.objectUtility.isNullOrEmpty(member.getCountry()) &&
            !this.objectUtility.isNullOrEmpty(member.getGender()) &&
            !this.objectUtility.isNullOrEmpty(member.getBirthDate())) {

            isMemberValidated = true;
        }

        if (isMemberValidated && this.isUserNameExist(member.getUsername())) {
            throw new ServiceException(ERR_SIX);
        }

        if (isMemberValidated && this.isEmailExist(member.getEmailAddress(), 0)) {
            throw new ServiceException(ERR_EIGHT);
        }

        if (isMemberValidated && this.isMobileNoExist(member.getMobileNumber(), 0)) {
            throw new ServiceException(ERR_NINE);
        }

        if (isMemberValidated && this.isPasswordCompliant(member.getPassword())) {
            MemberDaoImpl userMemberDao = null;
            userMemberDao = (MemberDaoImpl) this.userDao;

            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(member.getPassword());
            member.setPassword(hashedPassword);

            int memberId = userMemberDao.register(member);
            member.setMemberId(memberId);
            int userId = userMemberDao.addUserAcct(member.getUsername(), member.getPassword(), member.getEmailAddress(),
                    member.getMobileNumber(), "member", memberId, 0);
            member.setUserId(userId);

        } else {
            LOGGER.error(ERR_FOUR);
            throw new ServiceException(ERR_FOUR);
        }

    }


    /**
     * Checks if the given username exists.
     *
     * @param userName              user name to validate.
     * @return                      true/false.
     * @throws ServiceException     error occurred when validating.
     */
    private boolean isUserNameExist(String userName) throws ServiceException {
        try {
            return this.userDao.isUserNameExist(userName);
        } catch (DaoException e) {
            throw new ServiceException(ERR_SEVEN);
        }

    }

    /**
     * Checks if the given email exists.
     *
     * @param email                email to validate.
     * @param userId                if > 0 then make an exception
     * @return                      true/false.
     * @throws ServiceException     error occurred when validating.
     */
    private boolean isEmailExist(String email, int userId) throws ServiceException {
        try {
            return this.userDao.isEmailExist(email, userId);
        } catch (DaoException e) {
            throw new ServiceException(ERR_SEVEN);
        }

    }

    /**
     * Checks if the given mobile number exists.
     *
     * @param mobileNum             mobile number to validate.
     * @param userId                if > 0 then make an exception
     * @return                      true/false.
     * @throws ServiceException     error occurred when validating.
     */
    private boolean isMobileNoExist(String mobileNum, int userId) throws ServiceException {
        try {
            return this.userDao.isMobileNoExist(mobileNum, userId);
        } catch (DaoException e) {
            throw new ServiceException(ERR_SEVEN);
        }

    }


    /**
     * Checks if password is compliant to rules.
     *
     * @param password               password must have at least 8 char, combination of alpha numeric and 1 symbol.
     * @return                       true/false.
     */
    private boolean isPasswordCompliant(String password) {
        boolean isValid = true;

        if (password != null && !password.isEmpty() && password.length() < 8) {
            isValid = false;
        } else if (password.matches("^[ A-Za-z0-9_@./#&+-]+$")) {
            isValid = true;
        } else {
            isValid = false;
        }

        return isValid;
    }

    @Override
    public Member getUserDetails(String username) throws ServiceException {
        try {
            MammbaUser user = null;
            Member memberUser = null;
            if (!this.objectUtility.isNullOrEmpty(username)) {
                user = this.userDao.getUserDetails(username);
                memberUser = user instanceof Member ? (Member) user : null;
                if (memberUser != null) {
                    return memberUser;
                }

            }
        } catch(DaoException e) {
            throw new ServiceException(ERR_FIVE);
        }
        return null;
    }

    /**
     * Updates the an existing Member.
     *
     * @param member                       Member reference object.
     * @throws DaoException                Database error.
     * @throws ServiceException            Business logic error.
     */
    private void updateMember(Member member) throws DaoException, ServiceException {
        boolean isMemberValidated = false;
        LOGGER.info(member);
        //check required fields
        if (!this.objectUtility.isNullOrEmpty(member.getPassword()) &&
            !this.objectUtility.isNullOrEmpty(member.getMobileNumber()) &&
            !this.objectUtility.isNullOrEmpty(member.getEmailAddress()) &&
            !this.objectUtility.isNullOrEmpty(member.getFirstName()) &&
            !this.objectUtility.isNullOrEmpty(member.getLastName()) &&
            !this.objectUtility.isNullOrEmpty(member.getAddress1()) &&
            !this.objectUtility.isNullOrEmpty(member.getUserId())) {

            isMemberValidated = true;
        }

        if (isMemberValidated && this.isEmailExist(member.getEmailAddress(), member.getUserId())) {
            throw new ServiceException(ERR_EIGHT);
        }

        if (isMemberValidated && this.isMobileNoExist(member.getMobileNumber(), member.getUserId())) {
            throw new ServiceException(ERR_NINE);
        }

        if (isMemberValidated && this.isPasswordCompliant(member.getPassword())) {
            MemberDaoImpl userMemberDao = null;
            userMemberDao = (MemberDaoImpl) this.userDao;

            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(member.getPassword());
            member.setPassword(hashedPassword);

            userMemberDao.update(member);

        } else {
            LOGGER.error(ERR_FOUR);
            throw new ServiceException(ERR_FOUR);
        }

    }

}
