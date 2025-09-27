package is.hi.hbv501g.nennis.security;

import is.hi.hbv501g.nennis.persistence.entities.User;
import is.hi.hbv501g.nennis.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@Configuration
public class AppUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);
        return user.map(OurUserInfoDetails::new).orElseThrow(() -> new UsernameNotFoundException("User Does Not Exist"));
    }
}
