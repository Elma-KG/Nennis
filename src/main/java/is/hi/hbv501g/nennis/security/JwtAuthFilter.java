package is.hi.hbv501g.nennis.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final AppUserDetailsService userDetailsService;

    public JwtAuthFilter(JwtService jwtService, AppUserDetailsService uds) {
        this.jwtService = jwtService;
        this.userDetailsService = uds;
    }

    /**
     * This method is responsible for validating JWT tokens and authenticating users.
     * It takes the Authorization header from the incoming request and checks if it is valid.
     * If valid, it sets the authentication object in the SecurityContextHolder.
     * If not valid, it does not do anything and lets the request pass through.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {

        String header = req.getHeader("Authorization");
        String token = (StringUtils.hasText(header) && header.startsWith("Bearer "))
                ? header.substring(7) : null;

        if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                String subject = jwtService.extractSubject(token);
                var userDetails = userDetailsService.loadUserByUsername(subject);

                if (jwtService.validateToken(token)) {
                    var auth = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            } catch (Exception ignored) {
            }
        }


        chain.doFilter(req, res);
    }
}