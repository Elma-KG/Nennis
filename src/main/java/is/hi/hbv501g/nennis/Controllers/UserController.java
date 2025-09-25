package is.hi.hbv501g.nennis.Controllers;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @GetMapping("/me")
    public Object me(Authentication auth) {
        // returns the authenticated principal username (email)
        return java.util.Map.of("email", auth.getName());
    }
}
