package ro.msg.learning.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ro.msg.learning.shop.entity.Stock;
import ro.msg.learning.shop.service.StockService;

import java.util.List;

@ControllerAdvice
@RequiredArgsConstructor
@RestController
public class StockController {

    private final StockService stockService;

    @GetMapping(value = "/stocks/{id}", produces = "text/csv")
    public List<Stock> exportStocksCSV(@PathVariable("id") Integer pLocationId) {
        return stockService.findByLocation(pLocationId);
    }

}
