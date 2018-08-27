package dao;

import com.capgemini.StarterKitJpaStarterApplication;
import com.capgemini.dao.CustomerDao;
import com.capgemini.dao.TransactionDao;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StarterKitJpaStarterApplication.class, properties = "spring.profiles.active=mysql")
public class CustomerDaoTest {

    @Autowired
    CustomerDao customerDao;
    @Autowired
    TransactionDao transactionDao;

    @Test
    @Transactional
    //cascade test in customer
    public void shouldDeleteCustomerAndItsTransactions(){
        //given
        CustomerEntity customerEntity1 = new CustomerEntity("Dawid", "Dawidowski", new Date(2018-10-10),"daw@buziaczek.pl", 505896533, "Warszawa", null);
        CustomerEntity customerSave1 = customerDao.save(customerEntity1);

        TransactionEntity transactionEntity1 = new TransactionEntity( Status.INREALIZATION, new Date(2018-10-10), 0, customerEntity1, null);
        TransactionEntity transactionEntity2 = new TransactionEntity( Status.INREALIZATION, new Date(2017-10-10), 2, customerEntity1, null);
        TransactionEntity transactionEntity3 = new TransactionEntity( Status.INREALIZATION, new Date(2016-10-10), 3, customerEntity1, null);

        TransactionEntity save1 = transactionDao.save(transactionEntity1);
        TransactionEntity save2 = transactionDao.save(transactionEntity2);
        TransactionEntity save3 = transactionDao.save(transactionEntity3);

        Collection<TransactionEntity> transactions = new HashSet<>();
        transactions.add(save1);
        transactions.add(save2);
        transactions.add(save3);
        customerSave1.setTransactions(transactions);

        //when
        customerDao.delete(customerSave1.getId());

        //then
        long size = customerDao.count();
        System.out.println(size);
        Assertions.assertThat(size).isEqualTo(0);
        Assertions.assertThat(transactionDao.findAll()).isEmpty();


    }

}
