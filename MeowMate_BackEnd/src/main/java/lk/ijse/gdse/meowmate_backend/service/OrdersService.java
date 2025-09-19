package lk.ijse.gdse.meowmate_backend.service;

import lk.ijse.gdse.meowmate_backend.entity.Orders;
import org.springframework.data.domain.jaxb.SpringDataJaxb;

import java.util.List;

public interface OrdersService {
    Orders createOrder(Long userId, Long listingId, int qty);
    List<Orders> getOrdersByUser(Long userId);


}
