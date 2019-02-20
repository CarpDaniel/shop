package ro.msg.learning.shop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import ro.msg.learning.shop.entity.Customer;
import ro.msg.learning.shop.repository.CustomerRepository;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableScheduling
public class ShopApplication {

	private static final Logger log = LoggerFactory.getLogger(ShopApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(ShopApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(CustomerRepository customerRepository) {
		return (args -> {
			customerRepository.save(new Customer("Daniel", "LastName", "Danny"));
			customerRepository.save(new Customer("Istvan", "AnotherLastName", "SPD"));

			//read all customers
			log.info("ALL CUSTOMERS USING findAll(): ");
			log.info("----------------------------------");
			for (Customer customer : customerRepository.findAll()) {
				log.info(customer.toString());
			}
			log.info("");

			//read by id
			customerRepository.findById(1L).ifPresent(customer -> {
				log.info("Customer found with findById(1L)");
			log.info("--------------------------------");
			log.info(customer.toString());
			log.info("");});
		});
	}
}
