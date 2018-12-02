/**
 * ProfileController.java - MAMMBA Application
 * 2018 All rights reserved.
 *
 */

package src.main.java.mammba.core.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import src.main.java.mammba.core.exception.ServiceException;
import src.main.java.mammba.core.service.UserService;
import src.main.java.mammba.model.Member;

/**
 * This class serves as the profile controller in editing user profile.
 *
 * @author Mardolfh Del Rosario
 *
 */
@RestController
public class ProfileController {

    @Autowired
    @Qualifier(value="userMemberService")
    private UserService userMemberService;


    private static final Logger LOGGER = Logger.getLogger(ProfileController.class);


    /**
     * Register new member.
     *
     * @param member           Member reference from UI.
     * @return                 ResponseEntity - bad request or success.
     */
    @PostMapping("mammba-user/updateMember")
    public ResponseEntity<?> updateMember(@RequestBody Member member) {
        LOGGER.info("updateMember(member)-start");
        try {
            this.userMemberService.updateUser(member);
            return ResponseEntity.ok().body("Successfully updated member: " + member.getUserId());
        } catch (ServiceException e) {
            LOGGER.error("Error updating Member: " + member.getFirstName(), e);
        }

        LOGGER.info("register(member)-end");
        return ResponseEntity.status(404).body("Unable to update member: " + member.getFirstName());
    }
}
