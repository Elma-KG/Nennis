package is.hi.hbv501g.nennis.controllers;

import is.hi.hbv501g.nennis.persistence.entities.User;
import is.hi.hbv501g.nennis.persistence.repositories.UserRepository;
import is.hi.hbv501g.nennis.persistence.repositories.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String goH0me(){
        return "This is publicly accessible withing needing authentication ";
    }
    @PostMapping("/user/save")
    public ResponseEntity<Object> saveUSer(@RequestBody User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User result = userRepository.save(user);
        if (result.getId() > 0){
            return ResponseEntity.ok("USer Was Saved");
        }
        return ResponseEntity.status(404).body("Error, User Not Saved");
    }
    @GetMapping("/product/all")
    public ResponseEntity<Object> getAllProducts(){
        return ResponseEntity.ok(productRepo.findAll());
    }
    @GetMapping("/users/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> getAllUsers(){
        return ResponseEntity.ok(userRepository.findAll());
    }
    @GetMapping("/users/single")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<Object> getMyDetails(){
        return ResponseEntity.ok(userRepository.findByEmail(getLoggedInUserDetails().getUsername()));
    }

    public UserDetails getLoggedInUserDetails(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.getPrincipal() instanceof UserDetails){
            return (UserDetails) authentication.getPrincipal();
        }
        return null;
    }
}
