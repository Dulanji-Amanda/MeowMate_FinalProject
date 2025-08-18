package lk.ijse.gdse.meowmate_backend.service;

import lk.ijse.gdse.meowmate_backend.dto.AuthDTO;
import lk.ijse.gdse.meowmate_backend.dto.AuthResponseDTO;
import lk.ijse.gdse.meowmate_backend.dto.UserDTO;
import lk.ijse.gdse.meowmate_backend.entity.Role;
import lk.ijse.gdse.meowmate_backend.entity.User;
import lk.ijse.gdse.meowmate_backend.repo.UserRepository;
import lk.ijse.gdse.meowmate_backend.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    public AuthResponseDTO authenticate(AuthDTO authDTO) {
        User user=
                userRepository.findByUsername(authDTO.getUsername())
                        .orElseThrow(
                                ()->new UsernameNotFoundException
                                        ("Username not found"));
        if (!passwordEncoder.matches(
                authDTO.getPassword(),
                user.getPassword())) {
            throw new BadCredentialsException("Incorrect password");
        }
        String token=jwtUtil.generateToken(authDTO.getUsername());
        return  new AuthResponseDTO(token);
    }
    public String register(UserDTO userDTO) {
        if(userRepository.findByUsername(
                userDTO.getUsername()).isPresent()){
            throw new RuntimeException("Username already exists");
        }
        User user=User.builder()
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(
                        userDTO.getPassword()))
                .role(Role.valueOf(userDTO.getRole()))
                .build();
        userRepository.save(user);
        return  "User Registration Success";
    }
}
