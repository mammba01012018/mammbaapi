/**
 * MainController.java - MAMMBA Application
 * 2018 All rights reserved.
 *
 */
package src.main.java.mammba.core.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import src.main.java.mammba.core.exception.ServiceException;
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
            return ResponseEntity.ok().body(member);
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
            return ResponseEntity.ok().body(partner);
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

}