package com.plml.pplatform.users.signOnProcess.verificationtoken;

import com.plml.pplatform.users.ApplicationUser;

import javax.persistence.*;
import java.util.Date;

@Entity
public class VerificationToken {
    public VerificationToken() {
    }

    public VerificationToken(Long id, String token, ApplicationUser user, Date expiryDate) {
        this.id = id;
        this.token = token;
        this.user = user;
        this.expiryDate = expiryDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public String getToken() {
        return token;
    }

    @OneToOne(targetEntity = ApplicationUser.class, fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "user_id")
    private ApplicationUser user;

    private Date expiryDate;


    public void setToken(String token) {
        this.token = token;
    }

    public ApplicationUser getUser() {
        return user;
    }

    public void setUser(ApplicationUser user) {
        this.user = user;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    private String token;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}