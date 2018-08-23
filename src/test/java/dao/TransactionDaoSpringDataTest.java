package dao;

import com.capgemini.StarterKitJpaStarterApplication;
import com.capgemini.dao.CustomerDao;
import com.capgemini.dao.CustomerDaoSpringData;
import com.capgemini.dao.TransactionDao;
import com.capgemini.dao.TransactionDaoSpringData;
import com.capgemini.domain.CustomerEntity;
import com.capgemini.domain.TransactionEntity;
import com.capgemini.enums.Status;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StarterKitJpaStarterApplication.class) //, properties = "spring.profiles.active=mysql"
public class TransactionDaoSpringDataTest {

    @Autowired
    TransactionDao transactionDao;
    @Autowired
    CustomerDao customerDao;
    @Autowired
    TransactionDaoSpringData transactionDaoSpringData;

    @Test
    @Transactional
    public void shouldFindTransactionsByCustomerId(){
        //given
        CustomerEntity customerEntity1 = new CustomerEntity("Dawid", "Dawidowski", new Date(2018-10-10),"daw@buziaczek.pl", 505896533, "Warszawa", null);
        CustomerEntity customerEntity2 = new CustomerEntity("Tomek", "Torok", new Date(2000-10-9),"daw@buziaczek.pl", 505896533, "Warszawa", null);
        CustomerEntity customerEntity3 = new CustomerEntity("Eliasz", "Grzyb", new Date(2000-9-9),"daw@buziaczek.pl", 505896533, "Warszawa", null);
        CustomerEntity customerEntity4 = new CustomerEntity("Robert", "Podolski", new Date(2000-9-9),"daw@buziaczek.pl", 505896533, "Warszawa", null);

        CustomerEntity customerSave1 = customerDao.save(customerEntity1);
        CustomerEntity customerSave2 = customerDao.save(customerEntity2);
        CustomerEntity customerSave3 = customerDao.save(customerEntity3);
        CustomerEntity customerSave4 = customerDao.save(customerEntity4);

        TransactionEntity transactionEntity1 = new TransactionEntity( Status.INREALIZATION, new Date(2018-10-10), 10, customerSave1, null);
        TransactionEntity transactionEntity2 = new TransactionEntity( Status.INREALIZATION, new Date(2018-10-10), 20, customerSave2, null);
        TransactionEntity transactionEntity3 = new TransactionEntity( Status.INREALIZATION, new Date(2018-10-10), 30, customerSave3, null);

        TransactionEntity save1 = transactionDao.save(transactionEntity1);
        TransactionEntity save2 = transactionDao.save(transactionEntity2);
        TransactionEntity save3 = transactionDao.save(transactionEntity3);

        //when
        List<TransactionEntity> transactionsByCustomer = transactionDaoSpringData.findByCustomerId(2L);

        //then
        int size = transactionsByCustomer.size();
        int amountCustomer = transactionEntity2.getAmount();
        Assertions.assertThat(size).isEqualTo(1);
        Assertions.assertThat(amountCustomer).isEqualTo(20);

    }





}
