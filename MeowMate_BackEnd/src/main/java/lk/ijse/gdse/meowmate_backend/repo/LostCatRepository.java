package lk.ijse.gdse.meowmate_backend.repo;

import lk.ijse.gdse.meowmate_backend.entity.LostCat;
import lk.ijse.gdse.meowmate_backend.entity.LostStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LostCatRepository extends JpaRepository<LostCat, Long> {
    List<LostCat> findByStatus(LostStatus status);

    Optional<LostCat> findByCatId(Long catId);

}
