package src.main.java.mammba.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class MammbaUserDetails extends User {

    /**
     *
     */
    private static final long serialVersionUID = 8410052330564411326L;
    private MammbaUser user;

    public MammbaUserDetails(String username, String password, boolean enabled, boolean accountNonExpired,
            boolean credentialsNonExpired, boolean accountNonLocked,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);

    }

    public MammbaUser getUser() {
        return user;
    }

    public void setUser(MammbaUser user) {
        this.user = user;
    }


}
