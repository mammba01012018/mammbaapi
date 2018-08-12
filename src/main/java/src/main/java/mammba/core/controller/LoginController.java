package src.main.java.mammba.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import src.main.java.mammba.core.service.LoginService;
import src.main.java.mammba.model.LoginModel;

@RestController
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	
	@PostMapping("/mammba/loginUser")
	public ResponseEntity<?> validateLogin(@RequestBody LoginModel loginModel) {
		if (this.loginService.isLoginValid(loginModel)) {
			return ResponseEntity.ok("success");
		} else {
			return ResponseEntity.ok("Error login in user " +
					loginModel.getUserEmail());
		}
		
	}

}
