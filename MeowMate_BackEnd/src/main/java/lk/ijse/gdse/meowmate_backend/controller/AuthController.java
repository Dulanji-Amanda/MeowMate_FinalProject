package lk.ijse.gdse.meowmate_backend.controller;

import lk.ijse.gdse.meowmate_backend.dto.APIResponse;
import lk.ijse.gdse.meowmate_backend.dto.AuthDTO;
import lk.ijse.gdse.meowmate_backend.dto.UserDTO;
import lk.ijse.gdse.meowmate_backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<APIResponse> registerUser(
            @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(new APIResponse(
                200,
                "OK",
                authService.register(userDTO)));
    }
    @PostMapping("/login")
    public ResponseEntity<APIResponse> login(
            @RequestBody AuthDTO authDTO) {
        return ResponseEntity.ok(new APIResponse(
                200,
                "OK",
                authService.authenticate(authDTO)));
    }
}
