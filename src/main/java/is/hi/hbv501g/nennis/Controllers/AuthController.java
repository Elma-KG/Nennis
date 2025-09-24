package is.hi.hbv501g.nennis.Controllers;

import is.hi.hbv501g.nennis.Persistence.Repositories.UserRepository;
import is.hi.hbv501g.nennis.dto.LoginRequest;
import is.hi.hbv501g.nennis.dto.LoginResponse;
import is.hi.hbv501g.nennis.security.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthController(AuthenticationManager am, JwtService jwtService, UserRepository ur) {
        this.authenticationManager = am;
        this.jwtService = jwtService;
        this.userRepository = ur;
    }

    // POST /api/auth/login
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        
        String token = jwtService.generateToken(authentication);
        return ResponseEntity.ok(new LoginResponse(token));
    }
}
