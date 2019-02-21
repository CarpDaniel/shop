package ro.msg.learning.shop.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.entity.Location;
import ro.msg.learning.shop.exception.LocationNotFoundException;
import ro.msg.learning.shop.repository.LocationRepository;
import ro.msg.learning.shop.repository.StockRepository;

import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class AllInOneLocationStrategy implements LocationStrategy {

    private final StockRepository stockRepository;
    private final LocationRepository locationRepository;

    @Override
    public Location findLocation(Map<Integer, Integer> productsAndQuantity) {

        Set<Integer> suitableLocations = new HashSet<>();

        for (Entry entry : productsAndQuantity.entrySet()) {
            suitableLocations.addAll(stockRepository.findLocationByProductAndQuantity((Integer) entry.getKey(), (Integer) entry.getValue()));
            if (suitableLocations.isEmpty())
                throw new LocationNotFoundException();
        }

        for (Entry entry : productsAndQuantity.entrySet()) {
            suitableLocations = suitableLocations.stream().filter(item -> stockRepository.findProductInLocation((Integer) entry.getKey(), item) != null).collect(Collectors.toSet());
        }

        return !suitableLocations.isEmpty() ? locationRepository.findById(suitableLocations.iterator().next().longValue()).get() : null;
    }
}
