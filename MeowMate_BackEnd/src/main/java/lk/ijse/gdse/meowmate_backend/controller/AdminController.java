//package lk.ijse.gdse.meowmate_backend.controller;
//
//import lk.ijse.gdse.meowmate_backend.entity.User;
//import lk.ijse.gdse.meowmate_backend.repo.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.*;
//
//@RestController
//@RequestMapping("/api/admin")
//@RequiredArgsConstructor
//public class AdminController {
//
//    private final UserRepository userRepo;
//    private final PasswordEncoder encoder;
//
//    @GetMapping("/admins")
//    public List<User> getAllAdmins() {
//        return userRepo.findAll().stream()
//                .filter(u -> u.getRole() == User.Role.ADMIN)
//                .toList();
//    }
//
//    @PostMapping("/admins")
//    public User createAdmin(@RequestBody Map<String, String> body) {
//        User admin = User.builder()
//                .username(body.get("username"))
//                .email(body.get("email"))
//                .password(encoder.encode(body.get("password")))
//                .role(User.Role.ADMIN)
//                .build();
//        return userRepo.save(admin);
//    }
//
//    @DeleteMapping("/admins/{id}")
//    public Map<String, String> deleteAdmin(@PathVariable Long id) {
//        userRepo.deleteById(id);
//        return Map.of("message", "Admin deleted");
//    }
//
//    @GetMapping("/stats")
//    public Map<String, Object> getStats() {
//        long total = userRepo.count();
//        long admins = userRepo.findAll().stream().filter(u -> u.getRole() == User.Role.ADMIN).count();
//        return Map.of(
//                "totalUsers", total,
//                "totalAdmins", admins,
//                "totalRegularUsers", total - admins
//        );
//    }
//}

















package lk.ijse.gdse.meowmate_backend.controller;

import lk.ijse.gdse.meowmate_backend.entity.User;
import lk.ijse.gdse.meowmate_backend.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/admins")
    public ResponseEntity<List<User>> getAllAdmins() {
        return ResponseEntity.ok(userRepo.findByRole(User.Role.ADMIN));
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        long total = userRepo.count();
        long admins = userRepo.findByRole(User.Role.ADMIN).size();
        long regular = total - admins;
        return ResponseEntity.ok(Map.of(
                "totalUsers", total,
                "totalAdmins", admins,
                "totalRegularUsers", regular
        ));
    }

    @PostMapping("/admins")
    public ResponseEntity<?> createAdmin(@RequestBody Map<String,String> body) {
        String username = body.getOrDefault("username", "").trim();
        String email = body.getOrDefault("email", "").trim();
        String password = body.getOrDefault("password", "").trim();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message","All fields (username,email,password) are required"));
        }
        if (userRepo.existsByEmail(email)) {
            return ResponseEntity.badRequest().body(Map.of("message","Email already exists"));
        }

        User admin = User.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .role(User.Role.ADMIN)
                .build();
        userRepo.save(admin);
        return ResponseEntity.ok(Map.of(
                "id", admin.getId(),
                "username", admin.getUsername(),
                "email", admin.getEmail(),
                "role", admin.getRole().name(),
                "message", "Admin created successfully"
        ));
    }

    @DeleteMapping("/admins/{id}")
    public ResponseEntity<?> deleteAdmin(@PathVariable Long id) {
        Optional<User> u = userRepo.findById(id);
        if (u.isEmpty() || u.get().getRole() != User.Role.ADMIN) {
            return ResponseEntity.status(404).body(Map.of("message","Admin not found"));
        }
        userRepo.deleteById(id);
        return ResponseEntity.ok(Map.of("message","Admin deleted successfully"));
    }
}