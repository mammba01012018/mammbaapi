/**
 * MammbaSecurityConfig.java - MAMMBA Application
 * 2018 All rights reserved.
 *
 */
package src.main.java;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;


/**
 * This class serves as the security config of the Mammba API.
 *
 * @author Mardolfh Del Rosario
 *
 */
@Configuration
public class MammbaSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MammbaAuthenticationProvider authProvider;

    private static final Logger LOGGER = Logger.getLogger(MammbaSecurityConfig.class);

    /**
     * Configure mammba auth provider.
     * @param auth      AuthenticationManagerBuilder reference
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {

        auth.authenticationProvider(this.authProvider);
    }

    /**
     * Configure http filtering functions.
     * @param http      HTTP security.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().antMatchers("/init").anonymous();
        http.authorizeRequests().antMatchers("/register").anonymous();
        http.authorizeRequests().antMatchers("/login").anonymous();
        http.authorizeRequests().antMatchers("**/mammba-**").authenticated();
        http.authorizeRequests().antMatchers("**/mammba-**").hasRole("MAMMBA-MEMBER");
        http.logout().logoutSuccessUrl("/");

        // CSRF tokens handling
        http.addFilterAfter(new CsrfTokenResponseHeaderBindingForMammba(), CsrfFilter.class);
    }

}

