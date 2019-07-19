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
import src.main.java.mammba.core.util.EmailUtility;
import src.main.java.mammba.core.util.ErrorMessage;
import src.main.java.mammba.core.util.ObjectUtility;
import src.main.java.mammba.model.EmailModel;
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

    @Autowired
    private EmailUtility emailUtility;

    private static final Logger LOGGER = Logger.getLogger(UserMemberServiceImpl.class);

    @Override
    public void register(MammbaUser mammbaUser) throws ServiceException {

        try {
            if (mammbaUser != null) {
                if (mammbaUser instanceof Member) {
                    this.registerMember((Member) mammbaUser);
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

    @Override
    public void updateUser(MammbaUser mammbaUser) throws ServiceException {

        try {
            if (mammbaUser != null) {
                if (mammbaUser instanceof Member) {
                    this.updateMember((Member) mammbaUser);
                } else {
                    LOGGER.error(ErrorMessage.PROFILE_ERR_NO_USR_TYP);
                    throw new ServiceException(ErrorMessage.PROFILE_ERR_NO_USR_TYP);
                }

            } else {
                LOGGER.error(ErrorMessage.PROFILE_ERR_MBR_NULL);
                throw new ServiceException(ErrorMessage.PROFILE_ERR_MBR_NULL);
            }
        } catch (DaoException e) {
            LOGGER.error(ErrorMessage.PROFILE_ERR_UPDT_MEM);
            throw new ServiceException(ErrorMessage.PROFILE_ERR_UPDT_MEM + mammbaUser.getUserId());
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
            LOGGER.info("all fields are complete.");
        }

        if (isMemberValidated && this.isUserNameExist(member.getUsername())) {
            throw new ServiceException(ErrorMessage.PROFILE_ERR_USR_EXST);
        }

        if (isMemberValidated && this.isEmailExist(member.getEmailAddress(), 0)) {
            throw new ServiceException(ErrorMessage.PROFILE_ERR_EML_EXST);
        }

        if (isMemberValidated && this.isMobileNoExist(member.getMobileNumber(), 0)) {
            throw new ServiceException(ErrorMessage.PROFILE_ERR_MBL_REG);
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

            this.emailWelcomeLetter(member);

        } else {
            LOGGER.error(ErrorMessage.PROFILE_ERR_MBR_PWD);
            throw new ServiceException(ErrorMessage.PROFILE_ERR_MBR_PWD);
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
            LOGGER.info("Checking username: " + userName);
            return this.userDao.isUserNameExist(userName);
        } catch (DaoException e) {
            throw new ServiceException(ErrorMessage.PROFILE_ERR_ACS_DTA);
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
            throw new ServiceException(ErrorMessage.PROFILE_ERR_ACS_DTA);
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
            throw new ServiceException(ErrorMessage.PROFILE_ERR_ACS_DTA);
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
            throw new ServiceException(ErrorMessage.PROFILE_ERR_MBR_DTL_LOAD);
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
        if (!this.objectUtility.isNullOrEmpty(member.getMobileNumber()) &&
            !this.objectUtility.isNullOrEmpty(member.getEmailAddress()) &&
            !this.objectUtility.isNullOrEmpty(member.getFirstName()) &&
            !this.objectUtility.isNullOrEmpty(member.getLastName()) &&
            !this.objectUtility.isNullOrEmpty(member.getAddress1()) &&
            !this.objectUtility.isNullOrEmpty(member.getUserId()) &&
            !this.objectUtility.isNullOrEmpty(member.getMemberId())) {

            isMemberValidated = true;
        }

        if (isMemberValidated && this.isEmailExist(member.getEmailAddress(), member.getUserId())) {
            throw new ServiceException(ErrorMessage.PROFILE_ERR_EML_EXST);
        }

        if (isMemberValidated && this.isMobileNoExist(member.getMobileNumber(), member.getUserId())) {
            throw new ServiceException(ErrorMessage.PROFILE_ERR_MBL_REG);
        }

        MemberDaoImpl userMemberDao = null;
        userMemberDao = (MemberDaoImpl) this.userDao;

        if (!this.objectUtility.isNullOrEmpty(member.getPassword()) &&
           isMemberValidated && this.isPasswordCompliant(member.getPassword())) {

            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(member.getPassword());
            member.setPassword(hashedPassword);

            userMemberDao.update(member);

        } else if (isMemberValidated) {
            userMemberDao.update(member);
        }  else {
            LOGGER.error(ErrorMessage.PROFILE_ERR_MBR_PWD);
            throw new ServiceException(ErrorMessage.PROFILE_ERR_MBR_PWD);
        }



    }

    @Override
    public MammbaUser getUserDetails(int userId) throws ServiceException {
        try {
            MammbaUser user = null;
            Member memberUser = null;
            user = this.userDao.getUserDetails(userId);
            memberUser = user instanceof Member ? (Member) user : null;
            if (memberUser != null) {
                return memberUser;
            }
        } catch(DaoException e) {
            throw new ServiceException(ErrorMessage.PROFILE_ERR_MBR_DTL_LOAD);
        }
        return null;
    }


    /**
     * Welcome letter.
     *
     * @param member                member reference.
     * @param tempPwd               tmp password to be sent.
     * @throws DaoException         DB Error.
     * @throws ServiceException
     */
    private void emailWelcomeLetter(MammbaUser user) throws ServiceException {
        Member member = (Member) user;
        String body = "Welcome " + member.getFirstName() +
                ", \n\nThank you for joining Mammba!  Your travel experience starts here!\n" +
                "Mammba app is absolutely free!  We hope you enjoy using the app!";

        EmailModel email = new EmailModel();
        email.setToRecipient(member.getEmailAddress());
        email.setSubject("Welcome " + member.getFirstName() + " " + member.getLastName() + "!");
        email.setBodyMessage(body);

        this.emailUtility.sendEmail(email);
    }

}
