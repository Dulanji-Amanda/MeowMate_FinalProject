package lk.ijse.gdse.meowmate_backend.repo;

import lk.ijse.gdse.meowmate_backend.entity.AdoptionApplications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdoptionApplicationRepository extends JpaRepository<AdoptionApplications, Long> {
    List<AdoptionApplications> findByPetId(Long petId);
    List<AdoptionApplications> findByAdopterId(Long adopterId);
}
