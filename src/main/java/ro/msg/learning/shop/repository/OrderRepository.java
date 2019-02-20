package ro.msg.learning.shop.repository;

import org.springframework.data.repository.CrudRepository;
import ro.msg.learning.shop.entity.Customer;
import ro.msg.learning.shop.entity.Order;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends CrudRepository <Order, Long> {

    List<Order> findByCustomer(Customer customer);

    List<Order> findAllByOrderDateIsBetween(Date start, Date end);
}
