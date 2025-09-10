package lk.ijse.gdse.meowmate_backend.repo;

import lk.ijse.gdse.meowmate_backend.entity.Cat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CatRepository extends JpaRepository<Cat, Long> {
    List<Cat> findByOwnerId(Long ownerId);
    List<Cat> findByStatus(String status);

    Optional<String> findOwnerEmailById(@Param("id") Long id);
}
