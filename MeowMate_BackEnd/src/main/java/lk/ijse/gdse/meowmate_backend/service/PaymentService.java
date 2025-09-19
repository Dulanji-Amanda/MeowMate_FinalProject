package lk.ijse.gdse.meowmate_backend.service;


import lk.ijse.gdse.meowmate_backend.dto.PaymentDto;
import lk.ijse.gdse.meowmate_backend.entity.Payment;

import java.util.List;

public interface PaymentService {
    Payment savePayment(PaymentDto paymentDto);
}



