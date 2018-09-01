package src.main.java.mammba.core.util;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ObjectUtility {
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
