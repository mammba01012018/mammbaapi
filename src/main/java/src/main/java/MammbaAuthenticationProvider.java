/**
 * MammbaAuthenticationProvider.java - MAMMBA Application
 * 2018 All rights reserved.
 *
 */
package src.main.java;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import src.main.java.mammba.core.exception.ServiceException;
import src.main.java.mammba.core.service.MammbaUserService;
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
    private MammbaUserService mammbaUserService;


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
                GrantedAuthority grantedAuth = new GrantedAuthority() {

                    /**
                     * Serial generated.
                     */
                    private static final long serialVersionUID = 1L;

                    @Override
                    public String getAuthority() {
                        return "MAMMBA-MEMBER";
                    }
                };

                Collection<GrantedAuthority> roles = new ArrayList<>();
                roles.add(grantedAuth);
                return new UsernamePasswordAuthenticationToken(
                  name, password, roles);

            } else {
                throw new AuthenticationCredentialsNotFoundException("MAMMBA[MAP]-01-Invalid Authentication.");
            }
        } catch (ServiceException e) {
            throw new AuthenticationCredentialsNotFoundException("MAMMBA[MAP]-02-Invalid Access.", e);
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
