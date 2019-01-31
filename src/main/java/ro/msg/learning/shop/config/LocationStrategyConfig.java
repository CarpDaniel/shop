package ro.msg.learning.shop.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import ro.msg.learning.shop.repository.LocationRepository;
import ro.msg.learning.shop.repository.StockRepository;
import ro.msg.learning.shop.strategy.AllInOneLocationStrategy;
import ro.msg.learning.shop.strategy.LocationStrategy;

@Configuration
@RequiredArgsConstructor
public class LocationStrategyConfig {

    private final StockRepository stockRepository;
    private final LocationRepository locationRepository;

    @Primary
    @Bean
    public LocationStrategy getLocationStrategy(@Value("${shop.strategy}") String type) {
        switch (type) {
            case "AllInOneLocationStrategy":
                return new AllInOneLocationStrategy(stockRepository, locationRepository);
            default:
                return new AllInOneLocationStrategy(stockRepository, locationRepository);
        }
    }
}
