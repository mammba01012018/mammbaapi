/**
 * MammbaSecurityConfig.java - MAMMBA Application
 * 2018 All rights reserved.
 *
 */
package src.main.java.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.csrf.LazyCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


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

    @Autowired
    private MammbaEntryPointHandler authEntryPoint;

    @Autowired
    private MammbaAuthSuccessHandler authSuccessHandler;

    @Autowired
    private MammbaAuthenticationFailureHandler authFailureHandler;

    @Autowired
    private MammbaAuthenticationLogoutSuccessHandler authLogoutHandler;


    //private static final Logger LOGGER = Logger.getLogger(MammbaSecurityConfig.class);

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
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs",
                                   "/configuration/ui",
                                   "/swagger-resources/**",
                                   "/configuration/security",
                                   "/swagger-ui.html",
                                   "/webjars/**",
                                   "/city/**",
                                   "/book/**",
                                  "/tour/**");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
          .antMatchers("/registerMember", "/registerPartner", "/login", "/logout", "/securityQuestions/**").permitAll()
          .antMatchers("/mammba-user/**").hasAnyRole("MEMBER", "PARTNER", "ADMIN")
          //.antMatchers("/tour/**").hasAnyRole("MEMBER", "PARTNER")
          .anyRequest().denyAll()
          .and()
          .exceptionHandling()
              .authenticationEntryPoint(this.authEntryPoint)
          .and()
          .formLogin()
              .loginProcessingUrl("/login")
              .loginPage("/").permitAll()
              .successHandler(this.authSuccessHandler)
              .failureHandler(this.authFailureHandler)
          .and()
          .logout()
              .logoutUrl("/logout")
              .deleteCookies("JSESSIONID")
              .invalidateHttpSession(true)
              .logoutSuccessHandler(this.authLogoutHandler)
          .and()
          .csrf().csrfTokenRepository(
               new LazyCsrfTokenRepository(new HttpSessionCsrfTokenRepository()))
              .ignoringAntMatchers("/registerMember", "/registerPartner", "/login", "/logout", "/securityQuestions/**"
            		  ,"/city/**")
          .and()
              .cors().configurationSource(this.corsConfigurationSource());
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }



}

