package src.main.java.mammba.core.service;

import src.main.java.mammba.model.LoginModel;

public interface LoginService {
	boolean isLoginValid(LoginModel loginModel);
}
