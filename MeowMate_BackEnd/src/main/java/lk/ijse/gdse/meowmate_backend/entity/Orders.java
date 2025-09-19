package lk.ijse.gdse.meowmate_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Each order belongs to a user
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Each order belongs to a listing/item
    @ManyToOne(optional = false)
    @JoinColumn(name = "items_id", nullable = false)
    private AddItem items;

//    @Column(nullable = false)
    private Integer quantity;

//    @Column(nullable = false)
    private String unitPrice; // keep string for consistency with listing

//    @Column(nullable = false)
    private String total;

//    @Column(nullable = false)
    private String status; // e.g. PAID, PENDING

//    @Column(nullable = false)
    private LocalDateTime createdAt;
}