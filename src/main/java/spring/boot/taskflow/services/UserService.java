package spring.boot.taskflow.services;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import spring.boot.taskflow.dto.CreateUserRequestDTO;
import spring.boot.taskflow.dto.LoginUserRequestDTO;
import spring.boot.taskflow.entities.User;
import spring.boot.taskflow.exceptions.ExistingUserException;
import spring.boot.taskflow.exceptions.UserNotFoundException;
import spring.boot.taskflow.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User registerUser(CreateUserRequestDTO request) {
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());

        if (existingUser.isPresent()) {
            throw new ExistingUserException();
        }

        User user = request.toEntity();

        String password = request.getPassword();
        password = passwordEncoder.encode(password);
        user.setPassword(password);

        return userRepository.save(user);
    }

    public Boolean loginUser(LoginUserRequestDTO request) {
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());

        User user = existingUser.orElseThrow(() -> new UserNotFoundException());

        return passwordEncoder.matches(request.getPassword(), user.getPassword());
    }

    public User getUserByEmail(String email) {
        Optional<User> existingUser = userRepository.findByEmail(email);

        User user = existingUser.orElseThrow(() -> new UserNotFoundException());

        return user;
    }

    public User getUserById(long id) {
        Optional<User> existingUser = userRepository.findById(id);

        User user = existingUser.orElseThrow(() -> new UserNotFoundException());

        return user;
    }

    public void deleteUserById(long id) {
        userRepository.deleteById(id);
    }

    public void deleteUserByEmail(String email) {
        Optional<User> existingUser = userRepository.findByEmail(email);

        User user = existingUser.orElseThrow(() -> new UserNotFoundException());

        userRepository.deleteById(user.getId());
    }
}
