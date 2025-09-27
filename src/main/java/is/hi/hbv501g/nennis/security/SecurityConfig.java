package is.hi.hbv501g.nennis.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    /**
     * Configures the security filter chain. This method is responsible for disabling CSRF protection,
     * permitting access to certain endpoints (e.g. /, /user/save, /auth/login, /product/all),
     * configuring form-based login and adding the JWT authentication filter before the username and password authentication filter.
     *
     * @param http          the HTTP security configuration
     * @param jwtAuthFilter the JWT authentication filter
     * @return the configured security filter chain
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthFilter jwtAuthFilter) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/user/save", "/auth/login", "/product/all").permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form.permitAll())
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Returns an instance of the UserDetailsService, which is responsible for loading user details
     * and roles from the database.
     *
     * @return an instance of the UserDetailsService
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return new AppUserDetailsService();
    }

    /**
     * Returns an instance of the DaoAuthenticationProvider, which is responsible for authenticating users
     * against the database. The UserDetailsService and PasswordEncoder are set on the provider before returning it.
     *
     * @return an instance of the DaoAuthenticationProvider
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /**
     * Returns a BCryptPasswordEncoder, which is used for encoding and decoding passwords.
     *
     * @return a BCryptPasswordEncoder instance
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Builds an AuthenticationManager instance using the given HttpSecurity instance.
     * The AuthenticationManager is built by adding the authentication provider to the builder and then calling build.
     * The resulting AuthenticationManager can be used to authenticate users.
     *
     * @param http the HttpSecurity instance to use when building the AuthenticationManager
     * @return the built AuthenticationManager
     * @throws Exception if an error occurs during building
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.authenticationProvider(authenticationProvider());
        return builder.build();
    }
}
