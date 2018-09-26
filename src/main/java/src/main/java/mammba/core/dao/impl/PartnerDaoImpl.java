/**
 * PartnerDaoImpl.java - MAMMBA Application
 * 2018 All rights reserved.
 *
 */
package src.main.java.mammba.core.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
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
            partner.setUsername(rs.getString("user_userName"));
            partner.setPassword(rs.getString("user_password"));
            partner.setEmailAddress(rs.getString("user_email"));
            partner.setMobileNumber(rs.getString("user_mobileNumber"));
            partner.setUserType(rs.getString("user_type"));

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
            partner.setDTI(rs.getString("partner_DTI"));
            partner.setFax(rs.getString("partner_fax"));
            partner.setIataNumber(rs.getString("partner_IATANumber"));
            partner.setNumOfStaff(rs.getInt("partner_numOfStaff"));
            partner.setPartnerId(rs.getInt("partner_id"));
            partner.setPartnerName(rs.getString("partner_name"));
            partner.setProvince(rs.getString("partner_province"));
            partner.setSEC(rs.getString("partner_SEC"));
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
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("partnerName", partner.getPartnerName());
            paramMap.put("iataNumber", partner.getIataNumber());
            paramMap.put("companyName", partner.getCompanyName());
            paramMap.put("address1", partner.getAddress1());
            paramMap.put("province", partner.getProvince());
            paramMap.put("country", partner.getCountry());
            paramMap.put("telNumber", partner.getTelNumber());
            paramMap.put("mobileNumber", partner.getMobileNumber());
            paramMap.put("fax", partner.getFax());
            paramMap.put("emailAddress", partner.getEmailAddress());
            paramMap.put("numOfStaff", partner.getNumOfStaff());
            paramMap.put("tinNumber", partner.getTinNumber());
            paramMap.put("agencyType", partner.getAgencyType());
            paramMap.put("typeOfService", partner.getTypeOfService());
            paramMap.put("contactPersonName", partner.getContactPersonName());
            paramMap.put("contactPersonPosition", partner.getContactPersonPosition());
            paramMap.put("contactPersonTelNum", partner.getContactPersonTelNum());
            paramMap.put("contactPersonMobileNum", partner.getContactPersonMobileNum());
            paramMap.put("businessPermit", partner.getBusinessPermit());
            paramMap.put("businessPermitExpiry", partner.getBusinessPermitExpiry());
            paramMap.put("DTI", partner.getDTI());
            paramMap.put("SEC", partner.getSEC());
            return this.namedParameterJdbcTemplate.update("registerMember", paramMap);
        } catch (DataAccessException e) {
            LOGGER.error("registerMember()-exception");
            throw new DaoException("MAMMBA[UDI]-01-Database error");
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

}
