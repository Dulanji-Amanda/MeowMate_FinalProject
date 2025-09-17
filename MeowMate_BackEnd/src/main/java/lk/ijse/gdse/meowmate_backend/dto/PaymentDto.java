package lk.ijse.gdse.meowmate_backend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDto {
    private String listingName;
    private Integer quantity;
    private String price;
}