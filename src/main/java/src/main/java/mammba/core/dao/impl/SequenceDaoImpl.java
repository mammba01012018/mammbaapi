package src.main.java.mammba.core.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import src.main.java.mammba.core.dao.SequenceDao;
import src.main.java.mammba.core.exception.DaoException;
import src.main.java.mammba.core.util.QueryManager;
import src.main.java.mammba.model.Sequence;

@Repository("sequenceDaoImpl")
public class SequenceDaoImpl implements SequenceDao {
	

	@Autowired
    private QueryManager queryManager;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
    private static final Logger LOGGER = Logger.getLogger(SequenceDaoImpl.class);
    
    
    private static class SequenceMapper implements RowMapper<Sequence> {
        @Override
        public Sequence mapRow(ResultSet rs, int rowNum) throws SQLException {           
        	Sequence sequence = new Sequence();
        	sequence.setCurrentNumber(rs.getInt("current_number"));
        	sequence.setPrefix(rs.getString("prefix"));
        	sequence.setCurrSequence(rs.getString("current_sequence"));
        	sequence.setTableName(rs.getString("table_name"));
            return sequence;
        }
    }

	@Override
	public Sequence getSequence(String tableName) throws DaoException {
		try {
			String sql = this.queryManager.getQuery("getSequence");
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("tableName", tableName);
			
		  List<Sequence> sequence = this.namedParameterJdbcTemplate.query(sql, params,new SequenceMapper());
			if(sequence!=null && !sequence.isEmpty()) {
				return sequence.get(0);
			}
		  
		} catch (DataAccessException | SQLException e) {
			LOGGER.error("Getting Sequence()- exception", e);
			throw new DaoException("MAMMBA[AT]-04-Database error");
		}	
		return null;
	}

	@Override
	public Sequence updateSequence(Sequence sequence) throws DaoException {
		try {
			String sql = this.queryManager.getQuery("updateSequence");
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("currentNumber", sequence.getCurrentNumber());
			params.addValue("prefix", sequence.getPrefix());
			params.addValue("currentSequence", sequence.getCurrSequence());
			params.addValue("tableName", sequence.getTableName());
			
		  this.namedParameterJdbcTemplate.update(sql,params);
			return sequence;
		} catch (DataAccessException | SQLException e) {
			LOGGER.error("Updating Sequence()- exception", e);
			throw new DaoException("MAMMBA[AT]-04-Database error");
		}
	}

}
