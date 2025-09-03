package lk.ijse.gdse.meowmate_backend.dto;

import lombok.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdoptionApplicationDTO {
    private Long id;
    private Long petId;
    private Long adopterId;
    private String status;
    private Timestamp createdAt;
}
