package ro.msg.learning.shop.entity;

import lombok.Data;

import javax.persistence.Embeddable;

@Data
@Embeddable
public class OrderDetail {

    public OrderDetail() {}

    private Integer product;
    private Integer quantity;
}
