package lk.ijse.gdse.meowmate_backend.repo;

import lk.ijse.gdse.meowmate_backend.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByUser_Id(Long userId);
}
