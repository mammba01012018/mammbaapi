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
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import src.main.java.mammba.core.exception.ServiceException;
import src.main.java.mammba.core.service.UserService;
import src.main.java.mammba.core.util.ObjectUtility;
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
	private ObjectUtility utility;

	private static final Logger LOGGER = Logger.getLogger(StarterController.class);

	/**
	 * Get csrf token.
	 *
	 * @param csrfToken        csrf token.
	 * @return                 CsrfToken reference
	 */
    @GetMapping("/init")
    public CsrfToken getCsrf(CsrfToken csrfToken) {
        return csrfToken;
    }

	/**
     * Get Mammba user info details.
     *
     * @param username         Member reference from request.
     * @return                 ResponseEntity - bad request or success.
     */
    @RequestMapping(value = "mammba-user/getUser/" )
    public ResponseEntity<?> getUserInfo(HttpSession session) {
        LOGGER.info("getUserInfo()-start");
        String username = "";
        try {
            SecurityContextImpl userInfo = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
            if (userInfo != null) {
                username = (String) userInfo.getAuthentication().getPrincipal();
                List<String> roleList =
                        this.utility.getUserRoles(userInfo.getAuthentication().getAuthorities());

                if (roleList.contains("ROLE_MEMBER")) {
                    Member member = (Member)this.userMemberService.getUserDetails(username);
                    return ResponseEntity.ok(member);
                } else if (roleList.contains("ROLE_PARTNER")) {
                    Partner partner = (Partner)this.userPartnerService.getUserDetails(username);
                    return ResponseEntity.ok(partner);
                }

            }
        } catch (ServiceException e) {
            LOGGER.error("Unable to get User details.", e);
        }


        return ResponseEntity.status(404).body("Invalid user details");
    }


}