package src.main.java.mammba.core.dao;

import src.main.java.mammba.core.exception.DaoException;
import src.main.java.mammba.model.Sequence;

public interface SequenceDao {

	Sequence getSequence(String tableName) throws DaoException;
	
	Sequence updateSequence(Sequence sequence) throws DaoException;
	
}
