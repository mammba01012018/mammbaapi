/**
 * PartnerDaoImpl.java - MAMMBA Application
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
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;


import src.main.java.mammba.core.exception.DaoException;
import src.main.java.mammba.model.MammbaUser;
import src.main.java.mammba.model.Partner;

/**
 * Dao class for Partner only.
 *
 * @author Mardolfh Del Rosario / Michelle Pancipane
 *
 */
@Repository("userPartnerDao")
public class PartnerDaoImpl extends MammbaUserDaoImpl {

    private static final Logger LOGGER = Logger.getLogger(PartnerDaoImpl.class);
    private static class PartnerMapper implements RowMapper<Partner> {
        @Override
        public Partner mapRow(ResultSet rs, int rowNum) throws SQLException {
            Partner partner = new Partner();
            partner.setUserId(rs.getInt("user_id"));
            partner.setUsername(rs.getString("user_userName"));
            partner.setPassword(rs.getString("user_password"));
            partner.setEmailAddress(rs.getString("user_email"));
            partner.setMobileNumber(rs.getString("user_mobileNumber"));
            partner.setUserType(rs.getString("user_type"));

            if (rs.getInt("user_stat_id") > 0) {
                switch(rs.getInt("user_stat_id")) {
                    case 1: partner.setUserStatus(Partner.UserStatus.Active); break;
                    case 2: partner.setUserStatus(Partner.UserStatus.TempPassword); break;
                    case 3: partner.setUserStatus(Partner.UserStatus.Locked); break;
                    case 4: partner.setUserStatus(Partner.UserStatus.Inactive); break;
                }
            }

            partner.setAddress1(rs.getString("partner_address"));
            partner.setAgencyType(rs.getString("partner_agencyType"));
            partner.setBusinessPermit(rs.getString("partner_businessPermit"));
            partner.setBusinessPermitExpiry(rs.getDate("partner_businessPermitExpiry"));
            partner.setCompanyName(rs.getString("partner_companyName"));
            partner.setContactPersonMobileNum(rs.getString("partner_contactPrsonMobileNum"));
            partner.setContactPersonName(rs.getString("partner_contactPrsonName"));
            partner.setContactPersonPosition(rs.getString("partner_contactPrsonPosition"));
            partner.setContactPersonTelNum(rs.getString("partner_contactPrsonTelNum"));
            partner.setCountry(rs.getString("partner_country"));
            partner.setDti(rs.getString("partner_DTI"));
            partner.setFax(rs.getString("partner_fax"));
            partner.setIataNumber(rs.getString("partner_IATANumber"));
            partner.setNumOfStaff(rs.getInt("partner_numOfStaff"));
            partner.setPartnerId(rs.getInt("partner_id"));
            partner.setPartnerName(rs.getString("partner_name"));
            partner.setProvince(rs.getString("partner_province"));
            partner.setSec(rs.getString("partner_SEC"));
            partner.setTelNumber(rs.getString("partner_telNumber"));
            partner.setTinNumber(rs.getString("partner_tinNumber"));
            partner.setTypeOfService(rs.getString("partner_typeOfService"));

            return partner;
        }
    }

    @Override
    public int register(MammbaUser user) throws DaoException {
        try {
            Partner partner = (Partner) user;
            KeyHolder holder = new GeneratedKeyHolder();
            String sql = this.queryManager.getQuery("registerPartner");

            MapSqlParameterSource parameters = new MapSqlParameterSource()
                    .addValue("partnerName", partner.getPartnerName())
                    .addValue("iataNumber", partner.getIataNumber())
                    .addValue("companyName", partner.getCompanyName())
                    .addValue("address1", partner.getAddress1())
                    .addValue("province", partner.getProvince())
                    .addValue("country", partner.getCountry())
                    .addValue("telNumber", partner.getTelNumber())
                    .addValue("mobileNumber", partner.getMobileNumber())
                    .addValue("fax", partner.getFax())
                    .addValue("emailAddress", partner.getEmailAddress())
                    .addValue("numOfStaff", partner.getNumOfStaff())
                    .addValue("tinNumber", partner.getTinNumber())
                    .addValue("agencyType", partner.getAgencyType())
                    .addValue("typeOfService", partner.getTypeOfService())
                    .addValue("contactPersonName", partner.getContactPersonName())
                    .addValue("contactPersonPosition", partner.getContactPersonPosition())
                    .addValue("contactPersonTelNum", partner.getContactPersonTelNum())
                    .addValue("contactPersonMobileNum", partner.getContactPersonMobileNum())
                    .addValue("businessPermit", partner.getBusinessPermit())
                    .addValue("businessPermitExpiry", partner.getBusinessPermitExpiry())
                    .addValue("DTI", partner.getDti())
                    .addValue("SEC", partner.getSec());

           this.namedParameterJdbcTemplate.update(sql, parameters, holder);

           return holder.getKey().intValue();
        } catch (DataAccessException | SQLException e) {
            LOGGER.error("registerMember(Partner)-exception", e);
            throw new DaoException("MAMMBA[RP]-01-Database error");
        }
    }

    @Override
    public Partner getUserDetails(String userName) throws DaoException {
        try {
            String sql = this.queryManager.getQuery("getPartnerDetailByUserName");
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("username", userName);
            List<Partner> partner = this.namedParameterJdbcTemplate.query(sql,
                    params, new PartnerMapper());
            if (partner != null && !partner.isEmpty()) {
                return partner.get(0);
            }
        } catch (SQLException | DataAccessException e) {
            LOGGER.error("getUserDetails(PartnerDao)-exception", e);
            throw new DaoException("MAMMBA[UDI-C]-01-Database error");

        }

        return null;
    }
    
    @Override
    public MammbaUser getUserDetails(int userId) throws DaoException {
        try {
            String sql = this.queryManager.getQuery("getPartnerDetailByPartnerId");
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("partnerId", userId);
            List<Partner> partner = this.namedParameterJdbcTemplate.query(sql,
                    params, new PartnerMapper());
            if (partner != null && !partner.isEmpty()) {
                return partner.get(0);
            }
        } catch (SQLException | DataAccessException e) {
            LOGGER.error("getUserDetails()-exception",e);
            throw new DaoException("MAMMBA[GUD]-02-Database error");

        }

        return null;
    }
    
    

}
