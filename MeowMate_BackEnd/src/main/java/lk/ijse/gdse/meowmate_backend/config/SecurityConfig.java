//package lk.ijse.gdse.meowmate_backend.config;
//
//import lk.ijse.gdse.meowmate_backend.util.JWTAuthFilter;
//import lk.ijse.gdse.meowmate_backend.config.OAuth2LoginSuccessHandler;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//import java.util.List;
//
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class SecurityConfig {
//
//    private final JWTAuthFilter jwtAuthFilter;
//    private final UserDetailsService userDetailsService;
//    private final PasswordEncoder passwordEncoder; // Inject from ApplicationConfig
//    private final CorsConfigurationSource corsConfigurationSource;
//    private final OAuth2LoginSuccessHandler oauth2LoginSuccessHandler;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .csrf(csrf -> csrf.disable())
//                .cors(cors -> cors.configurationSource(corsConfigurationSource))
//        .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/api/hello/**").permitAll()
//                        .requestMatchers("/api/auth/**").permitAll() // Keep this for any future API auth endpoints
//                        .requestMatchers("/auth/meowmate/**").permitAll() // Add this for the actual auth controller
//
//            .requestMatchers("/oauth2/**").permitAll()
//            .requestMatchers("/oauth2/success").permitAll()
//
//                // new added
//                .requestMatchers("/oauth2/failure").permitAll()
//
//                        .requestMatchers("/api/cats/**").authenticated() //
//
//                        .requestMatchers("/api/cats/all").permitAll() // allow public access
//                        .requestMatchers("/api/adoptions/**").authenticated()
//
//                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
//
//
//                        .requestMatchers("/api/chat/**").permitAll()
//                        .requestMatchers( "/api/adoptions/request/**").authenticated()
//
//
//
//
//                        .requestMatchers("/api/items/**").permitAll()
//
//
//
//
//
//
//
//                        .requestMatchers("/api/lostcats/**").permitAll() // Make LostCat endpoints public
//                        .requestMatchers("/api/lostcats/sighting/**").permitAll()
//                        .anyRequest().authenticated()
//
//
//                )
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authenticationProvider(authenticationProvider())
//                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
//                // Configure OAuth2 login to use our success handler which issues a JWT
//                .oauth2Login(oauth2 -> oauth2.successHandler(oauth2LoginSuccessHandler))
//                .build();
//    }
//
//
//
//
//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setUserDetailsService(userDetailsService);
//        provider.setPasswordEncoder(passwordEncoder);
//        return provider;
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//        return config.getAuthenticationManager();
//    }
//
//
//
//}
//
//


















package lk.ijse.gdse.meowmate_backend.config;

import lk.ijse.gdse.meowmate_backend.util.JWTAuthFilter;
import lk.ijse.gdse.meowmate_backend.config.OAuth2LoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JWTAuthFilter jwtAuthFilter;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder; // Inject from ApplicationConfig
    private final CorsConfigurationSource corsConfigurationSource;
    private final OAuth2LoginSuccessHandler oauth2LoginSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/api/hello/**").permitAll()
                                .requestMatchers("/api/auth/**").permitAll() // Keep this for any future API auth endpoints
                                .requestMatchers("/auth/meowmate/**").permitAll() // Add this for the actual auth controller

                                .requestMatchers("/api/items/saveItems").permitAll() // ✅ allow items API

                                .requestMatchers("/oauth2/**").permitAll()
                                .requestMatchers("/oauth2/success").permitAll()

                                // new added
                                .requestMatchers("/oauth2/failure").permitAll()

                                .requestMatchers("/api/cats/**").authenticated() //

                                .requestMatchers("/api/cats/all").permitAll() // allow public access
                                .requestMatchers("/api/adoptions/**").authenticated()

                                .requestMatchers("/api/admin/**").hasRole("ADMIN")


                                .requestMatchers("/api/chat/**").permitAll()
                                .requestMatchers( "/api/adoptions/request/**").authenticated()

                                // Ensure both base path and subpaths are allowed (avoid OAuth redirect)
                                .requestMatchers("/api/items", "/api/items/**").permitAll()

                                // Allow orders and payments for checkout flow
                                .requestMatchers("/api/orders", "/api/orders/**").permitAll()
                                .requestMatchers("/api/payments", "/api/payments/**").permitAll()


                                .requestMatchers("/api/orders/create").permitAll()
                                .requestMatchers("/api/orders/user/**").permitAll()



                                .requestMatchers("/api/items/**").permitAll()

//                                .requestMatchers("/api/items/**").hasRole("ADMIN")




                                .requestMatchers("/api/payments/create").permitAll()




                                .requestMatchers("/api/lostcats/**").permitAll() // Make LostCat endpoints public
                                .requestMatchers("/api/lostcats/sighting/**").permitAll()
                                .anyRequest().authenticated()


                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                // Configure OAuth2 login to use our success handler which issues a JWT
                .oauth2Login(oauth2 -> oauth2.successHandler(oauth2LoginSuccessHandler))
                .build();
    }




    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }





}


