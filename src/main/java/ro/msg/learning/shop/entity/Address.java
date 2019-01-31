package ro.msg.learning.shop.entity;

import lombok.Data;

import javax.persistence.Embeddable;

@Embeddable
@Data
public class Address {

    private String country;
    private String city;
    private String county;
    private String street;
}
