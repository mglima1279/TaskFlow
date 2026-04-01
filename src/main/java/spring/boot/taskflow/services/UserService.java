package spring.boot.taskflow.services;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import spring.boot.taskflow.dto.CreateUserRequestDTO;
import spring.boot.taskflow.dto.LoginUserRequestDTO;
import spring.boot.taskflow.entities.User;
import spring.boot.taskflow.exceptions.TaskNotFoundException;
import spring.boot.taskflow.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User registerUser(CreateUserRequestDTO request) {
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());

        if (existingUser.isPresent()) {
            return null;
        }

        User user = request.toEntity();

        String password = request.getPassword();
        password = passwordEncoder.encode(password);
        user.setPassword(password);

        return userRepository.save(user);
    }

    public Boolean loginUser(LoginUserRequestDTO request) {
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());

        if (existingUser.isEmpty()) {
            throw new TaskNotFoundException();
        }

        User user = existingUser.get();

        return passwordEncoder.matches(request.getPassword(), user.getPassword());
    }

    public User getUserByEmail(String email) {
        Optional<User> existingUser = userRepository.findByEmail(email);

        if (existingUser.isEmpty()) {
            throw new TaskNotFoundException();
        }

        return existingUser.get();
    }

    public User getUserById(long id) {
        Optional<User> existingUser = userRepository.findById(id);

        if (existingUser.isEmpty()) {
            throw new TaskNotFoundException();
        }

        return existingUser.get();
    }

    public void deleteUserById(long id) {
        Optional<User> existingUser = userRepository.findById(id);

        if (existingUser.isPresent()) {
            userRepository.deleteById(id);
        }
    }

    public void deleteUserByEmail(String email) {
        Optional<User> existingUser = userRepository.findByEmail(email);

        if (existingUser.isPresent()) {
            userRepository.deleteById(existingUser.get().getId());
        }
    }
}
