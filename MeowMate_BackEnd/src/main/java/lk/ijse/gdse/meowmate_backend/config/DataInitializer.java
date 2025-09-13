package lk.ijse.gdse.meowmate_backend.config;

import jakarta.annotation.PostConstruct;
import lk.ijse.gdse.meowmate_backend.entity.User;
import lk.ijse.gdse.meowmate_backend.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepo;
    private final PasswordEncoder encoder;

    @PostConstruct
    public void init() {
        if (!userRepo.existsByEmail("admin@meowmate.com")) {
            User admin = User.builder()
                    .username("admin")
                    .email("admin@meowmate.com")
                    .password(encoder.encode("admin123"))
                    .role(User.Role.ADMIN)
                    .build();
            userRepo.save(admin);
            System.out.println("✅ Default Admin Created -> admin@meowmate.com / admin123");
        }
    }
}
