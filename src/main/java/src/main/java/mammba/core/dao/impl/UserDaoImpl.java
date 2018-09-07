/**
 * UserDaoImpl.java - MAMMBA Application
 * 2018 All rights reserved.
 *
 */
package src.main.java.mammba.core.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
    public void registerMember(Member member) throws DaoException {
        // TODO Auto-generated method stub

    }

}
