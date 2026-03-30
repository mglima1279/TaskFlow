package spring.boot.taskflow.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.boot.taskflow.dto.CreateUserRequestDTO;
import spring.boot.taskflow.dto.LoginUserRequestDTO;
import spring.boot.taskflow.entities.User;
import spring.boot.taskflow.security.CustomUserDetails;
import spring.boot.taskflow.services.JwtService;
import spring.boot.taskflow.services.UserService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody CreateUserRequestDTO request) {

        User user = userService.registerUser(request);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Falha ao criar o usuário");
        }

        UserDetails userDetails = new CustomUserDetails(user);

        String token = jwtService.generateToken(userDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(token);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginUserRequestDTO request) {
        Boolean validLogin = userService.loginUser(request);

        if (!validLogin) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Falha no login");
        }

        User user = userService.getUserByEmail(request.getEmail());
        UserDetails userDetails = new CustomUserDetails(user);
        String token = jwtService.generateToken(userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }
}
