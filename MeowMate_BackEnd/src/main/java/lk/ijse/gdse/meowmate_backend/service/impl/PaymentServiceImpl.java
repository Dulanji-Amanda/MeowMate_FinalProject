package lk.ijse.gdse.meowmate_backend.service.impl;
import lk.ijse.gdse.meowmate_backend.dto.PaymentDto;
import lk.ijse.gdse.meowmate_backend.entity.Payment;
import lk.ijse.gdse.meowmate_backend.repo.PaymentRepository;
import lk.ijse.gdse.meowmate_backend.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    public Payment savePayment(PaymentDto dto) {
        Payment payment = Payment.builder()
                .listingName(dto.getListingName())
                .quantity(dto.getQuantity())
                .price(dto.getPrice())
                .build();
        return paymentRepository.save(payment);
    }



}


