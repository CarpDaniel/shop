package ro.msg.learning.shop.repository;

import org.springframework.data.repository.CrudRepository;
import ro.msg.learning.shop.entity.Location;

public interface LocationRepository extends CrudRepository<Location, Long> {

}
