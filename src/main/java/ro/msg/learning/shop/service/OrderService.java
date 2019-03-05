package ro.msg.learning.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.entity.*;
import ro.msg.learning.shop.exception.OrderNotCreatedException;
import ro.msg.learning.shop.repository.CustomerRepository;
import ro.msg.learning.shop.repository.OrderRepository;
import ro.msg.learning.shop.repository.StockRepository;
import ro.msg.learning.shop.strategy.LocationStrategy;
import ro.msg.learning.shop.struct.StructOrder;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final LocationStrategy locationStrategy;
    private final StockRepository stockRepository;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order placeNewOrder(StructOrder order) {
        Location location = locationStrategy.findLocation(order.getProducts());

        if (location != null) {
            Order res = new Order();

            for (Map.Entry entry : order.getProducts().entrySet()) {
                //update stocks with new quantities
                Stock foundStock = stockRepository.findByLocationAndProduct(location.getId().intValue(), (Integer) entry.getKey());
                foundStock.setQuantity(foundStock.getQuantity() - (Integer) entry.getValue());
                stockRepository.save(foundStock);

                //update order detail collection for new order
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setProduct((Integer) entry.getKey());
                orderDetail.setQuantity((Integer) entry.getValue());
                res.getOrderDetail().add(orderDetail);
            }

            //attach delivery address, shipment location and customer
            res.setAddress(order.getDeliveryAddress());
            res.setShippedFrom(location.getId().intValue());
            res.setOrderDate(new Date());

            //proper customer will be set after security implementation is done, use dummy 'til then
            res.setCustomer(customerRepository.save(new Customer("Erica", "Alvarez", "DanyC")));

            return orderRepository.save(res);
        }
        throw new OrderNotCreatedException();
    }
}
