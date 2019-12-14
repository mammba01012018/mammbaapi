package src.main.java.mammba.core.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import src.main.java.mammba.core.exception.ControllerException;
import src.main.java.mammba.core.exception.ServiceException;
import src.main.java.mammba.core.service.SecurityQuestionService;

/**
 * This class serves as the security question controller.
 *
 * @author Mardolfh Del Rosario
 *
 */
@RestController
public class SecurityQuestionController {

    @Autowired
    private SecurityQuestionService securityQuestionService;


    /**
     * Get all security questions available in Mammba app.
     * @return                      List of questionnaires.
     * @throws ControllerException  error.
     */
    @PostMapping("/securityQuestions/getAll")
    public List<String> getAllSecurityQuestions() throws ControllerException {
        List<String> listOfQuestions = null;
        try {
            listOfQuestions = this.securityQuestionService.getAllSecurityQuestions();
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }

        return listOfQuestions;
    }

    /**
     * Get question id for the given user.
     * @param userId                user id.
     * @return                      question id.
     * @throws ControllerException  error.
     */
    @PostMapping("/securityQuestions/getQuestionId")
    public int getQuestionForUser(@RequestParam int userId) throws ControllerException {
        int questionId = 0;
        try {
            questionId = this.securityQuestionService.getQuestionForUserId(userId);
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }

        return questionId;
    }

    /**
     * Validate Answer for user.
     * @param userId                userId.
     * @param answer                answer to the security question.
     * @return                      question id.
     * @throws ControllerException  error.
     */
    @PostMapping("/securityQuestions/validateAnswer")
    public boolean validateUserAnswer(@RequestParam int userId, @RequestParam String answer)
            throws ControllerException {
        boolean isValid = false;
        try {
            isValid = this.securityQuestionService.isSecurityQuestionAnswerValid(userId, answer);
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }

        return isValid;
    }

    /**
     * Adds security question and answer for user.
     *
     * @param userId                userId.
     * @param questionId            questionId.
     * @param answer                answer to the security question.
     * @return                      notification.
     * @throws ControllerException  error.
     */
    @PostMapping("/securityQuestions/addQAUser")
    public String addNewSecurityQuestionAndAnswerUser(@RequestParam int userId, @RequestParam int questionId,
            @RequestParam String answer) throws ControllerException {
        try {
            this.securityQuestionService.addNewQAUser(userId, questionId, answer);
            return "Successfully added security question and answer.";
        } catch (ServiceException e) {
            throw new ControllerException("Unable to save user security question and answer.");
        }
    }

    /**
     * Get user id for the given user.
     * @param userName              username/mobile/email.
     * @return                      user id.
     * @throws ControllerException  error.
     */
    @PostMapping("/securityQuestions/getUserId")
    public int getUserId(@RequestParam String userName) throws ControllerException {
        int userId = 0;
        try {
            userId = this.securityQuestionService.getUserId(userName);
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }

        return userId;
    }


    /**
     * Get user id for the given user.
     * @param userName              username/mobile/email.
     * @return                      user id.
     * @throws ControllerException  error.
     */
    @PostMapping("/securityQuestions/getQuestionFromUserName")
    public ResponseEntity<?> getQuestionFromUserName(@RequestParam String userName) throws ControllerException {
        Map<Integer, String> questionaire = null;
        try {
            questionaire = this.securityQuestionService.getQuestionForUserName(userName);
            if (questionaire != null) {
                return ResponseEntity.ok().body(questionaire);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Question not found for user: " + userName);
            }

        } catch (ServiceException e) {
            throw new ControllerException(e);
        }

    }
}
