/**
 * ObjectUtility.java - MAMMBA Application
 * 2018 All rights reserved.
 *
 */
package src.main.java.mammba.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

/**
 * Utility helper class.
 *
 * @author Mardolfh Del Rosario
 *
 */
@Component
public class ObjectUtility {

    /**
     * Checks a certain object if it is a null or empty value.
     *
     * @param o                     Object reference.
     * @return                      true/false.
     */
	public boolean isNullOrEmpty(Object o) {
		if (o == null) {
			return true;
		} else if (o != null && o instanceof String) {
			String str = (String) o;
			if (str.isEmpty()) {
				return true;
			}
		} else if (o != null && o instanceof List) {
			List<?> list = (List<?>) o;
			if (list.isEmpty()) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Get the user role of the user login.
	 *
	 * @param authList                 GrantedAuthority reference.
	 * @return                         List in string format.
	 */
	@SuppressWarnings("unchecked")
    public List<String> getUserRoles(Collection<? extends GrantedAuthority> authList) {
	    List<String> roleStrList = null;
	    if (authList instanceof List<?>) {
	        List<SimpleGrantedAuthority> roleList = (List<SimpleGrantedAuthority>) authList;
	        roleStrList = new ArrayList<String>();
	        for(SimpleGrantedAuthority sgAuth : roleList) {
	            roleStrList.add(sgAuth.getAuthority());
	        }
	    }

	    return roleStrList;
	}
}
