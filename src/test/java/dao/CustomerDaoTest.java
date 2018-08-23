package dao;

import com.capgemini.StarterKitJpaStarterApplication;
import com.capgemini.dao.CustomerDao;
import com.capgemini.dao.CustomerDaoSpringData;
import com.capgemini.domain.CustomerEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StarterKitJpaStarterApplication.class, properties = "spring.profiles.active=mysql")
public class CustomerDaoTest {

    @Autowired
    CustomerDao customerDao;
    @Autowired
    CustomerDaoSpringData customerDaoSpringData;

    @Test
    public void shouldFindCustomerByName(){
        //given
        CustomerEntity customerEntity1 = new CustomerEntity("Dawid", "Dawidowski", new Date(2018-10-10),"daw@buziaczek.pl", 505896533, "Warszawa", null);
        CustomerEntity customerEntity2 = new CustomerEntity("Tomek", "Torok", new Date(2000-10-9),"daw@buziaczek.pl", 505896533, "Warszawa", null);
        CustomerEntity customerEntity3 = new CustomerEntity("Eliasz", "Grzyb", new Date(2000-9-9),"daw@buziaczek.pl", 505896533, "Warszawa", null);
        CustomerEntity customerEntity4 = new CustomerEntity("Robert", "Podolski", new Date(2000-9-9),"daw@buziaczek.pl", 505896533, "Warszawa", null);

        CustomerEntity customerSave1 = customerDao.save(customerEntity1);
        CustomerEntity customerSave2 = customerDao.save(customerEntity2);
        CustomerEntity customerSave3 = customerDao.save(customerEntity3);
        CustomerEntity customerSave4 = customerDao.save(customerEntity4);

        //when


    }

}
