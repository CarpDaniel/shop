package ro.msg.learning.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.entity.User;
import ro.msg.learning.shop.exception.RecoverableException;
import ro.msg.learning.shop.messages.ExceptionTextTemplate;
import ro.msg.learning.shop.repository.UserRepository;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) {
        User user = userRepository.findByUsername(s);
        if (user == null)
            throw new RecoverableException(ExceptionTextTemplate.ETT_AUTHENTICATION_EXCEPTION);
        return org.springframework.security.core.userdetails.User.withUsername(user.getUsername()).password(user.getPassword()).roles(user.getAuthority()).build();
    }
}
