/**
 * 
 */
package src.main.java.mammba.core.dao;

import src.main.java.mammba.core.exception.DaoException;
import src.main.java.mammba.model.Member;


public interface RegisterDao {
	
	/**
	 * Captures new member details. Must have member
	 * @param member
	 */
	void registerMember(Member member) throws DaoException;

}
