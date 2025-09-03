package lk.ijse.gdse.meowmate_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "lostCat")
public class LostCat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long catId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String lastSeenLocation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LostStatus status; // instead of "description"
}
