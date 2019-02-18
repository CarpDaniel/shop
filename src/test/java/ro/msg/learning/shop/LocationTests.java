package ro.msg.learning.shop;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ro.msg.learning.shop.entity.Address;
import ro.msg.learning.shop.entity.Location;
import ro.msg.learning.shop.repository.LocationRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
public class LocationTests  {

    @Autowired
    private LocationRepository locationRepository;

    @Test
    public void testCreateLocation() {
        Location location = new Location();
        location.setName("Test Location");
        location.setAddress(createTestAddress());

        Location res = locationRepository.save(location);
        Assert.assertNotNull(res);
        Assert.assertEquals(location.getAddress(), res.getAddress());
        Assert.assertEquals(res.getAddress().getCity(), "Cluj-Napoca");
    }

    private Address createTestAddress() {
        Address testAddress = new Address();
        testAddress.setCity("Cluj-Napoca");
        testAddress.setCountry("Cluj");
        testAddress.setStreet("Dambovitei, nr. 14");
        return testAddress;
    }
}
