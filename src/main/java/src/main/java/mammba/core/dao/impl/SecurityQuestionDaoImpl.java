/**
 * SecurityQuestionDaoImpl.java - MAMMBA Application
 * 2019 All rights reserved.
 *
 */
package src.main.java.mammba.core.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import src.main.java.mammba.core.dao.SecurityQuestionDao;
import src.main.java.mammba.core.exception.DaoException;
import src.main.java.mammba.core.util.QueryManager;

/**
 * This class will handle all security question related DAO functions.
 *
 * @author Mardolfh Del Rosario
 *
 */
@Repository
public class SecurityQuestionDaoImpl implements SecurityQuestionDao {

    @Autowired
    private QueryManager queryManager;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final Logger LOGGER = Logger.getLogger(SecurityQuestionDaoImpl.class);

    @Override
    public List<Map<String, Object>> getAllSecurityQuestions() throws DaoException {
        try {
            String sql = this.queryManager.getQuery("getAllSecurityQuestions");
            List<Map<String, Object>> questions = this.namedParameterJdbcTemplate.getJdbcOperations().queryForList(sql);

            if (questions != null && !questions.isEmpty()) {
                return questions;
            }
        } catch (SQLException | DataAccessException e) {
            LOGGER.error("getAllSecurityQuestions()-exception");
            throw new DaoException("MAMMBA[GASQ]-01-Database error");

        }

        return null;

    }

    @Override
    public boolean isSecurityQuestionAnswerValid(int userId, String answer) throws DaoException {
        try {
            String sql = this.queryManager.getQuery("IsAnswerSecurityCorrect");
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("userId", userId);
            params.addValue("securityAnswer", answer);

            String secAnswer = this.namedParameterJdbcTemplate.queryForObject(sql, params, String.class);

            if (secAnswer != null && !secAnswer.isEmpty() && secAnswer.equalsIgnoreCase(answer)) {
                return true;
            }
        }  catch (EmptyResultDataAccessException e) {
            return false;
        } catch (SQLException | DataAccessException e) {
            LOGGER.error("isSecurityQuestionAnswerValid()-exception");
            throw new DaoException("MAMMBA[ISQAV]-02-Database error", e);

        }

        return false;
    }

    @Override
    public void deactivateOldEntry(int userId, int questionId) throws DaoException {
        try {
            String sql = this.queryManager.getQuery("deactivatePreviousSecurityInfo");
            SqlParameterSource parameters = new MapSqlParameterSource()
                    .addValue("userId", userId)
                    .addValue("questionId", questionId);

           this.namedParameterJdbcTemplate.update(sql, parameters);
        } catch (DataAccessException | SQLException e) {
            LOGGER.error("deactivateOldEntry()-exception", e);
            throw new DaoException("MAMMBA[DOE]-03-Database error");
        }

    }

    @Override
    public void addNewQAUser(int userId, int questionId, String answer) throws DaoException {
        try {
            String sql = this.queryManager.getQuery("addNewSecurityInfo");
            SqlParameterSource parameters = new MapSqlParameterSource()
                    .addValue("userId", userId)
                    .addValue("questionId", questionId)
                    .addValue("securityAnswer", answer);

           this.namedParameterJdbcTemplate.update(sql, parameters);

        } catch (DataAccessException | SQLException e) {
            LOGGER.error("addNewQAUser()-exception", e);
            throw new DaoException("MAMMBA[ANQAU]-04-Database error");
        }

    }

    @Override
    public int getQuestionIdForUser(int userId) throws DaoException {
        try {
            String sql = this.queryManager.getQuery("getQuestionIdForUser");

            Integer questionId = this.namedParameterJdbcTemplate.getJdbcOperations()
                    .queryForList(sql, new Object[] {userId}, Integer.class).get(0);
            if (questionId != null && questionId > 0) {
                return questionId;
            }
        } catch (SQLException | DataAccessException e) {
            LOGGER.error("getQuestionIdForUser()-exception", e);
            throw new DaoException("MAMMBA[GQIFU]-05-Database error");

        }
        return 0;
    }

    @Override
    public int getUserId(String userName) throws DaoException {
        try {
            String sql = this.queryManager.getQuery("getUserIdFromSecurityQuestion");


            Integer userId = this.namedParameterJdbcTemplate.getJdbcOperations()
                    .queryForList(sql, new Object[] {userName, userName, userName}, Integer.class).get(0);
            if (userId != null && userId > 0) {
                return userId;
            }
        } catch (SQLException | DataAccessException e) {
            LOGGER.error("getUserId()-exception", e);
            throw new DaoException("MAMMBA[GUID]-06-Database error");

        }
        return 0;
    }

    @Override
    public String getQuestionForUserId(int userId) throws DaoException {
        try {
            String sql = this.queryManager.getQuery("getQuestionForUser");

            String question = this.namedParameterJdbcTemplate.getJdbcOperations()
                    .queryForList(sql, new Object[] {userId}, String.class).get(0);
            if (question != null && !question.isEmpty()) {
                return question;
            }
        } catch (SQLException | DataAccessException e) {
            LOGGER.error("getQuestionIdForUser()-exception", e);
            throw new DaoException("MAMMBA[GQFUI]-07-Database error");

        }

        return null;
    }

    @Override
    public void updateUserStatus(int userId, int userStatus) throws DaoException {
        try {
            String sql = this.queryManager.getQuery("userUpdateStatus");
            SqlParameterSource parameters = new MapSqlParameterSource()
                    .addValue("stat", userStatus)
                    .addValue("userId", userId);

           this.namedParameterJdbcTemplate.update(sql, parameters);

        } catch (DataAccessException | SQLException e) {
            LOGGER.error("updateUserStatusToTempPwd()-exception", e);
            throw new DaoException("MAMMBA[UUSTTP]-08-Database error");
        }

    }

    @Override
    public String getUserEmailByUserId(int userId) throws DaoException {
        try {
            String sql = this.queryManager.getQuery("getUserEmailByUserId");

            String emailAddress = this.namedParameterJdbcTemplate.getJdbcOperations()
                    .queryForList(sql, new Object[] {userId}, String.class).get(0);
            if (emailAddress != null && !emailAddress.isEmpty()) {
                return emailAddress;
            }
        } catch (SQLException | DataAccessException e) {
            LOGGER.error("getUserEmailByUserId()-exception", e);
            throw new DaoException("MAMMBA[GEBUI]-09-Database error");

        }

        return null;
    }

    @Override
    public void updateUserPwd(int userId, String hashedPwd) throws DaoException {
        try {
            String sql = this.queryManager.getQuery("updateUserPwd");
            SqlParameterSource parameters = new MapSqlParameterSource()
                    .addValue("pwd", hashedPwd)
                    .addValue("userId", userId);

           this.namedParameterJdbcTemplate.update(sql, parameters);

        } catch (DataAccessException | SQLException e) {
            LOGGER.error("updateUserToTempPwd()-exception", e);
            throw new DaoException("MAMMBA[UUTT]-10-Database error");
        }

    }

}
