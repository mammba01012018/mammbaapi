package src.main.java.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class MammbaAuthenticationLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

    private static final Logger LOGGER = Logger.getLogger(MammbaAuthenticationLogoutSuccessHandler.class);

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        LOGGER.info("Mammba user has logged out.");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
