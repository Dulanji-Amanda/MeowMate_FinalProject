package lk.ijse.gdse.meowmate_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddItemDto {
    private Long id;
    private String listingName;
    private String listingDescription;
    private String price;
    private Integer quantity;
    private String imageUrl; // Base64 or uploaded image URL
}
