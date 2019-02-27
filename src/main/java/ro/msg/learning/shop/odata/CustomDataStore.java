package ro.msg.learning.shop.odata;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.entity.Order;
import ro.msg.learning.shop.entity.OrderDetail;
import ro.msg.learning.shop.repository.OrderRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CustomDataStore {

    private final OrderRepository orderRepository;

    Map<String, Object> getOrder(final Long id) {
        Order order = orderRepository.findOrderById(id);
        return createOrder(order);
    }

    List<Map<String, Object>> getOrders() {
        List<Order> orderList = orderRepository.findAll();
        List<Map<String, Object>> res = new ArrayList<>();

        for (Order order : orderList) {
            res.add(createOrder(order));
        }
        return res;
    }

    List<Map<String, Object>> getOrderDetails(final Integer orderId) {
        List<OrderDetail> orderDetails = orderRepository.findById(orderId.longValue()).get().getOrderDetail();

        return createOrderDetails(orderDetails);
    }

    List<Map<String, Object>> getAllOrderDetails() {
        List<Order> orders = orderRepository.findAll();
        List<Map<String, Object>> res = new ArrayList<>();
        for (Order order : orders) {
            res.addAll(createOrderDetails(order.getOrderDetail()));
        }
        return res;
    }

    private Map<String, Object> createOrder(Order order) {
        Map<String, Object> data = new HashMap<>();

        data.put("Id", order.getId());
        data.put("ShippedFrom", order.getShippedFrom());
        data.put("Address", order.getAddress());
        data.put("Customer", order.getCustomer());
        data.put("OrderDate", order.getOrderDate());

        return data;
    }

    private List<Map<String, Object>> createOrderDetails(List<OrderDetail> orderDetails) {
        List<Map<String, Object>> dataList = new ArrayList<>();
        Map<String, Object> data = new HashMap<>();


        for (OrderDetail orderDetail : orderDetails) {
        data.put("Product", orderDetail.getProduct());
        data.put("Quantity", orderDetail.getQuantity());
        dataList.add(data);
        }

        return dataList;
    }
}
