/**
 * MainController.java - MAMMBA Application
 * 2018 All rights reserved.
 *
 */
package src.main.java.mammba.core.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import src.main.java.mammba.core.exception.ControllerException;
import src.main.java.mammba.core.exception.ServiceException;
import src.main.java.mammba.core.service.SecurityQuestionService;
import src.main.java.mammba.core.service.UserService;
import src.main.java.mammba.model.Member;
import src.main.java.mammba.model.Partner;

/**
 * This class serves as the main controller of the basic restful functions.
 *
 * @author Mardolfh Del Rosario
 *
 */
@RestController
public class StarterController {

	@Autowired
	@Qualifier(value="userMemberService")
	private UserService userMemberService;

	@Autowired
    @Qualifier(value="userPartnerService")
    private UserService userPartnerService;

	@Autowired
	private SecurityQuestionService securityQuestionService;



	private static final Logger LOGGER = Logger.getLogger(StarterController.class);


    /**
     * Register new member.
     *
     * @param member           Member reference from UI.
     * @return                 ResponseEntity - bad request or success.
     */
    @PostMapping("registerMember")
    public ResponseEntity<?> register(@RequestBody Member member) {
        LOGGER.info("register(member)-start");
        try {
            this.userMemberService.register(member);
            return ResponseEntity.ok().body("Successfully registered member: " + member.getUsername());
        } catch (ServiceException e) {
            LOGGER.error("Error registering Member.", e);
        }

        LOGGER.info("register(member)-end");
        return ResponseEntity.status(404).body("Unable to register member.");
    }

    /**
     * Register new partner.
     *
     * @param partner          Member reference from UI.
     * @return                 ResponseEntity - bad request or success.
     */
    @PostMapping("registerPartner")
    public ResponseEntity<?> register(@RequestBody Partner partner) {
        LOGGER.info("register(partner)-start");
        try {
            this.userPartnerService.register(partner);
            return ResponseEntity.ok().body("Successfully registered partner: " + partner.getUsername());
        } catch (ServiceException e) {
            LOGGER.error("Error registering Partner.", e);
        }

        LOGGER.info("register(partner)-end");
        return ResponseEntity.status(404).body("Unable to register partner.");
    }

    /**
     * Test to determine if user is allowed to get in.
     * @param session
     * @return
     */
    @PostMapping("/mammba-user/loginDetails")
    public ResponseEntity<?> loginDetails(HttpSession session) {

        SecurityContextImpl userInfo = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
        return ResponseEntity.ok().body("User is currently login: " +
                (String) userInfo.getAuthentication().getPrincipal());
    }

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
            questionId = this.securityQuestionService.getQuestionForUser(userId);
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


}