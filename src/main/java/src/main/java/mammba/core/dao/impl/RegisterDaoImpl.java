/**
 * 
 */
package src.main.java.mammba.core.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import src.main.java.mammba.core.dao.RegisterDao;
import src.main.java.mammba.core.util.QueryManager;
import src.main.java.mammba.model.Member;

@Repository
public class RegisterDaoImpl implements RegisterDao{
	
	@Autowired
	private QueryManager queryManager;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


	@Override
	public void registerMember(Member member) {
		
		
	}

}
