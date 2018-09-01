package src.main.java.mammba.core.service;

import src.main.java.mammba.core.exception.ServiceException;
import src.main.java.mammba.model.MammbaUser;

public interface RegisterService {
	void register(MammbaUser mammbaUser) throws ServiceException;
}
