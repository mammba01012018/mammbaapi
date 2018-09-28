/**
 * UserMemberServiceImpl.java - MAMMBA Application
 * 2018 All rights reserved.
 *
 */
package src.main.java.mammba.core.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
            !this.objectUtility.isNullOrEmpty(member.getMiddleInitial()) &&
            !this.objectUtility.isNullOrEmpty(member.getAddress1()) &&
            //!this.objectUtility.isNullOrEmpty(member.getAddress2()) &&
            !this.objectUtility.isNullOrEmpty(member.getProvince()) &&
            !this.objectUtility.isNullOrEmpty(member.getCountry()) &&
            !this.objectUtility.isNullOrEmpty(member.getGender())) {

            isMemberValidated = true;
        }

        if (isMemberValidated) {
            MemberDaoImpl userMemberDao = null;
            userMemberDao = (MemberDaoImpl) this.userDao;

            int memberId = userMemberDao.register(member);
            userMemberDao.addUserAcct(member.getUsername(), member.getPassword(), member.getEmailAddress(),
                    member.getMobileNumber(), "member", memberId, 0);

        } else {
            LOGGER.error(ERR_FOUR);
            throw new ServiceException(ERR_FOUR);
        }

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

}
