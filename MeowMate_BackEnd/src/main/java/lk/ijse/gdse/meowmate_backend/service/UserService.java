package lk.ijse.gdse.meowmate_backend.service;

import lk.ijse.gdse.meowmate_backend.dto.AuthDTO;
import lk.ijse.gdse.meowmate_backend.dto.AuthResponseDTO;
import lk.ijse.gdse.meowmate_backend.dto.UserDTO;
import lk.ijse.gdse.meowmate_backend.entity.Role;
import lk.ijse.gdse.meowmate_backend.entity.User;
import lk.ijse.gdse.meowmate_backend.repo.UserRepository;
import lk.ijse.gdse.meowmate_backend.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }

    public AuthResponseDTO register(UserDTO registerDTO) {
        if (userRepository.existsByEmail(registerDTO.getEmail())) {
            return AuthResponseDTO.builder()
                    .message("User already exists with this email!")
                    .build();
        }

        User user = User.builder()
                .firstName(registerDTO.getFirstName())
                .lastName(registerDTO.getLastName())
                .email(registerDTO.getEmail())
                .password(passwordEncoder.encode(registerDTO.getPassword()))
                .role(Role.USER) // Default role as USER
                .build();

        userRepository.save(user);

        return AuthResponseDTO.builder()
                .message("User registered successfully!")
                .userId(user.getId())
                .email(user.getEmail())
                .build();
    }

    public AuthResponseDTO authenticate(AuthDTO authDTO) {
        try {
            // Use AuthenticationManager instead of manual password check
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authDTO.getEmail(), authDTO.getPassword())
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            User user = userRepository.findByEmail(authDTO.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            // Generate token with userId and roles
            List<String> roles = List.of(user.getRole().name());
            String token = jwtUtil.generateToken(userDetails, user.getId(), roles);

            return AuthResponseDTO.builder()
                    .token(token)
                    .message("Authentication successful")
                    .userId(user.getId())
                    .email(user.getEmail())
                    .build();

        } catch (Exception e) {
            return AuthResponseDTO.builder()
                    .message("Invalid email or password")
                    .build();
        }
    }
}
