package lk.ijse.gdse.meowmate_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @Column(nullable = false)
    private String listingName;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private String price;
}