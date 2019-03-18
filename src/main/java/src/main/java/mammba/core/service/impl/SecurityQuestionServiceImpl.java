package src.main.java.mammba.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import src.main.java.mammba.core.dao.SecurityQuestionDao;
import src.main.java.mammba.core.exception.DaoException;
import src.main.java.mammba.core.exception.ServiceException;
import src.main.java.mammba.core.service.SecurityQuestionService;
import src.main.java.mammba.core.util.EmailUtility;
import src.main.java.mammba.model.EmailModel;

@Service
public class SecurityQuestionServiceImpl implements SecurityQuestionService {

    @Autowired
    private SecurityQuestionDao securityQuestionDao;

    @Autowired
    private EmailUtility emailUtility;


    private static final Logger LOGGER = Logger.getLogger(SecurityQuestionServiceImpl.class);

    @Override
    public List<String> getAllSecurityQuestions() throws ServiceException {
        try {
            List<String> listOfQuestions = new ArrayList<>();
            List<Map<String, Object>> questionaires = this.securityQuestionDao.getAllSecurityQuestions();
            if (questionaires != null && !questionaires.isEmpty()) {
                for (Map<String, Object> oMap : questionaires) {
                    Integer qstnId = (Integer) oMap.get("secQstnId");
                    String question = (String) oMap.get("secDesc");

                    listOfQuestions.add(qstnId.toString() + "=" + question);
                }

                return listOfQuestions;
            } else {
                throw new ServiceException("No questions found.");
            }

        } catch (DaoException e) {
            throw new ServiceException("Unable to load questions.", e);
        }
    }

    @Override
    @Transactional
    public boolean isSecurityQuestionAnswerValid(int userId, String answer) throws ServiceException {
        if (userId > 0 && answer != null && !answer.isEmpty()) {
            try {
                boolean isAnswerCorrect = this.securityQuestionDao.isSecurityQuestionAnswerValid(userId, answer);
                if (isAnswerCorrect) {
                    this.emailUserForTempPwd(userId, this.applyAccountRetrievalProcess(userId));
                }

                return isAnswerCorrect;
            } catch (DaoException e) {
                throw new ServiceException("Unable to validate answer for user.", e);
            }
        } else {
            throw new ServiceException("Missing parameters.");
        }
    }

    @Override
    public void addNewQAUser(int userId, int questionId, String answer) throws ServiceException {
        if (userId > 0 && questionId > 0 && answer != null && !answer.isEmpty()) {
            try {
                //deactivate all previous question and answer for user
                this.securityQuestionDao.deactivateOldEntry(userId, questionId);
                //map new q and a then add answer to question
                this.securityQuestionDao.addNewQAUser(userId, questionId, answer);

            } catch (DaoException e) {
                throw new ServiceException("Unable to insert new question and answer for user: " + userId, e);
            }
        } else {
            throw new ServiceException("Invalid Parameters to add");
        }

    }

    @Override
    public int getQuestionForUserId(int userId) throws ServiceException {
        if (userId > 0) {
            try {
                int questionId = this.securityQuestionDao.getQuestionIdForUser(userId);
                if (questionId <= 0) {
                    throw new ServiceException("Question NOT found for user: " + userId);
                }
                return questionId;
            } catch (DaoException e) {
                throw new ServiceException("Unable to get question id for user: "  + userId, e);
            }
        } else {
            throw new ServiceException("Unable to get question for user 0");
        }
    }

    @Override
    public int getUserId(String userName) throws ServiceException {
        if (userName != null && !userName.isEmpty()) {
            try {
                int userId = this.securityQuestionDao.getUserId(userName);
                return userId;
            } catch (DaoException e) {
                throw new ServiceException("Unable to get user id for "  + userName, e);
            }
        } else {
            throw new ServiceException("Unable to get user.");
        }
    }

    @Override
    @Transactional
    public Map<Integer, String> getQuestionForUserName(String userName) throws ServiceException {
        if (userName != null && !userName.isEmpty()) {
            try {
                Map<Integer, String> userQuestion = new HashMap<>();
                int userId = this.securityQuestionDao.getUserId(userName);
                String question = this.securityQuestionDao.getQuestionForUserId(userId);
                if (question == null) {
                    throw new ServiceException("Question is none for "  + userName);
                }

                userQuestion.put(userId, question);
                return userQuestion;
            } catch (DaoException e) {
                throw new ServiceException("Unable to get question for "  + userName, e);
            }
        } else {
            throw new ServiceException("Unable to get user.");
        }
    }

    /**
     * Do the account password reset program.
     *
     * @param userId                userId of the user.
     * @return                      tmpPwd
     * @throws DaoException         DB Error.
     */
    private String applyAccountRetrievalProcess(int userId) throws DaoException {
        this.securityQuestionDao.updateUserStatus(userId, 2);
        //password generator
        String tempPwd = RandomStringUtils.randomAlphanumeric(17).toUpperCase();
        LOGGER.debug("Temp Password is: " + tempPwd);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(tempPwd);
        LOGGER.debug("Hashed Password is: " + hashedPassword);

        this.securityQuestionDao.updateUserPwd(userId, hashedPassword);

        return tempPwd;

    }


    /**
     * Email user for temporary password.
     *
     * @param userId                user id to email.
     * @param tempPwd               tmp password to be sent.
     * @throws DaoException         DB Error.
     */
    private void emailUserForTempPwd(int userId, String tempPwd) throws DaoException {
        String emailAddUser = this.securityQuestionDao.getUserEmailByUserId(userId);
        String body = "Hello Mammba User, \n\nPlease be advised that your account\'s password has been reset.  " +
                "Please use this tempoary password: " +tempPwd+ "\n\nThank you very much.";

        EmailModel email = new EmailModel();
        email.setToRecipient(emailAddUser);
        email.setSubject("Here is your temporary password.");
        email.setBodyMessage(body);

        this.emailUtility.sendEmail(email);
    }


}
