package ro.msg.learning.shop.odata;


import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class CustomServletConfig {

    @Bean
    public ServletRegistrationBean exampleServletBean(CustomODataServlet customODataServlet) {
        ServletRegistrationBean bean = new ServletRegistrationBean(
                customODataServlet, "/odata/*");
        bean.setLoadOnStartup(1);
        return bean;
    }
}
