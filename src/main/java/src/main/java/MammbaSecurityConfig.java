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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


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
          .antMatchers("/init", "/registerMember", "/registerPartner", "/login", "/logout", "/deniedAccess").permitAll()
          .antMatchers("/mammba-user/**").hasAnyRole("MEMBER", "PARTNER", "ADMIN")
          .anyRequest().denyAll()
          .and()
          .formLogin()
              .defaultSuccessUrl("/mammba-user/getUser/")
              .failureForwardUrl("/deniedAccess")
          .and()
          .logout().permitAll().clearAuthentication(true)
              .deleteCookies("JSESSIONID").invalidateHttpSession(true)
              .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/logoutMammba")
          .and()
          .csrf().csrfTokenRepository(
               new LazyCsrfTokenRepository(new HttpSessionCsrfTokenRepository())).ignoringAntMatchers("/login");
    }

}

