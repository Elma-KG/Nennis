package is.hi.hbv501g.nennis.security;

import is.hi.hbv501g.nennis.persistence.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class OurUserInfoDetails implements UserDetails {
    private final String email;
    private final String password;
    private final List<GrantedAuthority> roles;

    public OurUserInfoDetails(User user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.roles = Arrays.stream(user.getRoles().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    /**
     * Returns the roles of the user as a collection of GrantedAuthority objects.
     * Each authority is a SimpleGrantedAuthority object which contains the role name.
     * The roles are fetched from the user object given in the constructor.
     *
     * @return A collection of GrantedAuthority objects representing the user's roles.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }


    /**
     * Returns the user's password as a string.
     *
     * @return The user's password.
     */
    @Override
    public String getPassword() {
        return this.password;
    }

    /**
     * Returns the username of the user.
     * This is the same as the email of the user.
     *
     * @return The username of the user.
     */
    @Override
    public String getUsername() {
        return this.email;
    }


    /**
     * Always returns true as the expiration of the user's account is not currently being checked.
     *
     * @return true
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Always returns true as the locking of the user's account is not currently being checked.
     *
     * @return true
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Always returns true as the expiration of the user's credentials is not currently being checked.
     *
     * @return true
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Always returns true as the user is always enabled.
     *
     * @return true
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
