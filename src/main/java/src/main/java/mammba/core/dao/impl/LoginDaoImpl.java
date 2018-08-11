package src.main.java.mammba.core.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import src.main.java.mammba.core.dao.LoginDao;
import src.main.java.mammba.core.util.QueryManager;
import src.main.java.mammba.model.LoginModel;

@Repository
public class LoginDaoImpl implements LoginDao {

	@Autowired
	private QueryManager queryManager;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	
	private static final Logger LOGGER = Logger.getLogger(LoginDaoImpl.class);
	private static class LoginMapper implements RowMapper<LoginModel> {

		@Override
		public LoginModel mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			LoginModel loginModel = new LoginModel();
			loginModel.setUserEmail(rs.getString("USR_EMAIL"));
			loginModel.setPassword(rs.getString("USR_PWD"));
			
			return loginModel;
		}
	
	}

	
	@Override
	public boolean isLoginValid(LoginModel loginModel) {
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
				LOGGER.info("success string");
			}
		} catch (SQLException e) {
			isFound = false;
			LOGGER.info("failed string");
		}

		
		
		return isFound;
	}

}
