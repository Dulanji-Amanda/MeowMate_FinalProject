package lk.ijse.gdse.meowmate_backend.service.impl;

import lk.ijse.gdse.meowmate_backend.entity.AddItem;
import lk.ijse.gdse.meowmate_backend.entity.Orders;
import lk.ijse.gdse.meowmate_backend.entity.User;
import lk.ijse.gdse.meowmate_backend.repo.AddItemRepository;
import lk.ijse.gdse.meowmate_backend.repo.OrdersRepository;
import lk.ijse.gdse.meowmate_backend.repo.UserRepository;
import lk.ijse.gdse.meowmate_backend.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdersServiceImpl implements OrdersService {

    private final OrdersRepository ordersRepository;
    private final UserRepository userRepository;
    private final AddItemRepository listingsRepository;

    @Override
    @Transactional
    public Orders createOrder(Long userId, Long listingId, int qty) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        AddItem addItem = listingsRepository.findById(listingId)
                .orElseThrow(() -> new RuntimeException("Listing not found"));

        double price = Double.parseDouble(addItem.getPrice());
        double total = price * qty;

        Orders order = Orders.builder()
                .user(user)
                .items(addItem)
                .quantity(qty)
                .unitPrice(String.valueOf(price))
                .total(String.valueOf(total))
                .status("PAID")
                .createdAt(LocalDateTime.now())
                .build();

        return ordersRepository.save(order);
    }

    @Override
    public List<Orders> getOrdersByUser(Long userId) {
        return ordersRepository.findByUser_Id(userId);
    }


}
