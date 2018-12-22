package com.plml.pplatform.users.signOnProcess.verificationtoken;

import com.plml.pplatform.users.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository
        extends JpaRepository<VerificationToken, Long> {

    VerificationToken findByToken(String token);

    VerificationToken findByUser(ApplicationUser user);
}