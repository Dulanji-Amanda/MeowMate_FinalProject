package lk.ijse.gdse.meowmate_backend.repo;

import lk.ijse.gdse.meowmate_backend.entity.Cat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatRepository extends JpaRepository<Cat, Long> {
    List<Cat> findByOwnerId(Long ownerId);
    List<Cat> findByStatus(String status);
}
