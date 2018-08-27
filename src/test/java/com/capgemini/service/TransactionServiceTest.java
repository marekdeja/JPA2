package com.capgemini.service;

import com.capgemini.StarterKitJpaStarterApplication;
import com.capgemini.dao.*;
import com.capgemini.enums.Status;
import com.capgemini.service.impl.TransactionServiceImpl;
import com.capgemini.types.CustomerTO;
import com.capgemini.types.PositionTO;
import com.capgemini.types.ProductTO;
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
import java.util.HashSet;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StarterKitJpaStarterApplication.class)
public class TransactionServiceTest {

    @Autowired
    TransactionService transactionService;
    @Autowired
    TransactionServiceImpl transactionServiceImpl;
    @Autowired
    TransactionDao transactionDao;
    @Autowired
    TransactionDaoSpringData transactionDaoSpringData;

    @Autowired
    CustomerService customerService;
    @Autowired
    CustomerDao customerDao;

    @Autowired
    ProductService productService;
    @Autowired
    ProductDao productDao;

    @Autowired
    PositionService positionService;
    @Autowired
    PositionDao positionDao;

    @Test
    @Transactional
    public void shouldSaveThreeTransactionsAndSizeIsThree() {
        //given
        TransactionTO transactionTO = TransactionTO.builder()
                .status(Status.INREALIZATION)
                .date(new Date(2018 - 1 - 5))
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
    public void shouldDeleteTransactionAndSizeIsThree() {
        //given

        TransactionTO transactionTO1 = TransactionTO.builder()
                .status(Status.INREALIZATION)
                .date(new Date(2018 - 1 - 5))
                .amount(300)
                .build();
        TransactionTO transactionTO2 = TransactionTO.builder()
                .status(Status.INREALIZATION)
                .date(new Date(2018 - 1 - 5))
                .amount(400)
                .build();
        TransactionTO transactionTO3 = TransactionTO.builder()
                .status(Status.INREALIZATION)
                .date(new Date(2018 - 1 - 5))
                .amount(500)
                .build();
        TransactionTO transactionTO4 = TransactionTO.builder()
                .status(Status.INREALIZATION)
                .date(new Date(2018 - 1 - 5))
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
    public void shouldFindAllAndSizeThree() {
        //given
        TransactionTO transactionTO1 = TransactionTO.builder()
                .status(Status.INREALIZATION)
                .date(new Date(2018 - 1 - 5))
                .amount(300)
                .build();
        TransactionTO transactionTO2 = TransactionTO.builder()
                .status(Status.INREALIZATION)
                .date(new Date(2018 - 1 - 5))
                .amount(400)
                .build();
        TransactionTO transactionTO3 = TransactionTO.builder()
                .status(Status.INREALIZATION)
                .date(new Date(2018 - 1 - 5))
                .amount(500)
                .build();

        TransactionTO transactionTOSaved1 = transactionService.saveTransaction(transactionTO1);
        TransactionTO transactionTOSaved2 = transactionService.saveTransaction(transactionTO2);
        TransactionTO transactionTOSaved3 = transactionService.saveTransaction(transactionTO3);


        //when
        Collection<TransactionTO> transactionAll = transactionService.findAll();

        //then
        long size = transactionAll.size();

        Assertions.assertThat(size).isEqualTo(3);
    }

    @Test
    @Transactional
    public void shouldFindOne() {
        //given
        TransactionTO transactionTO1 = TransactionTO.builder()
                .status(Status.INREALIZATION)
                .date(new Date(2018 - 1 - 5))
                .amount(300)
                .build();
        TransactionTO transactionTO2 = TransactionTO.builder()
                .status(Status.INREALIZATION)
                .date(new Date(2018 - 1 - 5))
                .amount(400)
                .build();
        TransactionTO transactionTO3 = TransactionTO.builder()
                .status(Status.INREALIZATION)
                .date(new Date(2018 - 1 - 5))
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
    public void shouldNotFindOne() {
        //given
        TransactionTO transactionTO1 = TransactionTO.builder()
                .status(Status.INREALIZATION)
                .date(new Date(2018 - 1 - 5))
                .amount(300)
                .build();
        TransactionTO transactionTO2 = TransactionTO.builder()
                .status(Status.INREALIZATION)
                .date(new Date(2018 - 1 - 5))
                .amount(400)
                .build();
        TransactionTO transactionTO3 = TransactionTO.builder()
                .status(Status.INREALIZATION)
                .date(new Date(2018 - 1 - 5))
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
    public void shouldNotSaveTransactions() {
        //given
        TransactionTO transactionTO1 = TransactionTO.builder()
                .status(Status.INREALIZATION)
                .date(new Date(2018 - 1 - 5))
                .amount(300)
                .build();
        TransactionTO transactionTO2 = TransactionTO.builder()
                .status(Status.INREALIZATION)
                .date(new Date(2018 - 1 - 5))
                .amount(400)
                .build();
        TransactionTO transactionTO3 = TransactionTO.builder()
                .status(Status.INREALIZATION)
                .date(new Date(2018 - 1 - 5))
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
    public void shouldCheckThreeTransactionAndReturnFalse() {
        //given
        CustomerTO customerTO = CustomerTO.builder()
                .name("Rafal")
                .build();

        CustomerTO customerSaved1 = customerService.saveCustomer(customerTO);

        TransactionTO transactionTO1 = TransactionTO.builder()
                .status(Status.INREALIZATION)
                .date(new Date(2018 - 1 - 5))
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
    public void shouldCheckThreeTransactionAndReturnTrue() {
        //given
        CustomerTO customerTO = CustomerTO.builder()
                .name("Rafal")
                .build();

        CustomerTO customerSaved1 = customerService.saveCustomer(customerTO);

        TransactionTO transactionTO1 = TransactionTO.builder()
                .status(Status.REALIZED)
                .date(new Date(2018 - 1 - 5))
                .customer(customerSaved1)
                .build();
        TransactionTO transactionTO2 = TransactionTO.builder()
                .status(Status.REALIZED)
                .date(new Date(2018 - 1 - 5))
                .customer(customerSaved1)
                .build();
        TransactionTO transactionTO3 = TransactionTO.builder()
                .status(Status.REALIZED)
                .date(new Date(2018 - 1 - 5))
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

    @Test
    @Transactional
    public void shouldOrderBeLessThan5000() {
        //given
        ProductTO productTO1 = ProductTO.builder()
                .name("Apple")
                .price(43.4f)
                .build();

        ProductTO productTOSaved1 = productService.saveProduct(productTO1);

        PositionTO positionTO1 = PositionTO.builder()
                .amount(2)
                .product(productTOSaved1)
                .build();

        PositionTO positionTOSaved1 = positionService.savePosition(positionTO1);

        TransactionTO transactionTO1 = TransactionTO.builder()
                .status(Status.REALIZED)
                .date(new Date(2018 - 1 - 5))
                .position(positionTOSaved1.getId())
                .build();

        TransactionTO transactionTOSaved1 = transactionService.saveTransaction(transactionTO1);

        //when
        boolean check = transactionServiceImpl.checkOrderLessThan5000(transactionTOSaved1);

        //then
        Assertions.assertThat(check).isEqualTo(true);

    }

    @Test(expected = RuntimeException.class)
    @Transactional
    public void shouldOrderBeMoreThan5000AndThrowsException() {

        //given
        ProductTO productTO1 = ProductTO.builder()
                .name("Computer")
                .price(4333.4f)
                .build();
        ProductTO productTO2 = ProductTO.builder()
                .name("Screen")
                .price(1000f)
                .build();

        ProductTO productTOSaved1 = productService.saveProduct(productTO1);
        ProductTO productTOSaved2 = productService.saveProduct(productTO2);

        PositionTO positionTO1 = PositionTO.builder()
                .amount(1)
                .product(productTOSaved1)
                .build();
        PositionTO positionTO2 = PositionTO.builder()
                .amount(1)
                .product(productTOSaved2)
                .build();

        PositionTO positionTOSaved1 = positionService.savePosition(positionTO1);
        PositionTO positionTOSaved2 = positionService.savePosition(positionTO2);

        Collection<Long> positionIDs = new HashSet<>();
        positionIDs.add(positionTOSaved1.getId());
        positionIDs.add(positionTOSaved2.getId());

        TransactionTO transactionTO1 = TransactionTO.builder()
                .status(Status.REALIZED)
                .date(new Date(2018 - 1 - 5))
                .positions(positionIDs)
                .build();

        TransactionTO transactionTOSaved1 = transactionService.saveTransaction(transactionTO1);

        //when
        boolean thrown = false;
        if (transactionServiceImpl.checkOrderLessThan5000(transactionTOSaved1) != true) {
            thrown = true;
        }

        //then
        Assertions.assertThat(thrown).isEqualTo(true);
    }

    @Test
    @Transactional
    public void shouldOrderBeLessThanFiveIdenticalProductAndReturnTrue() {

        //given
        ProductTO productTO1 = ProductTO.builder()
                .name("Computer")
                .price(4333.4f)
                .build();
        ProductTO productTO2 = ProductTO.builder()
                .name("Screen")
                .price(1000f)
                .build();

        ProductTO productTOSaved1 = productService.saveProduct(productTO1);
        ProductTO productTOSaved2 = productService.saveProduct(productTO2);

        PositionTO positionTO1 = PositionTO.builder()
                .amount(1)
                .product(productTOSaved1)
                .build();
        PositionTO positionTO2 = PositionTO.builder()
                .amount(1)
                .product(productTOSaved2)
                .build();

        PositionTO positionTOSaved1 = positionService.savePosition(positionTO1);
        PositionTO positionTOSaved2 = positionService.savePosition(positionTO2);

        Collection<Long> positionIDs = new HashSet<>();
        positionIDs.add(positionTOSaved1.getId());
        positionIDs.add(positionTOSaved2.getId());

        TransactionTO transactionTO1 = TransactionTO.builder()
                .status(Status.REALIZED)
                .date(new Date(2018 - 1 - 5))
                .positions(positionIDs)
                .build();

        TransactionTO transactionTOSaved1 = transactionService.saveTransaction(transactionTO1);

        //when
        boolean check =transactionServiceImpl.checkIdeanticalProductsPrice(transactionTOSaved1);


        //then
        Assertions.assertThat(check).isEqualTo(true);
    }

    @Test(expected = RuntimeException.class)
    @Transactional
    public void shouldCheckIdenticalProductsOverFiveAndSinglePriceIsOver7000AndThrowsException() {

        //given
        ProductTO productTO1 = ProductTO.builder()
                .name("Computer")
                .price(8000.4f)
                .build();
        ProductTO productTO2 = ProductTO.builder()
                .name("Screen")
                .price(1000f)
                .build();

        ProductTO productTOSaved1 = productService.saveProduct(productTO1);
        ProductTO productTOSaved2 = productService.saveProduct(productTO2);

        PositionTO positionTO1 = PositionTO.builder()
                .amount(1)
                .product(productTOSaved1)
                .build();
        PositionTO positionTO2 = PositionTO.builder()
                .amount(1)
                .product(productTOSaved2)
                .build();
        PositionTO positionTO3 = PositionTO.builder()
                .amount(1)
                .product(productTOSaved1)
                .build();
        PositionTO positionTO4 = PositionTO.builder()
                .amount(1)
                .product(productTOSaved1)
                .build();
        PositionTO positionTO5 = PositionTO.builder()
                .amount(1)
                .product(productTOSaved1)
                .build();
        PositionTO positionTO6 = PositionTO.builder()
                .amount(1)
                .product(productTOSaved1)
                .build();
        PositionTO positionTO7 = PositionTO.builder()
                .amount(1)
                .product(productTOSaved1)
                .build();


        PositionTO positionTOSaved1 = positionService.savePosition(positionTO1);
        PositionTO positionTOSaved2 = positionService.savePosition(positionTO2);
        PositionTO positionTOSaved3 = positionService.savePosition(positionTO3);
        PositionTO positionTOSaved4 = positionService.savePosition(positionTO4);
        PositionTO positionTOSaved5 = positionService.savePosition(positionTO5);
        PositionTO positionTOSaved6 = positionService.savePosition(positionTO6);
        PositionTO positionTOSaved7 = positionService.savePosition(positionTO7);

        Collection<Long> positionIDs = new HashSet<>();
        positionIDs.add(positionTOSaved1.getId());
        positionIDs.add(positionTOSaved2.getId());
        positionIDs.add(positionTOSaved3.getId());
        positionIDs.add(positionTOSaved4.getId());
        positionIDs.add(positionTOSaved5.getId());
        positionIDs.add(positionTOSaved6.getId());
        positionIDs.add(positionTOSaved7.getId());

        TransactionTO transactionTO1 = TransactionTO.builder()
                .status(Status.REALIZED)
                .date(new Date(2018 - 1 - 5))
                .positions(positionIDs)
                .build();

        TransactionTO transactionTOSaved1 = transactionService.saveTransaction(transactionTO1);

        //when
        boolean thrown = false;
        if (transactionServiceImpl.checkIdeanticalProductsPrice(transactionTOSaved1)) {
            thrown = true;
        }

        //then
        Assertions.assertThat(thrown).isEqualTo(true);
    }

    @Test(expected = RuntimeException.class)
    @Transactional
    public void shouldCheckIdenticalProductsOverFiveAndSinglePriceIsOver7000WhereOneProductWithAmount10AndThrowsException() {

        //given
        ProductTO productTO1 = ProductTO.builder()
                .name("Computer")
                .price(8000.4f)
                .build();
        ProductTO productTO2 = ProductTO.builder()
                .name("Screen")
                .price(1000f)
                .build();

        ProductTO productTOSaved1 = productService.saveProduct(productTO1);
        ProductTO productTOSaved2 = productService.saveProduct(productTO2);

        PositionTO positionTO1 = PositionTO.builder()
                .amount(10)
                .product(productTOSaved1)
                .build();
        PositionTO positionTO2 = PositionTO.builder()
                .amount(1)
                .product(productTOSaved2)
                .build();



        PositionTO positionTOSaved1 = positionService.savePosition(positionTO1);
        PositionTO positionTOSaved2 = positionService.savePosition(positionTO2);


        Collection<Long> positionIDs = new HashSet<>();
        positionIDs.add(positionTOSaved1.getId());
        positionIDs.add(positionTOSaved2.getId());


        TransactionTO transactionTO1 = TransactionTO.builder()
                .status(Status.REALIZED)
                .date(new Date(2018 - 1 - 5))
                .positions(positionIDs)
                .build();

        TransactionTO transactionTOSaved1 = transactionService.saveTransaction(transactionTO1);

        //when
        boolean thrown = false;
        if (transactionServiceImpl.checkIdeanticalProductsPrice(transactionTOSaved1)) {
            thrown = true;
        }

        //then
        Assertions.assertThat(thrown).isEqualTo(true);
    }

    @Test
    @Transactional
    public void shouldReturnTrueWhenWageLessThan25() {

        //given
        ProductTO productTO1 = ProductTO.builder()
                .name("Computer")
                .price(8000.4f)
                .wage(5)
                .build();
        ProductTO productTO2 = ProductTO.builder()
                .name("Screen")
                .price(1000f)
                .wage(3)
                .build();

        ProductTO productTOSaved1 = productService.saveProduct(productTO1);
        ProductTO productTOSaved2 = productService.saveProduct(productTO2);

        PositionTO positionTO1 = PositionTO.builder()
                .amount(2)
                .product(productTOSaved1)
                .build();
        PositionTO positionTO2 = PositionTO.builder()
                .amount(1)
                .product(productTOSaved2)
                .build();



        PositionTO positionTOSaved1 = positionService.savePosition(positionTO1);
        PositionTO positionTOSaved2 = positionService.savePosition(positionTO2);


        Collection<Long> positionIDs = new HashSet<>();
        positionIDs.add(positionTOSaved1.getId());
        positionIDs.add(positionTOSaved2.getId());


        TransactionTO transactionTO1 = TransactionTO.builder()
                .status(Status.REALIZED)
                .date(new Date(2018 - 1 - 5))
                .positions(positionIDs)
                .build();

        TransactionTO transactionTOSaved1 = transactionService.saveTransaction(transactionTO1);

        //when
        boolean check = transactionServiceImpl.checkWageNotOver(transactionTO1);

        //then
        Assertions.assertThat(check).isEqualTo(true);
    }

    @Test (expected = RuntimeException.class)
    @Transactional
    public void shouldThrowExceptionWhenWageMoreThan25() {

        //given
        ProductTO productTO1 = ProductTO.builder()
                .name("Stone")
                .price(80.4f)
                .wage(5)
                .build();
        ProductTO productTO2 = ProductTO.builder()
                .name("Brick")
                .price(1000f)
                .wage(3)
                .build();

        ProductTO productTOSaved1 = productService.saveProduct(productTO1);
        ProductTO productTOSaved2 = productService.saveProduct(productTO2);

        PositionTO positionTO1 = PositionTO.builder()
                .amount(10)
                .product(productTOSaved1)
                .build();
        PositionTO positionTO2 = PositionTO.builder()
                .amount(1)
                .product(productTOSaved2)
                .build();



        PositionTO positionTOSaved1 = positionService.savePosition(positionTO1);
        PositionTO positionTOSaved2 = positionService.savePosition(positionTO2);


        Collection<Long> positionIDs = new HashSet<>();
        positionIDs.add(positionTOSaved1.getId());
        positionIDs.add(positionTOSaved2.getId());


        TransactionTO transactionTO1 = TransactionTO.builder()
                .status(Status.REALIZED)
                .positions(positionIDs)
                .build();

        TransactionTO transactionTOSaved1 = transactionService.saveTransaction(transactionTO1);

        //when
        boolean check = transactionServiceImpl.checkWageNotOver(transactionTO1);

        //then
        Assertions.assertThat(check).isNotEqualTo(true);
    }

    @Test
    @Transactional
    public void shouldReturnTrueWhenAddingTransaction() {

        //given
        CustomerTO customerTO = CustomerTO.builder()
                .name("Rafal")
                .build();

        CustomerTO customerSaved1 = customerService.saveCustomer(customerTO);



        ProductTO productTO1 = ProductTO.builder()
                .name("Computer")
                .price(8000.4f)
                .wage(2)
                .build();
        ProductTO productTO2 = ProductTO.builder()
                .name("Screen")
                .price(1000f)
                .wage(3)
                .build();

        ProductTO productTOSaved1 = productService.saveProduct(productTO1);
        ProductTO productTOSaved2 = productService.saveProduct(productTO2);

        PositionTO positionTO1 = PositionTO.builder()
                .amount(10)
                .product(productTOSaved1)
                .build();
        PositionTO positionTO2 = PositionTO.builder()
                .amount(1)
                .product(productTOSaved2)
                .build();



        PositionTO positionTOSaved1 = positionService.savePosition(positionTO1);
        PositionTO positionTOSaved2 = positionService.savePosition(positionTO2);


        Collection<Long> positionIDs = new HashSet<>();
        positionIDs.add(positionTOSaved1.getId());
        positionIDs.add(positionTOSaved2.getId());


        TransactionTO transactionTO1 = TransactionTO.builder()
                .status(Status.REALIZED)
                .date(new Date(2018 - 1 - 5))
                .positions(positionIDs)
                .customer(customerSaved1)
                .build();

        TransactionTO transactionTOSaved1 = transactionService.saveTransaction(transactionTO1);

        //when
        TransactionTO transactionTOAdded = transactionServiceImpl.addTransaction(transactionTO1);

        //then
        Assertions.assertThat(transactionTOAdded.getStatus()).isEqualTo(Status.REALIZED);
    }

    @Test (expected = RuntimeException.class)
    @Transactional
    public void shouldThrowExceptionWhenAddingTransaction() {

        //given
        CustomerTO customerTO = CustomerTO.builder()
                .name("Rafal")
                .build();

        CustomerTO customerSaved1 = customerService.saveCustomer(customerTO);

        ProductTO productTO1 = ProductTO.builder()
                .name("Computer")
                .price(800.4f)
                .wage(10)
                .build();
        ProductTO productTO2 = ProductTO.builder()
                .name("Screen")
                .price(100f)
                .wage(3)
                .build();

        ProductTO productTOSaved1 = productService.saveProduct(productTO1);
        ProductTO productTOSaved2 = productService.saveProduct(productTO2);

        PositionTO positionTO1 = PositionTO.builder()
                .amount(10)
                .product(productTOSaved1)
                .build();
        PositionTO positionTO2 = PositionTO.builder()
                .amount(1)
                .product(productTOSaved2)
                .build();



        PositionTO positionTOSaved1 = positionService.savePosition(positionTO1);
        PositionTO positionTOSaved2 = positionService.savePosition(positionTO2);


        Collection<Long> positionIDs = new HashSet<>();
        positionIDs.add(positionTOSaved1.getId());
        positionIDs.add(positionTOSaved2.getId());


        TransactionTO transactionTO1 = TransactionTO.builder()
                .status(Status.REALIZED)
                .date(new Date(2018 - 1 - 5))
                .positions(positionIDs)
                .customer(customerSaved1)
                .build();

        TransactionTO transactionTOSaved1 = transactionService.saveTransaction(transactionTO1);

        //when
        TransactionTO transactionTOAdded = transactionServiceImpl.addTransaction(transactionTO1);

        //then
        Assertions.assertThat(transactionTOAdded.getStatus()).isEqualTo(Status.REALIZED);
    }
}
