package lk.ijse.gdse.meowmate_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String listingName;

    @Column(nullable = false)
    private String listingDescription;

    @Column(nullable = false)
    private String price; // Keep as String for now

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private String imageUrl;  // saved after uploading to ImgBB
}
