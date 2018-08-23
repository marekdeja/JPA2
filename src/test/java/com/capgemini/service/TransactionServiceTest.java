package com.capgemini.service;

import com.capgemini.StarterKitJpaStarterApplication;
import com.capgemini.dao.CustomerDao;
import com.capgemini.dao.TransactionDao;
import com.capgemini.dao.TransactionDaoSpringData;
import com.capgemini.domain.TransactionEntity;
import com.capgemini.enums.Status;
import com.capgemini.service.impl.TransactionServiceImpl;
import com.capgemini.types.CustomerTO;
import com.capgemini.types.TransactionTO;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StarterKitJpaStarterApplication.class)
public class TransactionServiceTest {

    @Autowired
    TransactionService transactionService;
    @Autowired
    TransactionServiceImpl transactionServiceImpl;
    @Autowired
    CustomerService customerService;
    @Autowired
    CustomerDao customerDao;
    @Autowired
    TransactionDao transactionDao;
    @Autowired
    TransactionDaoSpringData transactionDaoSpringData;

    @Test
    @Transactional
    public void shouldSaveThreeTransactionsAndSizeIsThree(){
        //given
        TransactionTO transactionTO = TransactionTO.builder()
                .status(Status.INREALIZATION)
                .date(new Date(2018-1-5))
                .amount(300)
                .build();

        //when
        TransactionTO transactionTOSaved = transactionService.saveTransaction(transactionTO);

        //then
        long size = transactionDao.count();

        Assertions.assertThat(size).isEqualTo(1);
        Assertions.assertThat(transactionTOSaved.getAmount()).isEqualTo(300);
    }


    @Test
    @Transactional
    public void shouldDeleteTransactionAndSizeIsThree(){
        //given

        TransactionTO transactionTO1 = TransactionTO.builder()
                .status(Status.INREALIZATION)
                .date(new Date(2018-1-5))
                .amount(300)
                .build();
        TransactionTO transactionTO2 = TransactionTO.builder()
                .status(Status.INREALIZATION)
                .date(new Date(2018-1-5))
                .amount(400)
                .build();
        TransactionTO transactionTO3 = TransactionTO.builder()
                .status(Status.INREALIZATION)
                .date(new Date(2018-1-5))
                .amount(500)
                .build();
        TransactionTO transactionTO4 = TransactionTO.builder()
                .status(Status.INREALIZATION)
                .date(new Date(2018-1-5))
                .amount(600)
                .build();

        TransactionTO transactionTOSaved1 = transactionService.saveTransaction(transactionTO1);
        TransactionTO transactionTOSaved2 = transactionService.saveTransaction(transactionTO2);
        TransactionTO transactionTOSaved3 = transactionService.saveTransaction(transactionTO3);
        TransactionTO transactionTOSaved4 = transactionService.saveTransaction(transactionTO4);

        //when
        transactionService.removeTransaction(transactionTOSaved2.getId());

        //then
        long size = transactionDao.count();

        Assertions.assertThat(size).isEqualTo(3);
    }

    @Test
    @Transactional
    public void shouldFindAllAndSizeThree(){
        //given
        TransactionTO transactionTO1 = TransactionTO.builder()
                .status(Status.INREALIZATION)
                .date(new Date(2018-1-5))
                .amount(300)
                .build();
        TransactionTO transactionTO2 = TransactionTO.builder()
                .status(Status.INREALIZATION)
                .date(new Date(2018-1-5))
                .amount(400)
                .build();
        TransactionTO transactionTO3 = TransactionTO.builder()
                .status(Status.INREALIZATION)
                .date(new Date(2018-1-5))
                .amount(500)
                .build();

        TransactionTO transactionTOSaved1 = transactionService.saveTransaction(transactionTO1);
        TransactionTO transactionTOSaved2 = transactionService.saveTransaction(transactionTO2);
        TransactionTO transactionTOSaved3 = transactionService.saveTransaction(transactionTO3);


        //when
        Collection<TransactionTO> transactionAll= transactionService.findAll();

        //then
        long size = transactionAll.size();

        Assertions.assertThat(size).isEqualTo(3);
    }

    @Test
    @Transactional
    public void shouldFindOne(){
        //given
        TransactionTO transactionTO1 = TransactionTO.builder()
                .status(Status.INREALIZATION)
                .date(new Date(2018-1-5))
                .amount(300)
                .build();
        TransactionTO transactionTO2 = TransactionTO.builder()
                .status(Status.INREALIZATION)
                .date(new Date(2018-1-5))
                .amount(400)
                .build();
        TransactionTO transactionTO3 = TransactionTO.builder()
                .status(Status.INREALIZATION)
                .date(new Date(2018-1-5))
                .amount(500)
                .build();

        TransactionTO transactionTOSaved1 = transactionService.saveTransaction(transactionTO1);
        TransactionTO transactionTOSaved2 = transactionService.saveTransaction(transactionTO2);
        TransactionTO transactionTOSaved3 = transactionService.saveTransaction(transactionTO3);


        //when
        TransactionTO transactionOne = transactionService.findOne(transactionTOSaved3.getId());

        //then
        int amount = transactionOne.getAmount();

        Assertions.assertThat(amount).isEqualTo(500);
    }

