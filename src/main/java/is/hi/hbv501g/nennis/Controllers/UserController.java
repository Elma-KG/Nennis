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

/**
 * This endpoint is publicly accessible without needing authentication.
 * It simply returns a string "This is publicly accessible without needing authentication".
 */
    @GetMapping("/")
    public String goH0me(){
        return "This is publicly accessible without needing authentication ";
    }
    /**
     * Saves a user to the database.
     * @param user the user to save
     * @return a ResponseEntity containing a message indicating whether the user was saved or not
     */
    @PostMapping("/user/save")
    public ResponseEntity<Object> saveUSer(@RequestBody User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User result = userRepository.save(user);
        if (result.getId() > 0){
            return ResponseEntity.ok("User Was Saved");
        }
        return ResponseEntity.status(404).body("Error, User Not Saved");
    }
    /**
     * Returns a list of all products in the database.
     * @return a ResponseEntity containing a list of all products in the database
     */
    @GetMapping("/product/all")
    public ResponseEntity<Object> getAllProducts(){
        return ResponseEntity.ok(productRepo.findAll());
    }
/**
 * Returns a list of all users in the database.
 * This endpoint is only accessible by users with the role 'ADMIN'.
 * @return a ResponseEntity containing a list of all users in the database
 */
    @GetMapping("/users/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> getAllUsers(){
        return ResponseEntity.ok(userRepository.findAll());
    }
    /**
     * Returns the details of the currently logged in user.
     * This endpoint is only accessible by users with the role 'ADMIN' or 'USER'.
     * @return a ResponseEntity containing the details of the currently logged in user
     */
    @GetMapping("/users/single")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<Object> getMyDetails(){
        return ResponseEntity.ok(userRepository.findByEmail(getLoggedInUserDetails().getUsername()));
    }

    /**
     * Returns the details of the currently logged in user.
     * This method is used to obtain the details of the currently logged in user.
     * It checks if the authentication object is not null and if the principal is an instance of UserDetails.
     * If both conditions are true, it returns the UserDetails object. Otherwise, it returns null.
     * @return the details of the currently logged in user, or null if not logged in
     */
    public UserDetails getLoggedInUserDetails(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.getPrincipal() instanceof UserDetails){
            return (UserDetails) authentication.getPrincipal();
        }
        return null;
    }
}
