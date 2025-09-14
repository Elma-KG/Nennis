package is.hi.hbv501g.nennis;

import is.hi.hbv501g.nennis.user.Role;
import is.hi.hbv501g.nennis.user.User;
import is.hi.hbv501g.nennis.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class DemoDataConfig {

    @Bean
    CommandLineRunner seedUsers(UserService users, PasswordEncoder encoder) {
        return args -> {
            if (!users.usernameTaken("demo")) {
                users.save(new User(
                        "demo",
                        "demo@example.com",
                        encoder.encode("demo123"),
                        Set.of(Role.USER)
                ));
            }
            if (!users.usernameTaken("admin")) {
                users.save(new User(
                        "admin",
                        "admin@example.com",
                        encoder.encode("admin123"),
                        Set.of(Role.ADMIN)
                ));
            }
        };
    }
}
