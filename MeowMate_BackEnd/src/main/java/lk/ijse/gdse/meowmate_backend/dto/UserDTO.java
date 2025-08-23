package lk.ijse.gdse.meowmate_backend.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String password;
    private String role;
}