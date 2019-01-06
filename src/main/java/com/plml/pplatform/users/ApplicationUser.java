package com.plml.pplatform.users;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.plml.pplatform.validations.EmailAlreadyExistConstrain;
import com.plml.pplatform.validations.UserAlreadyExistConstrain;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "application_user")
public class ApplicationUser implements UserDetails {

    public ApplicationUser() {
        super();
        this.enabled = false;
        this.role = "USER";
    }

    public ApplicationUser(long id, String username, String password, String email, String name, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.role = role;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "Username cannot be empty")
    @Size(min = 1, max = 12, message = "Username should have from 1 to 12 signs")
    @UserAlreadyExistConstrain
    private String username;

    @NotNull(message = "Please provide password")
    private String password;

    @NotNull(message = "Email cannot be empty")
    @EmailAlreadyExistConstrain
    @Email(message = "Email address is invalid")
    private String email;

    @NotNull(message = "User name is not specify")
    @Size(min = 1, max = 12, message = "User name length should beeb between 1 and 12")
    private String name;

    private boolean enabled;
    private String role;
    private boolean credentialsNonExpired;
    private boolean accountNonLocked;
    private boolean accountNonExpired;
    private boolean passwordReset;

    @Override
    @JsonDeserialize(using = GrantedAuthorityDeserializer.class)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        String ROLE_PREFIX = "ROLE_";
        authorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + role));

        return authorities;
    }
    
    public long getId() {
        return id;
    }


    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isPasswordReset() {
        return passwordReset;
    }

    public void setPasswordReset(boolean passwordReset) {
        this.passwordReset = passwordReset;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
