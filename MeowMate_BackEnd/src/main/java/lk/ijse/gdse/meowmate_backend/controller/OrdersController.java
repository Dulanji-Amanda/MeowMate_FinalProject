package lk.ijse.gdse.meowmate_backend.controller;

import lk.ijse.gdse.meowmate_backend.entity.Orders;
import lk.ijse.gdse.meowmate_backend.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class OrdersController {
    private final OrdersService ordersService;

    @PostMapping("/create")
    public Orders createOrder(@RequestParam Long userId,
                              @RequestParam Long listingId,
                              @RequestParam int quantity) {
        return ordersService.createOrder(userId, listingId, quantity);
    }

    @GetMapping("/user/{userId}")
    public List<Orders> getOrdersByUser(@PathVariable Long userId) {
        return ordersService.getOrdersByUser(userId);
    }
}
