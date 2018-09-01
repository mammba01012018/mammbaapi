package src.main.java.mammba.core.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import src.main.java.mammba.core.dao.RegisterDao;
import src.main.java.mammba.core.exception.DaoException;
import src.main.java.mammba.core.exception.ServiceException;
import src.main.java.mammba.core.service.RegisterService;
import src.main.java.mammba.core.util.ObjectUtility;
import src.main.java.mammba.model.MammbaUser;
import src.main.java.mammba.model.Member;

@Service("registerMember")
public class RegisterMemberServiceImpl implements RegisterService {

	@Autowired
	private ObjectUtility objectUtility;

	@Autowired
	private RegisterDao registerDao;

	private static final Logger LOGGER = Logger.getLogger(RegisterMemberServiceImpl.class);
	private static String ERR_ONE = "No user type exists.";
	private static String ERR_TWO = "Member cannot be null";
	private static String ERR_THREE = "Error register Mammba User";
	private static String ERR_FOUR = "Member has incomplete details.";

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

	private void registerMember(Member member) throws DaoException, ServiceException {
	    boolean isMemberValidated = false;

	    //check required fields
        if (!this.objectUtility.isNullOrEmpty(member.getFirstName()) &&
            !this.objectUtility.isNullOrEmpty(member.getLastName()) &&
            !this.objectUtility.isNullOrEmpty(member.getMiddleInitial()) &&
            !this.objectUtility.isNullOrEmpty(member.getAddress1()) &&
            !this.objectUtility.isNullOrEmpty(member.getProvince()) &&
            !this.objectUtility.isNullOrEmpty(member.getCountry()) &&
            !this.objectUtility.isNullOrEmpty(member.getGender())) {

            isMemberValidated = true;
        }

        if (isMemberValidated) {
            this.registerDao.registerMember(member);
        } else {
            LOGGER.error(ERR_FOUR);
            throw new ServiceException(ERR_FOUR);
        }

	}
}
