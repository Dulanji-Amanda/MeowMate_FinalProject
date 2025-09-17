package lk.ijse.gdse.meowmate_backend.controller;
import lk.ijse.gdse.meowmate_backend.dto.PaymentDto;
import lk.ijse.gdse.meowmate_backend.entity.Payment;
import lk.ijse.gdse.meowmate_backend.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<Payment> createPayment(@RequestBody PaymentDto paymentDto) {
        Payment payment = paymentService.savePayment(paymentDto);
        return ResponseEntity.ok(payment);
    }
}