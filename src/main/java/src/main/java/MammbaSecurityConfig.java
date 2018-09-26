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
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.csrf.LazyCsrfTokenRepository;


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


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
          .antMatchers("/init").anonymous()
          .antMatchers("/mammba-user/getUser/*").hasAnyRole("MEMBER", "PARTNER", "ADMIN")
          .and()
          .formLogin().loginPage("/login")
          .defaultSuccessUrl("/mammba-user/getUser/")
          .and()
          .csrf().csrfTokenRepository( new LazyCsrfTokenRepository(new HttpSessionCsrfTokenRepository()));
    }

}

