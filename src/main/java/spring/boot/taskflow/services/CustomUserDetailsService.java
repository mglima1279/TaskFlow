package spring.boot.taskflow.services;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import spring.boot.taskflow.entities.User;
import spring.boot.taskflow.repositories.UserRepository;
import spring.boot.taskflow.security.CustomUserDetails;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    public CustomUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        Optional<User> user = repository.findByEmail(email);

        if (user.isEmpty()) {
            return null;
        }

        return new CustomUserDetails(user.get());
    }
}