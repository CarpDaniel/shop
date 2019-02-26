package ro.msg.learning.shop.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import ro.msg.learning.shop.entity.User;
import ro.msg.learning.shop.exception.RecoverableException;
import ro.msg.learning.shop.messages.ExceptionTextTemplate;
import ro.msg.learning.shop.repository.UserRepository;

public class CustomAuthenticationProvider extends DaoAuthenticationProvider {

    private final UserRepository userRepository;

    public CustomAuthenticationProvider(UserRepository userRepository, UserDetailsService userDetailsService){
        super();
        this.setUserDetailsService(userDetailsService);
        this.userRepository = userRepository;
    }

    @Override
    public Authentication authenticate(Authentication auth) {
        final User user = userRepository.findByUsername(auth.getName());
        if ((user == null)) {
            throw new RecoverableException(ExceptionTextTemplate.USERNAME_NOT_FOUND_EXCEPTION);
        }
        final Authentication result = super.authenticate(auth);
        return new UsernamePasswordAuthenticationToken(user, result.getCredentials(), result.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
