/**
 * MammbaUserDaoImpl.java - MAMMBA Application
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import src.main.java.mammba.core.dao.MammbaUserDao;
import src.main.java.mammba.core.exception.DaoException;
import src.main.java.mammba.core.util.QueryManager;
import src.main.java.mammba.model.LoginModel;
import src.main.java.mammba.model.MammbaUser;

/**
 * UserDao implementation for user functions.
 *
 * @author Michelle Pancipane / Mardolfh Del Rosario
 *
 */
@Repository("userDao")
public class MammbaUserDaoImpl implements MammbaUserDao {

    @Autowired
    protected QueryManager queryManager;

    @Autowired
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    private static final Logger LOGGER = Logger.getLogger(MammbaUserDaoImpl.class);
    private static class LoginMapper implements RowMapper<LoginModel> {

        @Override
        public LoginModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            LoginModel loginModel = new LoginModel();
            loginModel.setUserEmail(rs.getString("user_userName"));
            loginModel.setPassword(rs.getString("user_password"));

            return loginModel;
        }
    }

    private static class UserMapper implements RowMapper<MammbaUser> {

        @Override
        public MammbaUser mapRow(ResultSet rs, int rowNum) throws SQLException {
            MammbaUser mammbaUser = new MammbaUser();
            mammbaUser.setUsername(rs.getString("user_userName"));
            mammbaUser.setPassword(rs.getString("user_password"));
            mammbaUser.setUserType(rs.getString("user_type"));
            mammbaUser.setEmailAddress(rs.getString("user_email"));
            mammbaUser.setMobileNumber(rs.getString("user_mobileNumber"));

            return mammbaUser;
        }
    }

    @Override
    public boolean isLoginValid(LoginModel loginModel) throws DaoException {
        boolean isFound = false;
        try {
            String sql = this.queryManager.getQuery("getUserDetailByUserName");
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("username", loginModel.getUserEmail());

            List<LoginModel> user = this.namedParameterJdbcTemplate.query(sql,
                    params, new LoginMapper());
            if (user != null && !user.isEmpty()) {
                PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                if (passwordEncoder.matches(loginModel.getPassword(), user.get(0).getPassword())) {
                    isFound = true;
                    LOGGER.info("isLoginValid()-valid authentication");
                }
            }
        } catch (SQLException | DataAccessException e) {
            LOGGER.error("isLoginValid()-exception");
            throw new DaoException("MAMMBA[UDI]-01-Database error");

        }

        return isFound;
    }

    @Override
    public int register(MammbaUser user) throws DaoException {
        return 0;
    }

    @Override
    public int addUserAcct(String username, String password, String email,  String mobileNumber,
            String userType, int memberId, int partnerId) throws DaoException {

        try {
            java.sql.Date dateTime = new java.sql.Date(new java.util.Date().getTime());
            String sql = this.queryManager.getQuery("addUserAcct");
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("username", username);
            paramMap.put("password", password);
            paramMap.put("email", email);
            paramMap.put("creTime", dateTime);
            paramMap.put("mobileNumber", mobileNumber);
            paramMap.put("userType", userType);
            paramMap.put("memberId", memberId);
            paramMap.put("partnerId", partnerId);
            return this.namedParameterJdbcTemplate.update(sql, paramMap);
        } catch (DataAccessException | SQLException e) {
            LOGGER.error("registerMember()-exception", e);
            throw new DaoException("MAMMBA[UDI]-01-Database error");
        }
    }

    @Override
    public MammbaUser getUserDetails(String userName) throws DaoException {
        try {
            String sql = this.queryManager.getQuery("getUserDetailByUserName");
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("username", userName);
            List<MammbaUser> member = this.namedParameterJdbcTemplate.query(sql,
                    params, new UserMapper());
            if (member != null && !member.isEmpty()) {
                return member.get(0);
            }
        } catch (SQLException | DataAccessException e) {
            LOGGER.error("getUserDetails()-exception");
            throw new DaoException("MAMMBA[UDI]-01-Database error");

        }

        return null;
    }
}
