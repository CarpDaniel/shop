package ro.msg.learning.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.entity.Location;
import ro.msg.learning.shop.entity.Order;
import ro.msg.learning.shop.entity.OrderDetail;
import ro.msg.learning.shop.entity.Revenue;
import ro.msg.learning.shop.repository.LocationRepository;
import ro.msg.learning.shop.repository.OrderRepository;
import ro.msg.learning.shop.repository.ProductRepository;
import ro.msg.learning.shop.repository.RevenueRepository;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RevenueService {

    private final RevenueRepository revenueRepository;

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    private final LocationRepository locationRepository;

    public List<Revenue> aggregateSalesByDate(Date date) {
        Map<Location, Revenue> revenuesWithLocationsMap = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);
        for (Order order : orderRepository.findAllByOrderDateIsBetween(calendar.getTime(), date)) {
            Location orderShippedFromLocation = locationRepository.findById(order.getShippedFrom().longValue()).get();
            BigDecimal totalShare = BigDecimal.ZERO;
            for (OrderDetail orderDetail : orderRepository.findById(order.getId().longValue()).get().getOrderDetail()) {
                totalShare = totalShare.add(productRepository.findById(orderDetail.getProduct().longValue()).get().getPrice().multiply(BigDecimal.valueOf(orderDetail.getQuantity())));
            }
            Revenue revenue;
            if (revenuesWithLocationsMap.get(orderShippedFromLocation) == null) {
                revenue = new Revenue();
                revenue.setSum(totalShare);

                revenuesWithLocationsMap.put(orderShippedFromLocation, revenue);
            } else {
                revenue = revenuesWithLocationsMap.get(orderShippedFromLocation);
                revenue.setSum(revenue.getSum().add(totalShare));
            }
            revenue.setDate(date);
            revenue.setLocation(orderShippedFromLocation);
        }
        return revenuesWithLocationsMap.values().stream().map(revenueRepository::save).collect(Collectors.toList());
    }
}
