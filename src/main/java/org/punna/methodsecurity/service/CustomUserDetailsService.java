package org.punna.methodsecurity.service;

import org.punna.methodsecurity.model.User;
import org.punna.methodsecurity.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    // hardcoded users here. usually we get users from database
    private final UserRepository userRepository;

    public CustomUserDetailsService() {
        User user1 = new User.UserBuilder()
                .username("mahesh")
                .password("password")
                .JobLevel("7")
                // HOD: head of a department.
                .roles(List.of("CONSULTANT", "AVP", "HOD"))
                .build();
        User user2 = new User.UserBuilder()
                .username("pawan")
                .password("password")
                .JobLevel("8")
                // BOD: board of directors
                .roles(List.of("CONSULTANT", "AVP", "HOD", "BOD"))

                .build();
        this.userRepository = new UserRepository(List.of(user1, user2));
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.getUser(username);
    }
}
