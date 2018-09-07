/**
 * MainController.java - MAMMBA Application
 * 2018 All rights reserved.
 *
 */
package src.main.java.mammba.core.controller;

import java.time.LocalDate;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import src.main.java.mammba.core.exception.ServiceException;
import src.main.java.mammba.core.service.MammbaUserService;
import src.main.java.mammba.core.service.UserService;
import src.main.java.mammba.model.LoginModel;
import src.main.java.mammba.model.Member;

/**
 * This class serves as the main controller of the basic restful functions.
 *
 * @author Mardolfh Del Rosario
 *
 */
@RestController
public class StarterController {

    @Autowired
    private MammbaUserService mammbaUserService;

	@Autowired
	@Qualifier(value="userMember")
	private UserService userMemberService;

	private static final Logger LOGGER = Logger.getLogger(StarterController.class);


	/**
	 * Restful controller that handles the login whether mobile or web app.
	 *
	 * @param userEmail        email login
	 * @param password         password must be encrypted
	 * @return                 ResponseEntity - ok or fail.
	 */
	@PostMapping(value = { "/loginUser" })
	public ResponseEntity<?> login(@RequestBody(required = true) LoginModel loginModel) {

	    try {
	        LOGGER.info("login()-start");
	        loginModel.setLocalDate(LocalDate.now());
	        if (this.mammbaUserService.isLoginValid(loginModel)) {
	            return ResponseEntity.ok("MAMMBA[MC]-01-OK");
	        } else {
	            return ResponseEntity.ok("MAMMBA[MC]-02-FAIL");
	        }
	    } catch (ServiceException e) {
	        return ResponseEntity.badRequest().body("MAMMBA[MC]-03-ERROR");
	    } finally {
	        LOGGER.info("login()-end");
	    }

	}

	/**
	 * Registers a new Mammba user - member.
	 *
	 * @param member           Member reference from request.
	 * @return                 ResponseEntity - bad request or success.
	 */
	@PostMapping(value = { "/register" })
	public ResponseEntity<?> registerNewMember(@RequestBody(required = true) Member member) {
		LOGGER.info("registerNewMember()-start");
		LOGGER.info(member);
	    try {
		    this.userMemberService.register(member);
		} catch (ServiceException e) {
			return ResponseEntity.badRequest()
			        .body("MAMMBA[MC]-04-Exception occurred on register.");
		}

	    LOGGER.info("registerNewMember()-end");
		return ResponseEntity.ok("MAMMBA[MC]-05-SUCCESS");
	}
}