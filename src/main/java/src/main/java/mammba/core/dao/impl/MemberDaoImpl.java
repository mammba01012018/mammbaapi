/**
 * MemberDaoImpl.java - MAMMBA Application
 * 2018 All rights reserved.
 *
 */
package src.main.java.mammba.core.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import src.main.java.mammba.core.exception.DaoException;
import src.main.java.mammba.model.MammbaUser;
import src.main.java.mammba.model.Member;

/**
 * Dao class for Member only.
 *
 * @author Mardolfh Del Rosario / Michelle Pancipane
 *
 */
@Repository("userMemberDao")
public class MemberDaoImpl extends MammbaUserDaoImpl {

    private static final Logger LOGGER = Logger.getLogger(MemberDaoImpl.class);
    private static class MemberMapper implements RowMapper<Member> {
        @Override
        public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
            Member member = new Member();
            member.setUserId(rs.getInt("user_id"));
            member.setUsername(rs.getString("user_userName"));
            member.setPassword(rs.getString("user_password"));
            member.setEmailAddress(rs.getString("user_email"));
            member.setMobileNumber(rs.getString("user_mobileNumber"));
            member.setUserType(rs.getString("user_type"));
            member.setAddress1(rs.getString("member_address1"));
            member.setAddress2(rs.getString("member_address2"));
            member.setCountry(rs.getString("member_country"));
            member.setFirstName(rs.getString("member_firstName"));
            member.setGender(rs.getString("member_gender"));
            member.setLastName(rs.getString("member_lastName"));
            member.setMemberId(rs.getInt("member_id"));
            member.setMiddleInitial(rs.getString("member_middleInitial"));
            member.setProvince(rs.getString("member_province"));
            member.setRate(rs.getString("member_rate"));
            member.setBirthDate(rs.getDate("member_birthDate"));

            return member;
        }
    }

    @Override
    public int register(MammbaUser user) throws DaoException {
        try {
            Member member = (Member) user;
            KeyHolder holder = new GeneratedKeyHolder();
            String sql = this.queryManager.getQuery("registerMember");
            SqlParameterSource parameters = new MapSqlParameterSource()
                    .addValue("firstName", member.getFirstName())
                    .addValue("lastName", member.getLastName())
                    .addValue("middleInitial", member.getMiddleInitial())
                    .addValue("gender", member.getGender())
                    .addValue("address1", member.getAddress1())
                    .addValue("address2", member.getAddress2())
                    .addValue("province", member.getProvince())
                    .addValue("country", member.getCountry())
                    .addValue("emailAddress", member.getEmailAddress())
                    .addValue("mobileNumber", member.getMobileNumber())
                    .addValue("birthdate", member.getBirthDate());

           this.namedParameterJdbcTemplate.update(sql, parameters, holder);

           return holder.getKey().intValue();
        } catch (DataAccessException | SQLException e) {
            LOGGER.error("registerMember(Member)-exception", e);
            throw new DaoException("MAMMBA[RM]-01-Database error");
        }

    }

    @Override
    public Member getUserDetails(String userName) throws DaoException {
        try {
            String sql = this.queryManager.getQuery("getMemberDetailByUserName");
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("username", userName);
            List<Member> member = this.namedParameterJdbcTemplate.query(sql,
                    params, new MemberMapper());
            if (member != null && !member.isEmpty()) {
                return member.get(0);
            }
        } catch (SQLException | DataAccessException e) {
            LOGGER.error("getUserDetails(MemberDao)-exception", e);
            throw new DaoException("MAMMBA[UDI-C]-01-Database error");

        }

        return null;
    }

    @Override
    @Transactional
    public int update(MammbaUser user) throws DaoException {
        try {
            Member member = (Member) user;
            String sql0 = this.queryManager.getQuery("updateUser");
            SqlParameterSource parameters = new MapSqlParameterSource()
                    .addValue("emailAddress", member.getEmailAddress())
                    .addValue("mobileNumber", member.getMobileNumber())
                    .addValue("password", member.getPassword())
                    .addValue("memberId", member.getMemberId());

            //Updates USER table
            int result = this.namedParameterJdbcTemplate.update(sql0, parameters);
            LOGGER.info("Update user table: " + result);

            String sql1 = this.queryManager.getQuery("updateMember");
            parameters = new MapSqlParameterSource()
                    .addValue("firstName", member.getFirstName())
                    .addValue("lastName", member.getLastName())
                    .addValue("middleInitial", member.getMiddleInitial())
                    .addValue("address1", member.getAddress1())
                    .addValue("emailAddress", member.getEmailAddress())
                    .addValue("mobileNumber", member.getMobileNumber())
                    .addValue("memberId", member.getMemberId());

           //Updates MEMBER table
           this.namedParameterJdbcTemplate.update(sql1, parameters);
           LOGGER.info("Update member table");

           return member.getUserId();
        } catch (DataAccessException | SQLException e) {
            LOGGER.error("updateMember(Member)-exception", e);
            throw new DaoException("MAMMBA[RM]-01-Database error");
        }
    }
}
