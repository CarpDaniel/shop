package ro.msg.learning.shop.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ro.msg.learning.shop.entity.Stock;

import java.util.List;

public interface StockRepository extends CrudRepository<Stock, Long> {

    List<Stock> findByProduct(Integer product);

    Stock findByLocationAndProduct(Integer location, Integer product);

    List<Stock> findByLocation(Integer location);

    @Query(value = "SELECT s.location FROM Stock s WHERE s.product = ?1 AND s.quantity >= ?2")
    List<Integer> findLocationByProductAndQuantity(Integer product, Integer quantity);

    @Query(value = "SELECT s.location FROM Stock s WHERE s.product =?1 AND s.location = ?2")
    Integer findProductInLocation(Integer product, Integer location);
}
