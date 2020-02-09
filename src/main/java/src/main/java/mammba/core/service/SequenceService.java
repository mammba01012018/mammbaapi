package src.main.java.mammba.core.service;

import src.main.java.mammba.core.exception.ServiceException;
import src.main.java.mammba.model.Sequence;

public interface SequenceService {
	
	Sequence getSequence(String tableName) throws ServiceException;
	
	Sequence updateSequence(String tableName)  throws ServiceException;
}
