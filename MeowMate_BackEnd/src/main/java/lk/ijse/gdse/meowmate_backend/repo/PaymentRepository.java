package lk.ijse.gdse.meowmate_backend.repo;


import lk.ijse.gdse.meowmate_backend.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}