/**
 * UserDaoImpl.java - MAMMBA Application
 * 2018 All rights reserved.
 *
 */
package src.main.java.mammba.core.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import src.main.java.mammba.core.dao.UserDao;
import src.main.java.mammba.core.exception.DaoException;
import src.main.java.mammba.core.util.QueryManager;
import src.main.java.mammba.model.LoginModel;
import src.main.java.mammba.model.Member;
import src.main.java.mammba.model.Partner;

/**
 * UserDao implementation for user functions.
 *
 * @author Michelle Pancipane / Mardolfh Del Rosario
 *
 */
@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private QueryManager queryManager;

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


	private static final Logger LOGGER = Logger.getLogger(UserDaoImpl.class);
	private static class LoginMapper implements RowMapper<LoginModel> {

		@Override
		public LoginModel mapRow(ResultSet rs, int rowNum) throws SQLException {
			LoginModel loginModel = new LoginModel();
			loginModel.setUserEmail(rs.getString("USR_EMAIL"));
			loginModel.setPassword(rs.getString("USR_PWD"));

			return loginModel;
		}
	}
	
	private static class UserMapper implements RowMapper<LoginModel> {

		@Override
		public LoginModel mapRow(ResultSet rs, int rowNum) throws SQLException {
			LoginModel loginModel = new LoginModel();
			loginModel.setUserEmail(rs.getString("user_userName"));
			loginModel.setPassword(rs.getString("user_password"));

			return loginModel;
		}
	}

	@Override
	public boolean isLoginValid(LoginModel loginModel) throws DaoException {
		boolean isFound = false;
		try {
			String sql = this.queryManager.getQuery("validateUser");
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("userEmail", loginModel.getUserEmail());
			params.addValue("userPwd", loginModel.getPassword());
			List<LoginModel> user = this.namedParameterJdbcTemplate.query(sql,
					params, new LoginMapper());
			if (user != null && !user.isEmpty() &&
				user.get(0).getUserEmail().equals(loginModel.getUserEmail()) &&
				user.get(0).getPassword().equals(loginModel.getPassword())) {
				isFound = true;
				LOGGER.info("isLoginValid()-valid authentication");
			}
		} catch (SQLException | DataAccessException e) {
			LOGGER.error("isLoginValid()-exception");
			throw new DaoException("MAMMBA[UDI]-01-Database error");

		}

		return isFound;
	}


    @Override
    public int registerMember(Member member) throws DaoException {
    	try {
    		Map<String, Object> paramMap = new HashMap<String, Object>();
    		paramMap.put("firstName", member.getFirstName());
            paramMap.put("lastName", member.getLastName());
            paramMap.put("middleInitial", member.getMiddleInitial());
            paramMap.put("gender", member.getGender());
            paramMap.put("address1", member.getAddress1());
            paramMap.put("address2", member.getAddress2());
            paramMap.put("province", member.getProvince());
            paramMap.put("country", member.getCountry());
            paramMap.put("emailAddress", member.getEmailAddress());
            paramMap.put("mobileNumber", member.getMobileNumber());
            return this.namedParameterJdbcTemplate.update("registerMember", paramMap);  
    	} catch (DataAccessException e) {
    		LOGGER.error("registerMember()-exception");
			throw new DaoException("MAMMBA[UDI]-01-Database error");
    	}

    }

	@Override
	public int registerPartner(Partner partner) throws DaoException {
		try {
    		Map<String, Object> paramMap = new HashMap<String, Object>();
    		paramMap.put("partnerName", partner.getPartnerName());
    		paramMap.put("iataNumber", partner.getIataNumber());
    		paramMap.put("companyName", partner.getCompanyName());
    		paramMap.put("address1", partner.getAddress1());
    		paramMap.put("province", partner.getProvince());
    		paramMap.put("country", partner.getCountry());
    		paramMap.put("telNumber", partner.getTelNumber());
    		paramMap.put("mobileNumber", partner.getMobileNumber());
    		paramMap.put("fax", partner.getFax());
    		paramMap.put("emailAddress", partner.getEmailAddress());
    		paramMap.put("numOfStaff", partner.getNumOfStaff());
    		paramMap.put("tinNumber", partner.getTinNumber());
    		paramMap.put("agencyType", partner.getAgencyType());
    		paramMap.put("typeOfService", partner.getTypeOfService());
    		paramMap.put("contactPersonName", partner.getContactPersonName());
    		paramMap.put("contactPersonPosition", partner.getContactPersonPosition());
    		paramMap.put("contactPersonTelNum", partner.getContactPersonTelNum());
    		paramMap.put("contactPersonMobileNum", partner.getContactPersonMobileNum());
    		paramMap.put("businessPermit", partner.getBusinessPermit());
    		paramMap.put("businessPermitExpiry", partner.getBusinessPermitExpiry());
    		paramMap.put("DTI", partner.getDTI());
    		paramMap.put("SEC", partner.getSEC());
            return this.namedParameterJdbcTemplate.update("registerMember", paramMap);  
    	} catch (DataAccessException e) {
    		LOGGER.error("registerMember()-exception");
			throw new DaoException("MAMMBA[UDI]-01-Database error");
    	}
	}

	@Override
	public int addUserAcct(String username, String password, String email,  String mobileNumber,
			String userType, int memberId, int partnerId) throws DaoException {
		
		try {
			java.sql.Date dateTime = new java.sql.Date(new java.util.Date().getTime());
    		Map<String, Object> paramMap = new HashMap<String, Object>();
    		paramMap.put("username", username);
            paramMap.put("password", password);
            paramMap.put("email", email);
            paramMap.put("creTime", dateTime);
            paramMap.put("mobileNumber", mobileNumber);
            paramMap.put("userType", userType);
            paramMap.put("memberId", memberId);
            paramMap.put("partnerId", partnerId);
            return this.namedParameterJdbcTemplate.update("addUserAcct", paramMap);  
    	} catch (DataAccessException e) {
    		LOGGER.error("registerMember()-exception");
			throw new DaoException("MAMMBA[UDI]-01-Database error");
    	}
	}


	@Override
	public boolean isUserValid(LoginModel loginModel) throws DaoException {
		boolean isFound = false;
		try {
			String sql = this.queryManager.getQuery("getUser");
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("username", loginModel.getUserEmail());
			params.addValue("password", loginModel.getPassword());
			List<LoginModel> user = this.namedParameterJdbcTemplate.query(sql,
					params, new LoginMapper());
			if (user != null && !user.isEmpty() &&
				user.get(0).getUserEmail().equals(loginModel.getUserEmail()) &&
				user.get(0).getPassword().equals(loginModel.getPassword())) {
				isFound = true;
				LOGGER.info("isLoginValid()-valid authentication");
			}
		} catch (SQLException | DataAccessException e) {
			LOGGER.error("isLoginValid()-exception");
			throw new DaoException("MAMMBA[UDI]-01-Database error");

		}

		return isFound;
	}
}
