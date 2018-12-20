package com.plml.pplatform.Users;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static java.util.Collections.emptyList;

public class UserPlatformService implements UserDetailsService {
    private UserRepository userRepository;

    public UserPlatformService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser applicationUser = userRepository.findByUsername(username);
        if (applicationUser == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(applicationUser.getUsername(), applicationUser.getPassword(), emptyList());
    }

    public ApplicationUser getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public ApplicationUser getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public ApplicationUser save(ApplicationUser user) {
        return userRepository.save(user);
    }
}
