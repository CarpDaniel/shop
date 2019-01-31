package ro.msg.learning.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ro.msg.learning.shop.entity.Order;
import ro.msg.learning.shop.service.OrderService;
import ro.msg.learning.shop.struct.StructOrder;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/orders")
    List<Order> getAllOrders() {
        return orderService.findAll();
    }

    @PostMapping("/newOrder")
    Order newOrder(@RequestBody StructOrder newOrder) {
        return orderService.placeNewOrder(newOrder);
    }
}
