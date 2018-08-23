package dao;

import com.capgemini.StarterKitJpaStarterApplication;
import com.capgemini.dao.CustomerDao;
import com.capgemini.dao.CustomerDaoSpringData;
import com.capgemini.domain.CustomerEntity;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StarterKitJpaStarterApplication.class, properties = "spring.profiles.active=mysql") //
public class CustomerDaoSpringDataTest {

    @Autowired
    CustomerDao customerDao;
    @Autowired
    CustomerDaoSpringData customerDaoSpringData;

    @Test
    @Transactional
   // @Rollback(false)
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
        List<CustomerEntity> customerSearched = customerDaoSpringData.findByName("Eliasz");

        //then
        Assertions.assertThat(customerSearched.get(0).getSurname()).isEqualTo("Grzyb");
    }

    @Test
    @Transactional
    public void shouldNotFindCustomerByNameThatNotExist(){
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
        List<CustomerEntity> customerSearched = customerDaoSpringData.findByName("Marek");

        //then
        Assertions.assertThat(customerSearched).isEmpty();
    }

    @Test
    @Transactional
    public void shouldFindCustomerBySurname(){
        //given
        customerDao.deleteAll();

        CustomerEntity customerEntity5 = new CustomerEntity("Dawid", "Dawidowski", new Date(2018-10-10),"daw@buziaczek.pl", 505896533, "Warszawa", null);
        CustomerEntity customerEntity6 = new CustomerEntity("Tomek", "Torok", new Date(2000-10-9),"daw@buziaczek.pl", 505896533, "Warszawa", null);
        CustomerEntity customerEntity7 = new CustomerEntity("Eliasz", "Grzyb", new Date(2000-9-9),"daw@buziaczek.pl", 505896533, "Warszawa", null);
        CustomerEntity customerEntity8 = new CustomerEntity("Robert", "Podolski", new Date(2000-9-9),"daw@buziaczek.pl", 505896533, "Warszawa", null);

        CustomerEntity customerSave5 = customerDao.save(customerEntity5);
        CustomerEntity customerSave6 = customerDao.save(customerEntity6);
        CustomerEntity customerSave7 = customerDao.save(customerEntity7);
        CustomerEntity customerSave8 = customerDao.save(customerEntity8);

        //when
        List<CustomerEntity> customerSearched2 = customerDaoSpringData.findBySurname("Grzyb");

        //then
        Assertions.assertThat(customerSearched2.get(0).getName()).isEqualTo("Eliasz");
    }

    @Test
    @Transactional
    public void shouldFindTwoCustomersByName(){
        //given
        CustomerEntity customerEntity1 = new CustomerEntity("Dawid", "Dawidowski", new Date(2018-10-10),"daw@buziaczek.pl", 505896533, "Warszawa", null);
        CustomerEntity customerEntity2 = new CustomerEntity("Tomek", "Torok", new Date(2000-10-9),"daw@buziaczek.pl", 505896533, "Warszawa", null);
        CustomerEntity customerEntity3 = new CustomerEntity("Tomek", "Grzyb", new Date(2000-9-9),"daw@buziaczek.pl", 505896533, "Warszawa", null);
        CustomerEntity customerEntity4 = new CustomerEntity("Robert", "Podolski", new Date(2000-9-9),"daw@buziaczek.pl", 505896533, "Warszawa", null);

        CustomerEntity customerSave1 = customerDao.save(customerEntity1);
        CustomerEntity customerSave2 = customerDao.save(customerEntity2);
        CustomerEntity customerSave3 = customerDao.save(customerEntity3);
        CustomerEntity customerSave4 = customerDao.save(customerEntity4);

        //when
        List<CustomerEntity> customerSearched = customerDaoSpringData.findByName("Tomek");

        //then
        Assertions.assertThat(customerSearched.size()).isEqualTo(2);
    }



}
