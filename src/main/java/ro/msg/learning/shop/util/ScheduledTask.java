package ro.msg.learning.shop.util;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.service.RevenueService;

import java.util.Date;

@RequiredArgsConstructor
@Component
public class ScheduledTask {

    private final RevenueService revenueService;
    private static final Logger log = LoggerFactory.getLogger(ScheduledTask.class);

    @Scheduled(cron = "0 0 0 * * ?")
    public void scheduleDailySalesRevenues() {
        log.info("Scheduled sales aggregation started at: " + new Date());
        revenueService.aggregateSalesByDate(new Date());
        log.info("Scheduled sales aggregation finished at: " + new Date());
    }
}
