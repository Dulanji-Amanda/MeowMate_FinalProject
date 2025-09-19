package lk.ijse.gdse.meowmate_backend.controller;

import lk.ijse.gdse.meowmate_backend.dto.APIResponse;
import lk.ijse.gdse.meowmate_backend.dto.UserDTO;
import lk.ijse.gdse.meowmate_backend.entity.User;
import lk.ijse.gdse.meowmate_backend.repo.UserRepository;
import lk.ijse.gdse.meowmate_backend.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    private Long extractUserId(String authHeader){
        if(authHeader==null || !authHeader.startsWith("Bearer ")) return null;
        String token = authHeader.substring(7);
        return jwtUtil.extractUserId(token);
    }

    @GetMapping("/me")
    public ResponseEntity<APIResponse> getCurrentUser(@RequestHeader(name="Authorization", required=false) String authHeader){
        Long userId = extractUserId(authHeader);
        if(userId==null) return ResponseEntity.status(401).body(new APIResponse(401,"UNAUTHORIZED", null));
        Optional<User> userOpt = userRepository.findById(userId);
        if(userOpt.isEmpty()) return ResponseEntity.status(404).body(new APIResponse(404,"NOT_FOUND", null));
        User user = userOpt.get();
        UserDTO dto = UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
        return ResponseEntity.ok(new APIResponse(200, "OK", dto));
    }

    @PutMapping("/me")
    public ResponseEntity<APIResponse> updateCurrentUser(@RequestHeader(name="Authorization", required=false) String authHeader,
                                                         @RequestBody UserDTO updateDTO){
        Long userId = extractUserId(authHeader);
        if(userId==null) return ResponseEntity.status(401).body(new APIResponse(401,"UNAUTHORIZED", null));
        User user = userRepository.findById(userId).orElse(null);
        if(user==null) return ResponseEntity.status(404).body(new APIResponse(404,"NOT_FOUND", null));

        if(updateDTO.getUsername()!=null && !updateDTO.getUsername().isBlank()){
            user.setUsername(updateDTO.getUsername());
        }
        if(updateDTO.getEmail()!=null && !updateDTO.getEmail().isBlank() && !updateDTO.getEmail().equals(user.getEmail())){
            if(userRepository.existsByEmail(updateDTO.getEmail())){
                return ResponseEntity.badRequest().body(new APIResponse(400,"EMAIL_ALREADY_IN_USE", null));
            }
            user.setEmail(updateDTO.getEmail());
        }
        if(updateDTO.getPassword()!=null && !updateDTO.getPassword().isBlank()){
            user.setPassword(passwordEncoder.encode(updateDTO.getPassword()));
        }
        userRepository.save(user);
        UserDTO dto = UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
        return ResponseEntity.ok(new APIResponse(200,"UPDATED", dto));
    }
}