    @Test
    @Transactional
    public void shouldNotFindOne(){
        //given
        TransactionTO transactionTO1 = TransactionTO.builder()
                .status(Status.INREALIZATION)
                .date(new Date(2018-1-5))
                .amount(300)
                .build();
        TransactionTO transactionTO2 = TransactionTO.builder()
                .status(Status.INREALIZATION)
                .date(new Date(2018-1-5))
                .amount(400)
                .build();
        TransactionTO transactionTO3 = TransactionTO.builder()
                .status(Status.INREALIZATION)
                .date(new Date(2018-1-5))
                .amount(500)
                .build();

        TransactionTO transactionTOSaved1 = transactionService.saveTransaction(transactionTO1);
        TransactionTO transactionTOSaved2 = transactionService.saveTransaction(transactionTO2);
        TransactionTO transactionTOSaved3 = transactionService.saveTransaction(transactionTO3);


        //when
        TransactionTO transactionOne = transactionService.findOne(4L);

        //then

        Assertions.assertThat(transactionOne).isNull();
    }

    @Test
    @Transactional
    public void shouldNotSaveTransactions(){
        //given
        TransactionTO transactionTO1 = TransactionTO.builder()
                .status(Status.INREALIZATION)
                .date(new Date(2018-1-5))
                .amount(300)
                .build();
        TransactionTO transactionTO2 = TransactionTO.builder()
                .status(Status.INREALIZATION)
                .date(new Date(2018-1-5))
                .amount(400)
                .build();
        TransactionTO transactionTO3 = TransactionTO.builder()
                .status(Status.INREALIZATION)
                .date(new Date(2018-1-5))
                .amount(500)
                .build();

        TransactionTO transactionTOSaved1 = transactionService.saveTransaction(transactionTO1);
        TransactionTO transactionTOSaved2 = transactionService.saveTransaction(transactionTO2);
        TransactionTO transactionTOSaved3 = transactionService.saveTransaction(transactionTO3);


        //when
        TransactionTO transactionOne = transactionService.findOne(44L);

        //then

        Assertions.assertThat(transactionOne).isNull();
    }

    @Test
    @Transactional
    public void shouldCheckThreeTransactionAndReturnFalse(){
        //given
        CustomerTO customerTO = CustomerTO.builder()
                .name("Rafal")
                .build();

        CustomerTO customerSaved1 = customerService.saveCustomer(customerTO);

        TransactionTO transactionTO1 = TransactionTO.builder()
                .status(Status.INREALIZATION)
                .date(new Date(2018-1-5))
                .customer(customerSaved1)
                .build();

        TransactionTO transactionTOSaved1 = transactionService.saveTransaction(transactionTO1);

        //when
        boolean check = transactionServiceImpl.checkThreeTransactions(transactionService.findOne(transactionTOSaved1.getId()));
        //then
        Assertions.assertThat(check).isEqualTo(false);
    }

    @Test
    @Transactional
    public void shouldCheckThreeTransactionAndReturnTrue(){
        //given
        CustomerTO customerTO = CustomerTO.builder()
                .name("Rafal")
                .build();

        CustomerTO customerSaved1 = customerService.saveCustomer(customerTO);

        TransactionTO transactionTO1 = TransactionTO.builder()
                .status(Status.REALIZED)
                .date(new Date(2018-1-5))
                .customer(customerSaved1)
                .build();
        TransactionTO transactionTO2 = TransactionTO.builder()
                .status(Status.REALIZED)
                .date(new Date(2018-1-5))
                .customer(customerSaved1)
                .build();
        TransactionTO transactionTO3 = TransactionTO.builder()
                .status(Status.REALIZED)
                .date(new Date(2018-1-5))
                .customer(customerSaved1)
                .build();

        TransactionTO transactionTOSaved1 = transactionService.saveTransaction(transactionTO1);
        TransactionTO transactionTOSaved2 = transactionService.saveTransaction(transactionTO1);
        TransactionTO transactionTOSaved3 = transactionService.saveTransaction(transactionTO1);

        //when
        boolean check = transactionServiceImpl.checkThreeTransactions(transactionService.findOne(transactionTOSaved1.getId()));
        //then
        Assertions.assertThat(check).isEqualTo(true);
    }
}
