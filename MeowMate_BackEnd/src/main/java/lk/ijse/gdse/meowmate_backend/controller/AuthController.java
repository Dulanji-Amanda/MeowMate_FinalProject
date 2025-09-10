// corect code --------------------------------------------------------------------------------------------------------------------------------

package lk.ijse.gdse.meowmate_backend.controller;

import lk.ijse.gdse.meowmate_backend.dto.APIResponse;
import lk.ijse.gdse.meowmate_backend.dto.AuthDTO;
import lk.ijse.gdse.meowmate_backend.dto.AuthResponseDTO;
import lk.ijse.gdse.meowmate_backend.dto.UserDTO;
import lk.ijse.gdse.meowmate_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth/meowmate")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<APIResponse> registerUser(@RequestBody UserDTO registerDTO) {
        return ResponseEntity.ok(new APIResponse(
                200,
                "OK",
                userService.register(registerDTO)
        ));
    }


    @PostMapping("/login")
    public ResponseEntity<APIResponse> login(@RequestBody AuthDTO authDTO) {
        AuthResponseDTO authResponse = userService.authenticate(authDTO);

        // Make sure the token field is called accessToken, and include role and userId
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("accessToken", authResponse.getToken());
        responseData.put("userId", authResponse.getUserId());   // include userId
        responseData.put("email", authResponse.getEmail());

        return ResponseEntity.ok(new APIResponse(200, "OK", responseData));
    }



}



