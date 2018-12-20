package com.plml.pplatform.Users;

import com.plml.pplatform.Validations.EmailAlreadyExistConstrain;
import com.plml.pplatform.Validations.UserAlreadyExistConstrain;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "application_user")
public class ApplicationUser {

    public ApplicationUser() {
    }

    public ApplicationUser(long id, String username, String password, String email, String name) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
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
    
    public long getId() {
        return id;
    }


    public String getUsername() {
        return username;
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
}
