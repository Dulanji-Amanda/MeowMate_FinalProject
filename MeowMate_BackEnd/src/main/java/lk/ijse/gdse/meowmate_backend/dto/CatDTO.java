package lk.ijse.gdse.meowmate_backend.dto;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CatDTO {
    private Long id;
    private String catName;
    private String breed;
    private Integer age;
    private String status;
    private Long ownerId;
}

