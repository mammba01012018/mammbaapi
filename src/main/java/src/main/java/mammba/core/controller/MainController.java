package src.main.java.mammba.core.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import src.main.java.mammba.core.service.LoginService;
import src.main.java.mammba.model.LoginModel;

@Controller
public class MainController {

	@Autowired
	private LoginService loginService;
	
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
			return ResponseEntity.ok("success");
		} else {
			return ResponseEntity.ok("fail");
		}
		
	}
}