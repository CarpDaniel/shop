package ro.msg.learning.shop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ro.msg.learning.shop.exception.RecoverableException;
import ro.msg.learning.shop.messages.ExceptionTextTemplate;

@Configuration
@EnableWebSecurity
public class SecurityConfigAdapter extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Override
    protected void configure(HttpSecurity httpSecurity) {
        try {
            httpSecurity.authorizeRequests().antMatchers("/securityNone").permitAll().anyRequest().authenticated().and().httpBasic().authenticationEntryPoint(authenticationEntryPoint);
        } catch (Exception e) {
            throw new RecoverableException(e.getMessage());
        }
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder builder) {
        try {
            builder.inMemoryAuthentication().withUser("DanyC").password(passwordEncoder().encode("DanyCPass")).authorities("ROLE_USER");
        } catch (Exception e) {
            throw new RecoverableException(ExceptionTextTemplate.ETT_AUTHENTICATION_EXCEPTION);
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
