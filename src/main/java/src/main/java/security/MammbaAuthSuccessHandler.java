package src.main.java.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import src.main.java.mammba.core.exception.ServiceException;
import src.main.java.mammba.core.service.UserService;
import src.main.java.mammba.model.Member;
import src.main.java.mammba.model.Partner;


@Component
public class MammbaAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private static final Logger LOGGER = Logger.getLogger(MammbaAuthSuccessHandler.class);

    @Autowired
    @Qualifier(value="userMemberService")
    private UserService userMemberService;

    @Autowired
    @Qualifier(value="userPartnerService")
    private UserService userPartnerService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_OK);

        ObjectMapper mapper = new ObjectMapper();
        ArrayNode roles = mapper.createArrayNode();

        for (GrantedAuthority authority : authentication.getAuthorities()) {
            roles.add(authority.getAuthority());
        }

        String user = (String)authentication.getPrincipal();
        ObjectNode node = mapper.createObjectNode();
        node.put("logUsing", user);

        try {
            if (roles.get(0).asText().equals("ROLE_MEMBER")) {
                Member member = (Member)this.userMemberService.getUserDetails(user);
                JSONObject obj = new JSONObject(member);
                node.putPOJO("member", obj);
            } else if (roles.get(0).asText().equals("ROLE_PARTNER")) {
                Partner partner = (Partner)this.userPartnerService.getUserDetails(user);
                JSONObject obj = new JSONObject(partner);
                node.putPOJO("partner", obj);
            }

            CsrfToken token = (CsrfToken)request.getAttribute(CsrfToken.class.getName());
            LOGGER.info("tokenlog="  + token.getToken());
            node.put("_csrf", token.getToken());

            response.setContentType("application/json");
            PrintWriter writer = response.getWriter();
            writer.print(node);
            writer.flush();
            writer.close();
        } catch (ServiceException e) {
            throw new ServletException();
        }

    }
}
