package src.main.java.mammba.core.dao;

import src.main.java.mammba.model.LoginModel;

public interface LoginDao {
	boolean isLoginValid(LoginModel loginModel);
}
