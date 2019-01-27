/**
 * SecurityQuestionService.java - MAMMBA Application
 * 2019 All rights reserved.
 *
 */
package src.main.java.mammba.core.service;

import java.util.List;

import src.main.java.mammba.core.exception.ServiceException;

/**
 * This interface will handle all security question related Service functions.
 *
 * @author Mardolfh Del Rosario
 *
 */
public interface SecurityQuestionService {
    /**
     * Get all security questions in List format.
     *
     * @return                      List of questions in string format.
     * @throws ServiceException     logical error.
     */
    List<String> getAllSecurityQuestions() throws ServiceException;

    /**
     * Get security question id for user.
     *
     * @return                      Question id for user.
     * @throws ServiceException     logical error.
     */
    int getQuestionForUser(int userId) throws ServiceException;


    /**
     * Validates whether a security question has been answered correctly.
     *
     * @param userId                user to validate to.
     * @param answer                answer to security question.
     * @return                      true/false.
     * @throws ServiceException     logical error.
     */
    boolean isSecurityQuestionAnswerValid(int userId, String answer) throws ServiceException;


    /**
     * Add new entry of question and answer for user.
     *
     * @param userId                user reference.
     * @param questionId            question reference.
     * @param answer                user's answer.
     * @throws ServiceException     logical error.
     */
    void addNewQAUser(int userId, int questionId, String answer) throws ServiceException;

}
