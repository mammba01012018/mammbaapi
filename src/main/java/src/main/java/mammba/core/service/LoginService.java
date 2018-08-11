package src.main.java.mammba.core.service;

import org.springframework.stereotype.Service;

import src.main.java.mammba.model.LoginModel;

public interface LoginService {
	boolean isLoginValid(LoginModel loginModel);
}
