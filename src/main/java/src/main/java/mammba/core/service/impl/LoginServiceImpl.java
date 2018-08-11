package src.main.java.mammba.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import src.main.java.mammba.core.dao.LoginDao;
import src.main.java.mammba.core.service.LoginService;
import src.main.java.mammba.model.LoginModel;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private LoginDao loginDao;
	
	@Override
	public boolean isLoginValid(LoginModel loginModel) {
		return this.loginDao.isLoginValid(loginModel);
	}

}
