package ro.msg.learning.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.msg.learning.shop.entity.Revenue;
import ro.msg.learning.shop.service.RevenueService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class RevenueController {

    private final RevenueService revenueService;

    @GetMapping("/revenues")
    public List<Revenue> aggregateSalesByDate(@RequestParam String date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
        return revenueService.aggregateSalesByDate(formatter.parse(date));
    }
}
