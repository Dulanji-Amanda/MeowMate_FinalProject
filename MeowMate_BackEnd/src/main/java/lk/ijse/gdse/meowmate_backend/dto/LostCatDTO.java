package lk.ijse.gdse.meowmate_backend.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LostCatDTO {
    private Long id;
    private Long catId;
    private Long userId;
    private String lastSeenLocation;
    private String description; // MISSING, FOUND

    // Data fetched from Cat table
    private String catName;
    private String breed;
    private Integer age;
    private String imageUrl;

    private String status;

}
