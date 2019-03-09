package src.main.java.mammba.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import src.main.java.mammba.core.dao.SecurityQuestionDao;
import src.main.java.mammba.core.exception.DaoException;
import src.main.java.mammba.core.exception.ServiceException;
import src.main.java.mammba.core.service.SecurityQuestionService;

@Service
public class SecurityQuestionServiceImpl implements SecurityQuestionService {

    @Autowired
    private SecurityQuestionDao securityQuestionDao;

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
    public boolean isSecurityQuestionAnswerValid(int userId, String answer) throws ServiceException {
        if (userId > 0 && answer != null && !answer.isEmpty()) {
            try {
                return this.securityQuestionDao.isSecurityQuestionAnswerValid(userId, answer);
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
    public int getQuestionForUser(int userId) throws ServiceException {
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

}
