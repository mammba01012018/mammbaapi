/**
 * MammbaSecurityConfig.java - MAMMBA Application
 * 2018 All rights reserved.
 *
 */
package src.main.java;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


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


    /**
     * Configure mammba auth provider.
     * @param auth      AuthenticationManagerBuilder reference
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {

        auth.authenticationProvider(authProvider);
    }

    /**
     * Configure http filtering functions.
     * @param http      HTTP security.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception  {
        http.httpBasic().and().authorizeRequests().antMatchers("**/mammba-**")
        .hasRole("MAMMBA-MEMBER").and()
        .csrf().disable().headers().frameOptions().disable();
    }

}

