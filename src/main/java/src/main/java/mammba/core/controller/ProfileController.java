/**
 * ProfileController.java - MAMMBA Application
 * 2018 All rights reserved.
 *
 */

package src.main.java.mammba.core.controller;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import src.main.java.mammba.core.exception.ServiceException;
import src.main.java.mammba.core.service.LoginService;
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

    @Autowired
    private LoginService mammbaUserService;


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
        String errorMsg = "";
        try {
            this.userMemberService.updateUser(member);
            Member updatedMember = (Member)this.userMemberService.getUserDetails(member.getEmailAddress());
            JSONObject obj = new JSONObject(updatedMember);

            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(obj.toString());
        } catch (ServiceException e) {
            LOGGER.error("Error updating Member: " + member.getFirstName(), e);
            errorMsg = e.getMessage();
        }

        LOGGER.info("updateMember(member)-end");
        return ResponseEntity.status(404).body("Unable to update member: " + member.getFirstName() + " due to " +
                errorMsg);
    }

    /**
     * Updates password and update user status.
     *
     * @param member           Member reference from UI.
     * @return                 ResponseEntity - bad request or success.
     */
    @PostMapping("mammba-user/updateAuth")
    public ResponseEntity<?> updateAuth(@RequestParam int userId, @RequestParam String pwd) {
        LOGGER.info("resetPassword()-start");
        try {
            this.mammbaUserService.updatePassword(userId, pwd);
            return ResponseEntity.ok().body("Successfully reset credentials: " + userId);
        } catch (ServiceException e) {
            LOGGER.error("Error updating credentials: " + userId, e);
        }

        LOGGER.info("resetPassword()-end");
        return ResponseEntity.status(404).body("Unable to update credentials.");
    }
}
