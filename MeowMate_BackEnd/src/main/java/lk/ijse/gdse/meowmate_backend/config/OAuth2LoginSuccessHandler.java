//package lk.ijse.gdse.meowmate_backend.config;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lk.ijse.gdse.meowmate_backend.entity.Role;
//import lk.ijse.gdse.meowmate_backend.entity.User;
//import lk.ijse.gdse.meowmate_backend.repo.UserRepository;
//import lk.ijse.gdse.meowmate_backend.util.JWTUtil;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.util.List;
//
///**
// * On successful OAuth2 login, create or lookup the user by email, issue a JWT and
// * redirect to a small page which will store the token in localStorage and redirect
// * to the frontend dashboard.
// */
//@Component
//@RequiredArgsConstructor
//public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {
//
//    private final UserRepository userRepository;
//    private final JWTUtil jwtUtil;
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        Object principal = authentication.getPrincipal();
//        if (!(principal instanceof OAuth2User oauthUser)) {
//            response.sendRedirect("/oauth2/failure");
//            return;
//        }
//
//        String email = (String) oauthUser.getAttributes().get("email");
//        String name = (String) oauthUser.getAttributes().getOrDefault("name", email);
//
//        // Find or create user
//        User user = userRepository.findByEmail(email).orElseGet(() -> {
//            User u = User.builder()
//                    .email(email)
//                    .username(name)
//                    // Random placeholder password - not used for OAuth users
//                    .password("oauth2user")
//                    .role(Role.USER)
//                    .build();
//            return userRepository.save(u);
//        });
//
//        // Build a simple UserDetails-like object to generate token
//        org.springframework.security.core.userdetails.User userDetails =
//                new org.springframework.security.core.userdetails.User(
//                        user.getEmail(),
//                        user.getPassword(),
//                        List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
//                );
//
//        String token = jwtUtil.generateToken(userDetails, user.getId(), List.of(user.getRole().name()));
//
//        // Redirect to a safe endpoint that will set the token in localStorage
//        String redirectUrl = "/oauth2/success?token=" + token;
//        response.sendRedirect(redirectUrl);
//    }
//}





























package lk.ijse.gdse.meowmate_backend.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.gdse.meowmate_backend.entity.User;
import lk.ijse.gdse.meowmate_backend.repo.UserRepository;
import lk.ijse.gdse.meowmate_backend.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Handles successful OAuth2 login for Google, creates user if not exists,
 * generates JWT, and redirects to frontend with token.
 */
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        Object principal = authentication.getPrincipal();
        if (!(principal instanceof OAuth2User oauthUser)) {
            // Not an OAuth2 user -> redirect to failure
            response.sendRedirect("/oauth2/failure");
            return;
        }

        // Extract email and name from OAuth2 attributes
        String email = (String) oauthUser.getAttributes().get("email");
        if (email == null || email.isEmpty()) {
            response.sendRedirect("/oauth2/failure");
            return;
        }
        String name = (String) oauthUser.getAttributes().getOrDefault("name", email);

        // Find existing user or create new one
        User user = userRepository.findByEmail(email).orElseGet(() -> {
            User newUser = User.builder()
                    .email(email)
                    .username(name)
                    .password("oauth2user") // placeholder password
                    .role(User.Role.USER)   // ✅ use User.Role here
                    .build();
            return userRepository.save(newUser);
        });

        // Build UserDetails for JWT generation
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())))
                .build();

        // Generate JWT token
        String token = jwtUtil.generateToken(userDetails, user.getId(), List.of(user.getRole().name()));

        // Redirect to frontend with token
        String redirectUrl = "/oauth2/success?token=" + token;
        response.sendRedirect(redirectUrl);
    }
}
