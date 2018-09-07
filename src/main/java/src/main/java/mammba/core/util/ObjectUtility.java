/**
 * ObjectUtility.java - MAMMBA Application
 * 2018 All rights reserved.
 *
 */
package src.main.java.mammba.core.util;

import java.util.List;

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
}
