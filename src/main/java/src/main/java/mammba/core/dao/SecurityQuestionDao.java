/**
 * SecurityQuestionDao.java - MAMMBA Application
 * 2019 All rights reserved.
 *
 */
package src.main.java.mammba.core.dao;

import java.util.List;
import java.util.Map;

import src.main.java.mammba.core.exception.DaoException;

/**
 * This interface will handle all security question related DAO functions.
 *
 * @author Mardolfh Del Rosario
 *
 */
public interface SecurityQuestionDao {
    /**
     * Get all security questions in the database.
     *
     * @return                      List of questions in string format.
     * @throws DaoException         DB error.
     */
    List<Map<String, Object>> getAllSecurityQuestions() throws DaoException;

    /**
     * Get security question for user in the database.
     *
     * @return                      questionId.
     * @throws DaoException         DB error.
     */
    int getQuestionIdForUser(int userId) throws DaoException;

    /**
     * Validates whether a security question has been answered correctly.
     *
     * @param userId                user to validate to.
     * @param answer                answer to security question.
     * @return                      true/false.
     * @throws DaoException         DB error.
     */
    boolean isSecurityQuestionAnswerValid(int userId, String answer) throws DaoException;


    /**
     * Deactivate any old entry of the user.
     *
     * @param userId                user reference.
     * @param questionId            question to invalidate.
     * @throws DaoException         DB error.
     */
    void deactivateOldEntry(int userId, int questionId) throws DaoException;

    /**
     * Add new entry of question and answer for user.
     *
     * @param userId                user reference.
     * @param questionId            question reference.
     * @param answer                user's answer.
     * @throws DaoException         DB error.
     */
    void addNewQAUser(int userId, int questionId, String answer) throws DaoException;

    /**
     * Get user id of username in the database.
     *
     * @param username              username/email/mobile.
     * @return                      userId.
     * @throws DaoException         DB error.
     */
    int getUserId(String userName) throws DaoException;


    /**
     * Get security question for user in the database.
     *
     * @param userId                user id to get the question.
     * @return                      question.
     * @throws DaoException         DB error.
     */
    String getQuestionForUserId(int userId) throws DaoException;

    /**
     * Set user account to temp password status.
     *
     * @param userId                user id to update status.
     * @throws DaoException         DB error.
     */
    void updateUserStatusToTempPwd(int userId) throws DaoException;


    /**
     * Get Member user's email address.
     *
     * @param userId                user id of the user.
     * @return                      user email address.
     * @throws DaoException         DB error.
     */
    String getUserEmailByUserId(int userId) throws DaoException;

}
