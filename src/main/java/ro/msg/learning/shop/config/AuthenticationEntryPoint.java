package ro.msg.learning.shop.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.messages.ExceptionTextTemplate;
import ro.msg.learning.shop.util.ResourceBundleUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class AuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx)
            throws IOException {
        response.addHeader("WWW-Authenticate", "Basic realm=" +getRealmName());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter writer = response.getWriter();
        writer.println("HTTP Status 401 - " + ResourceBundleUtil.getMessageFromEnum(ExceptionTextTemplate.ETT_AUTHENTICATION_EXCEPTION));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        setRealmName("Shop");
        super.afterPropertiesSet();
    }

}