package ro.msg.learning.shop.strategy;

import org.springframework.stereotype.Component;
import ro.msg.learning.shop.entity.Location;

import java.util.Map;

@Component
public interface LocationStrategy {

    Location findLocation(Map<Integer, Integer> productsAndQuantity);
}
