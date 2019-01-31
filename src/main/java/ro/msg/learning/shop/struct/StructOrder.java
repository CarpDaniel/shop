package ro.msg.learning.shop.struct;

import lombok.Data;
import ro.msg.learning.shop.entity.Address;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Map;

@Data
public class StructOrder implements Serializable {

    private Timestamp timestamp;
    private Address deliveryAddress;
    private Map<Integer, Integer> products;

    StructOrder() {}

    StructOrder(Timestamp timestamp, Address address, Map<Integer, Integer> products) {
        this.timestamp = timestamp;
        this.deliveryAddress = address;
        this.products = products;
    }

}
