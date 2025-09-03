package lk.ijse.gdse.meowmate_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "adoption_applications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdoptionApplications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Generic pet id (works for dogs or cats)
    @Column(nullable = false)
    private Long petId;

    @Column(nullable = false)
    private Long adopterId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Adoption_Status status;

    @Column(nullable = false, updatable = false)
    private Timestamp createdAt;

    @PrePersist
    protected void prePersist() {
        if (createdAt == null) createdAt = new Timestamp(System.currentTimeMillis());
        if (status == null) status = Adoption_Status.PENDING;
    }
}
