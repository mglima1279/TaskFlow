package spring.boot.taskflow.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.boot.taskflow.dto.UserResponseDTO;
import spring.boot.taskflow.entities.User;
import spring.boot.taskflow.services.UserService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getUser(@AuthenticationPrincipal UserDetails userDetails) {

        User user = userService.getUserByEmail(userDetails.getUsername());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(UserResponseDTO.fromEntity(user));
    }
}
