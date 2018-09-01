package src.main.java.mammba.core.controller;

import java.time.LocalDate;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import src.main.java.mammba.core.exception.ServiceException;
import src.main.java.mammba.core.service.LoginService;
import src.main.java.mammba.core.service.RegisterService;
import src.main.java.mammba.model.LoginModel;
import src.main.java.mammba.model.Member;

@Controller
public class MainController {

	@Autowired
	private LoginService loginService;

	@Autowired
	@Qualifier("registerMember")
	private RegisterService registerMemberService;

	private static final Logger LOGGER = Logger.getLogger(MainController.class);

	@RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
    public String homePage(Model model) {
        return "homePage";
	}

	@RequestMapping(value = { "/login" }, method = RequestMethod.POST)
	public ResponseEntity<?> login(@RequestParam String userEmail, @RequestParam String password) {
		LoginModel loginModel = new LoginModel();
		loginModel.setUserEmail(userEmail);
		loginModel.setPassword(password);
		loginModel.setLocalDate(LocalDate.now());
		if (this.loginService.isLoginValid(loginModel)) {
			return ResponseEntity.ok("MAMMBA[MC]-01-OK");
		} else {
			return ResponseEntity.ok("MAMMBA[MC]-02-FAIL");
		}
	}

	@RequestMapping(value = { "/register" }, method = RequestMethod.POST)
	public ResponseEntity<?> registerNewMember(@RequestBody Member member) {
		LOGGER.info("registerNewMember()-start");
		LOGGER.info(member);
	    try {
		    this.registerMemberService.register(member);
		} catch (ServiceException e) {
			return ResponseEntity.badRequest()
			        .body("MAMMBA[MC]-03-Exception occurred on register.");
		}

	    LOGGER.info("registerNewMember()-end");
		return ResponseEntity.ok("MAMMBA[MC]-04-SUCCESS");
	}
}