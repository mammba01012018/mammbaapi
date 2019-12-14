/**
 * MammbaAuthenticationProvider.java - MAMMBA Application
 * 2018 All rights reserved.
 *
 */
package src.main.java.security;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import src.main.java.mammba.core.exception.ServiceException;
import src.main.java.mammba.core.service.LoginService;
import src.main.java.mammba.model.LoginModel;

/**
 * This class serves as authentication logic for Mammba.
 *
 * @author Mardolfh Del Rosario
 *
 */
@Component
public class MammbaAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private LoginService mammbaUserService;

    private static final Logger LOGGER = Logger.getLogger(MammbaAuthenticationProvider.class);


    /**
     * Implements the authenticate method.
     *
     * @param authentication        Authentication reference.
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        LoginModel model = new LoginModel();
        model.setUserEmail(name);
        model.setPassword(password);

        try {
            if (this.mammbaUserService.isLoginValid(model)) {
                List<GrantedAuthority> grantedAuthorities = null;
                String role = this.mammbaUserService.getUserType(name);


                if (role != null && ("member".equals(role) ||
                                     "partner".equals(role) ||
                                     "admin".equals(role))) {
                    grantedAuthorities = new ArrayList<>();
                    grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));
                    LOGGER.info("User is " + grantedAuthorities.get(0).getAuthority() );
                    return new UsernamePasswordAuthenticationToken(
                      name, password, grantedAuthorities);
                } else {
                    throw new AuthenticationCredentialsNotFoundException("MAMMBA[MAP]-01-Invalid Authorization.");
                }

            } else {
                throw new AuthenticationCredentialsNotFoundException("MAMMBA[MAP]-02-Invalid Authentication.");
            }
        } catch (ServiceException e) {
            throw new AuthenticationCredentialsNotFoundException("MAMMBA[MAP]-03-Invalid Access.", e);
        }
    }


    /**
     * Tells Spring to support the created authentication.
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
