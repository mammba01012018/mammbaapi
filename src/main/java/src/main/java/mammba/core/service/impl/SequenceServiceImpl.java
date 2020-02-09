package src.main.java.mammba.core.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import src.main.java.mammba.core.dao.SequenceDao;
import src.main.java.mammba.core.exception.DaoException;
import src.main.java.mammba.core.exception.ServiceException;
import src.main.java.mammba.core.service.SequenceService;
import src.main.java.mammba.model.Sequence;


@Service("sequenceServiceImpl")
public class SequenceServiceImpl implements SequenceService {
	
	@Autowired
	private SequenceDao sequenceDao;
	
	@Override
	public Sequence getSequence(String tableName) throws ServiceException {
		// TODO Auto-generated method stub
		String err = "";
		try {
			return sequenceDao.getSequence(tableName);
		} catch (DaoException e) {
			err = e.getMessage();
			throw new ServiceException(err);
		}
	}

	@Override
	public Sequence updateSequence(String tableName) throws ServiceException {
		String err = "";
		try {
			Sequence seq = this.getSequence(tableName);
			seq.setCurrentNumber(seq.getCurrentNumber()+1);
			seq.setCurrSequence(seq.getPrefix() + formatCurrentSequence(seq.getCurrentNumber()));
			sequenceDao.updateSequence(seq);
			return seq;
		} catch (DaoException e) {
			err = e.getMessage();
			throw new ServiceException(err);
		}
	}
	
	private String formatCurrentSequence(Integer num) {
		String numStr = num.toString();
		return StringUtils.leftPad(numStr, 10, '0');
		
		
	}
	/*
	 * TODO  GENERATE LOGIC for auto prefix counting
	 * 
	 */
	private String changePrefixSequence(Integer num) {
		String currentPrefix;
		
		if(num >= Integer.MAX_VALUE) {
			for (char c = 'A'; c <= 'Z'; c++) {
		
			}

		}		
		return null;	
	}

}
