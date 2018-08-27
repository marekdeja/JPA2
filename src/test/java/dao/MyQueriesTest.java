package dao;

import com.capgemini.StarterKitJpaStarterApplication;
import com.capgemini.dao.*;
import com.capgemini.domain.CustomerEntity;
import com.capgemini.domain.PositionEntity;
import com.capgemini.domain.ProductEntity;
import com.capgemini.domain.TransactionEntity;
import com.capgemini.enums.Status;
import com.capgemini.types.ProductTO;
import com.querydsl.core.Tuple;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StarterKitJpaStarterApplication.class, properties = "spring.profiles.active=mysql")
public class MyQueriesTest {

    @Autowired
    private TransactionDao transactionDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private PositionDao positionDao;
    @Autowired
    private MyQueries myQueries;

    @PersistenceContext
    EntityManager entityManager;

    @Test
    @Transactional
    public void shouldFindCustomersNamedTomek() {
        CustomerEntity customerEntity1 = new CustomerEntity("Dawid", "Dawidowski", new Date(2018 - 10 - 10), "daw@buziaczek.pl", 505896533, "Warszawa", null);
        CustomerEntity customerEntity2 = new CustomerEntity("Tomek", "Torok", new Date(2000 - 10 - 9), "daw@buziaczek.pl", 505896533, "Warszawa", null);
        CustomerEntity customerEntity3 = new CustomerEntity("Tomek", "Grzyb", new Date(2000 - 9 - 9), "daw@buziaczek.pl", 505896533, "Warszawa", null);
        CustomerEntity customerEntity4 = new CustomerEntity("Robert", "Podolski", new Date(2000 - 9 - 9), "daw@buziaczek.pl", 505896533, "Warszawa", null);

        CustomerEntity customerSave1 = customerDao.save(customerEntity1);
        CustomerEntity customerSave2 = customerDao.save(customerEntity2);
        CustomerEntity customerSave3 = customerDao.save(customerEntity3);
        CustomerEntity customerSave4 = customerDao.save(customerEntity4);

        List<CustomerEntity> customersFound = myQueries.getCustomers("Tomek");
        int size = customersFound.size();
        String nameFound = customersFound.get(1).getName();

        Assertions.assertThat(size).isEqualTo(2);
        Assertions.assertThat(nameFound).isEqualTo("Tomek");

    }

    @Test
    @Transactional
    public void shouldGetAllTransactionOfCustomerAndSizeIsTwo() {
        CustomerEntity customerEntity1 = new CustomerEntity("Dawid", "Dawidowski", new Date(2018 - 10 - 10), "daw@buziaczek.pl", 505896533, "Warszawa", null);
        CustomerEntity customerEntity2 = new CustomerEntity("Rafal", "Dawidowski", new Date(2018 - 10 - 10), "raw@buziaczek.pl", 505896533, "Warszawa", null);

        CustomerEntity customerSave1 = customerDao.save(customerEntity1);
        CustomerEntity customerSave2 = customerDao.save(customerEntity2);

        TransactionEntity transactionEntity1 = new TransactionEntity(Status.INREALIZATION, new Date(2018 - 10 - 10), 0, customerSave1, null);
        TransactionEntity transactionEntity2 = new TransactionEntity(Status.INREALIZATION, new Date(2018 - 10 - 10), 0, customerSave2, null);
        TransactionEntity transactionEntity3 = new TransactionEntity(Status.INREALIZATION, new Date(2018 - 10 - 10), 0, customerSave2, null);

        TransactionEntity transactionSave1 = transactionDao.save(transactionEntity1);
        TransactionEntity transactionSave2 = transactionDao.save(transactionEntity2);
        TransactionEntity transactionSave3 = transactionDao.save(transactionEntity3);

        int check = myQueries.getAllTransactionsFromCustomer(customerSave2.getId());

        Assertions.assertThat(check).isEqualTo(2);
    }

    //2.b
    @Test
    @Transactional
    public void shouldCountAllTransactionAmountOfCustomer() {
        CustomerEntity customerEntity1 = new CustomerEntity("Dawid", "Dawidowski", new Date(2018 - 10 - 10), "daw@buziaczek.pl", 505896533, "Warszawa", null);
        CustomerEntity customerEntity2 = new CustomerEntity("Rafal", "Dawidowski", new Date(2018 - 10 - 10), "raw@buziaczek.pl", 505896533, "Warszawa", null);
        CustomerEntity customerSave1 = customerDao.save(customerEntity1);
        CustomerEntity customerSave2 = customerDao.save(customerEntity2);

        ProductEntity productEntity1 = new ProductEntity("Apple", 5.0f, 0.1f, 12, null);
        ProductEntity productEntity2 = new ProductEntity("Orange", 6.0f, 0.1f, 12, null);
        ProductEntity productEntity3 = new ProductEntity("Peach", 3.0f, 0.1f, 12, null);
        ProductEntity productSave1 = productDao.save(productEntity1);
        ProductEntity productSave2 = productDao.save(productEntity2);
        ProductEntity productSave3 = productDao.save(productEntity3);

        PositionEntity positionEntity1 = new PositionEntity(1, null, productSave1);
        PositionEntity positionEntity2 = new PositionEntity(1, null, productSave1);
        PositionEntity positionEntity3 = new PositionEntity(1, null, productSave2);
        PositionEntity positionEntitySaved1 = positionDao.save(positionEntity1);
        PositionEntity positionEntitySaved2 = positionDao.save(positionEntity2);
        PositionEntity positionEntitySaved3 = positionDao.save(positionEntity3);

        Collection<PositionEntity> positions1 = new HashSet<>();
        positions1.add(positionEntitySaved1);
        positions1.add(positionEntitySaved2);
        positions1.add(positionEntitySaved3);

        TransactionEntity transactionEntity1 = new TransactionEntity(Status.INREALIZATION, new Date(2018 - 10 - 10), 0, customerSave1, positions1);

        TransactionEntity transactionSave1 = transactionDao.save(transactionEntity1);

        positions1.forEach(position -> position.setTransaction(transactionSave1));

        Float check = myQueries.getAllTransactionsPriceFromCustomer(customerSave1.getId());

        Assertions.assertThat(check).isEqualTo(16);
    }

    //2.a name
    @Test
    @Transactional
    public void searchTransactionByCustomerName() {
        //given
        CustomerEntity customerEntity1 = new CustomerEntity("Dawid", "Dawidowski", new Date(2018 - 10 - 10), "daw@buziaczek.pl", 505896533, "Warszawa", null);
        CustomerEntity customerEntity2 = new CustomerEntity("Rafal", "Dawidowski", new Date(2018 - 10 - 10), "raw@buziaczek.pl", 505896533, "Warszawa", null);
        CustomerEntity customerEntity3 = new CustomerEntity("Dawid", "Dawidowski", new Date(2018 - 10 - 10), "raw@buziaczek.pl", 505896533, "Warszawa", null);


        CustomerEntity customerSave1 = customerDao.save(customerEntity1);
        CustomerEntity customerSave2 = customerDao.save(customerEntity2);
        CustomerEntity customerSave3 = customerDao.save(customerEntity3);

        TransactionEntity transactionEntity1 = new TransactionEntity(Status.INREALIZATION, new Date(2018 - 10 - 10), 0, customerSave1, null);
        TransactionEntity transactionEntity2 = new TransactionEntity(Status.REALIZED, new Date(2018 - 10 - 10), 0, customerSave2, null);
        TransactionEntity transactionEntity3 = new TransactionEntity(Status.REALIZED, new Date(2018 - 10 - 10), 0, customerSave3, null);

        TransactionEntity transactionSave1 = transactionDao.save(transactionEntity1);
        TransactionEntity transactionSave2 = transactionDao.save(transactionEntity2);
        TransactionEntity transactionSave3 = transactionDao.save(transactionEntity3);

        List<TransactionEntity> transactions1 = new ArrayList<>();
        transactions1.add(transactionSave1);
        customerSave1.setTransactions(transactions1);

        List<TransactionEntity> transactions2 = new ArrayList<>();
        transactions2.add(transactionSave2);
        customerSave2.setTransactions(transactions2);

        List<TransactionEntity> transactions3 = new ArrayList<>();
        transactions3.add(transactionSave2);
        customerSave3.setTransactions(transactions3);

        //when
        SearchCriteria searchCriteria = SearchCriteria.builder()
                .customerName("Dawid")
                .build();

        List<TransactionEntity> transactionSearched = myQueries.findTransactionByCriteria(searchCriteria);
        //then
        Assertions.assertThat(transactionSearched.size()).isEqualTo(2);
        Assertions.assertThat(transactionSearched.contains(transactionSave1));
    }

    //2.a date
    @Test
    @Transactional
    public void searchTransactionByDate() throws ParseException {
        //given
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date1 = sdf.parse("21/12/2017");
        Date date2 = sdf.parse("21/12/2019");
        Date date3 = sdf.parse("01/01/2018");
        Date date4 = sdf.parse("01/04/2018");

        TransactionEntity transactionEntity1 = new TransactionEntity(Status.INREALIZATION, date3, 0, null, null);
        TransactionEntity transactionEntity2 = new TransactionEntity(Status.REALIZED, date4, 0, null, null);



        TransactionEntity transactionSave1 = transactionDao.save(transactionEntity1);
        TransactionEntity transactionSave2 = transactionDao.save(transactionEntity2);


        //when
        SearchCriteria searchCriteria = SearchCriteria.builder()
                .start(date1)
                .end(date2)
                .build();

        List<TransactionEntity> transactionSearched = myQueries.findTransactionByCriteria(searchCriteria);
        //then
        Assertions.assertThat(transactionSearched.size()).isEqualTo(2);
        Assertions.assertThat(transactionSearched.contains(transactionDao.getOne(transactionSave1.getId())));
    }

    //2.a product
    @Test
    @Transactional
    public void shouldFindTransactionByProduct() {
//given
        ProductEntity productEntity1 = new ProductEntity("Apple", 5.0f, 0.1f, 12, null);
        ProductEntity productEntity2 = new ProductEntity("Orange", 6.0f, 0.1f, 12, null);
        ProductEntity productEntity3 = new ProductEntity("Peach", 3.0f, 0.1f, 12, null);
        ProductEntity productSave1 = productDao.save(productEntity1);
        ProductEntity productSave2 = productDao.save(productEntity2);
        ProductEntity productSave3 = productDao.save(productEntity3);

        PositionEntity positionEntity1 = new PositionEntity(1, null, productSave1);
        PositionEntity positionEntity2 = new PositionEntity(1, null, productSave1);
        PositionEntity positionEntity3 = new PositionEntity(1, null, productSave2);
        PositionEntity positionEntitySaved1 = positionDao.save(positionEntity1);
        PositionEntity positionEntitySaved2 = positionDao.save(positionEntity2);
        PositionEntity positionEntitySaved3 = positionDao.save(positionEntity3);

        Collection<PositionEntity> positions1 = new HashSet<>();
        positions1.add(positionEntitySaved1);
        positions1.add(positionEntitySaved2);
        positions1.add(positionEntitySaved3);

        TransactionEntity transactionEntity1 = new TransactionEntity(Status.INREALIZATION, new Date(2018 - 10 - 10), 0, null, positions1);

        TransactionEntity transactionSave1 = transactionDao.save(transactionEntity1);

        positions1.forEach(position -> position.setTransaction(transactionSave1));
//when
        SearchCriteria searchCriteria = SearchCriteria.builder()
                .product(productSave1)
                .build();
        List check = myQueries.findTransactionByCriteria(searchCriteria);
//then

        Assertions.assertThat(check.size()).isEqualTo(1);
        Assertions.assertThat(check.get(0).equals(transactionSave1));
    }

    //2.a priceTransaction
    @Test
    @Transactional
    public void shouldFindTransactionByPriceTransaction() {
//given
        ProductEntity productEntity1 = new ProductEntity("Apple", 5.0f, 0.1f, 12, null);
        ProductEntity productEntity2 = new ProductEntity("Orange", 6.0f, 0.1f, 12, null);
        ProductEntity productEntity3 = new ProductEntity("Peach", 3.0f, 0.1f, 12, null);
        ProductEntity productSave1 = productDao.save(productEntity1);
        ProductEntity productSave2 = productDao.save(productEntity2);
        ProductEntity productSave3 = productDao.save(productEntity3);


        PositionEntity positionEntity1 = new PositionEntity(1, null, productSave1);
        PositionEntity positionEntity2 = new PositionEntity(1, null, productSave2);
        PositionEntity positionEntity3 = new PositionEntity(1, null, productSave3);
        PositionEntity positionEntitySaved1 = positionDao.save(positionEntity1);
        PositionEntity positionEntitySaved2 = positionDao.save(positionEntity2);
        PositionEntity positionEntitySaved3 = positionDao.save(positionEntity3);

        Collection<PositionEntity> positions1 = new HashSet<>();
        positions1.add(positionEntitySaved1);
        positions1.add(positionEntitySaved2);
        positions1.add(positionEntitySaved3);

        Collection<PositionEntity> positionsForProduct1 = new ArrayList<>();
        positionsForProduct1.add(positionEntitySaved1);

        Collection<PositionEntity> positionsForProduct2 = new ArrayList<>();
        positionsForProduct2.add(positionEntitySaved2);

        Collection<PositionEntity> positionsForProduct3 = new ArrayList<>();
        positionsForProduct3.add(positionEntitySaved3);

        productSave1.setPositions(positionsForProduct1);
        productSave2.setPositions(positionsForProduct2);
        productSave3.setPositions(positionsForProduct3);


        TransactionEntity transactionEntity1 = new TransactionEntity(Status.INREALIZATION, new Date(), 0, null, positions1);

        TransactionEntity transactionSave1 = transactionDao.save(transactionEntity1);

        positions1.forEach(position -> position.setTransaction(transactionSave1));
//when

        SearchCriteria searchCriteria = SearchCriteria.builder()
                .priceTransaction(14.0f)
                .build();
        List check = myQueries.findTransactionByCriteria(searchCriteria);

//then

        Assertions.assertThat(check.size()).isEqualTo(1);
        Assertions.assertThat(check.get(0).equals(transactionSave1));
    }

    //2.a three parameters not null , //NOT WORKING WITH 4 PARAS (without transaction or product) ??
    @Test
    @Transactional
    public void shouldFindTransactionByAllParameters() throws ParseException {
//given
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date1 = sdf.parse("21/12/2017");
        Date date2 = sdf.parse("21/12/2019");
        Date date3 = sdf.parse("01/01/2018");
        Date date4 = sdf.parse("01/04/2018");

        ProductEntity productEntity1 = new ProductEntity("Apple", 5.0f, 0.1f, 12, null);
        ProductEntity productEntity2 = new ProductEntity("Orange", 6.0f, 0.1f, 12, null);
        ProductEntity productEntity3 = new ProductEntity("Peach", 3.0f, 0.1f, 12, null);
        ProductEntity productSave1 = productDao.save(productEntity1);
        ProductEntity productSave2 = productDao.save(productEntity2);
        ProductEntity productSave3 = productDao.save(productEntity3);


        PositionEntity positionEntity1 = new PositionEntity(1, null, productSave1);
        PositionEntity positionEntity2 = new PositionEntity(1, null, productSave2);
        PositionEntity positionEntity3 = new PositionEntity(1, null, productSave3);
        PositionEntity positionEntitySaved1 = positionDao.save(positionEntity1);
        PositionEntity positionEntitySaved2 = positionDao.save(positionEntity2);
        PositionEntity positionEntitySaved3 = positionDao.save(positionEntity3);

        Collection<PositionEntity> positions1 = new HashSet<>();
        positions1.add(positionEntitySaved1);
        positions1.add(positionEntitySaved2);
        positions1.add(positionEntitySaved3);

        Collection<PositionEntity> positionsForProduct1 = new ArrayList<>();
        positionsForProduct1.add(positionEntitySaved1);

        Collection<PositionEntity> positionsForProduct2 = new ArrayList<>();
        positionsForProduct2.add(positionEntitySaved2);

        Collection<PositionEntity> positionsForProduct3 = new ArrayList<>();
        positionsForProduct3.add(positionEntitySaved3);

        productSave1.setPositions(positionsForProduct1);
        productSave2.setPositions(positionsForProduct2);
        productSave3.setPositions(positionsForProduct3);

        CustomerEntity customerEntity1 = new CustomerEntity("Dawid", "Dawidowski", new Date(2018 - 10 - 10), "daw@buziaczek.pl", 505896533, "Warszawa", null);
CustomerEntity customerSaved1 = customerDao.save(customerEntity1);


        TransactionEntity transactionEntity1 = new TransactionEntity(Status.INREALIZATION, date3, 0, customerSaved1, positions1);

        TransactionEntity transactionSave1 = transactionDao.save(transactionEntity1);

        positions1.forEach(position -> position.setTransaction(transactionSave1));
//when

        SearchCriteria searchCriteria = SearchCriteria.builder()
               .customerName("Dawid")
                .start(date1)
                .end(date2)
                .product(productSave1)
                //NOT WORKING WITH 4 PARAS
                //.priceTransaction(14.0f)
                .build();
        List check = myQueries.findTransactionByCriteria(searchCriteria);

//then

        Assertions.assertThat(check.size()).isEqualTo(1);
        Assertions.assertThat(check.get(0).equals(transactionSave1));
    }

    //2.a all is null
    @Test
    @Transactional
    public void shouldSearchAndReturnNull() {
//given
        ProductEntity productEntity1 = new ProductEntity("Apple", 5.0f, 0.1f, 12, null);
        ProductEntity productEntity2 = new ProductEntity("Orange", 6.0f, 0.1f, 12, null);
        ProductEntity productEntity3 = new ProductEntity("Peach", 3.0f, 0.1f, 12, null);
        ProductEntity productSave1 = productDao.save(productEntity1);
        ProductEntity productSave2 = productDao.save(productEntity2);
        ProductEntity productSave3 = productDao.save(productEntity3);

        PositionEntity positionEntity1 = new PositionEntity(1, null, productSave1);
        PositionEntity positionEntity2 = new PositionEntity(1, null, productSave1);
        PositionEntity positionEntity3 = new PositionEntity(1, null, productSave2);
        PositionEntity positionEntitySaved1 = positionDao.save(positionEntity1);
        PositionEntity positionEntitySaved2 = positionDao.save(positionEntity2);
        PositionEntity positionEntitySaved3 = positionDao.save(positionEntity3);

        Collection<PositionEntity> positions1 = new HashSet<>();
        positions1.add(positionEntitySaved1);
        positions1.add(positionEntitySaved2);
        positions1.add(positionEntitySaved3);

        TransactionEntity transactionEntity1 = new TransactionEntity(Status.INREALIZATION, new Date(2018 - 10 - 10), 0, null, positions1);

        TransactionEntity transactionSave1 = transactionDao.save(transactionEntity1);

        positions1.forEach(position -> position.setTransaction(transactionSave1));
//when
        SearchCriteria searchCriteria = SearchCriteria.builder()
                .build();
        List check = myQueries.findTransactionByCriteria(searchCriteria);
//then

        Assertions.assertThat(check).isEqualTo(null);
    }

    //2.c.1
    @Test
    @Transactional
    public void findSumTransactionCustomerStatus() {
        CustomerEntity customerEntity1 = new CustomerEntity("Dawid", "Dawidowski", new Date(2018 - 10 - 10), "daw@buziaczek.pl", 505896533, "Warszawa", null);
        CustomerEntity customerEntity2 = new CustomerEntity("Rafal", "Dawidowski", new Date(2018 - 10 - 10), "raw@buziaczek.pl", 505896533, "Warszawa", null);
        CustomerEntity customerSave1 = customerDao.save(customerEntity1);
        CustomerEntity customerSave2 = customerDao.save(customerEntity2);

        ProductEntity productEntity1 = new ProductEntity("Apple", 5.0f, 0.1f, 12, null);
        ProductEntity productEntity2 = new ProductEntity("Orange", 6.0f, 0.1f, 12, null);
        ProductEntity productEntity3 = new ProductEntity("Peach", 3.0f, 0.1f, 12, null);
        ProductEntity productSave1 = productDao.save(productEntity1);
        ProductEntity productSave2 = productDao.save(productEntity2);
        ProductEntity productSave3 = productDao.save(productEntity3);

        PositionEntity positionEntity1 = new PositionEntity(1, null, productSave1);
        PositionEntity positionEntity2 = new PositionEntity(1, null, productSave1);
        PositionEntity positionEntity3 = new PositionEntity(1, null, productSave2);
        PositionEntity positionEntitySaved1 = positionDao.save(positionEntity1);
        PositionEntity positionEntitySaved2 = positionDao.save(positionEntity2);
        PositionEntity positionEntitySaved3 = positionDao.save(positionEntity3);

        Collection<PositionEntity> positions1 = new HashSet<>();
        positions1.add(positionEntitySaved1);
        positions1.add(positionEntitySaved2);
        positions1.add(positionEntitySaved3);

        TransactionEntity transactionEntity1 = new TransactionEntity(Status.INREALIZATION, new Date(), 0, customerSave1, positions1);

        TransactionEntity transactionSave1 = transactionDao.save(transactionEntity1);

        positions1.forEach(position -> position.setTransaction(transactionSave1));

        Float check = myQueries.getSumTransactionStatusCustomer(customerSave1.getId(), Status.INREALIZATION);

        Assertions.assertThat(check).isEqualTo(16);
    }

    //2.c.2
    @Test
    @Transactional
    public void findSumTransactionAllCustomerStatus() {
        CustomerEntity customerEntity1 = new CustomerEntity("Dawid", "Dawidowski", new Date(2018 - 10 - 10), "daw@buziaczek.pl", 505896533, "Warszawa", null);
        CustomerEntity customerEntity2 = new CustomerEntity("Rafal", "Dawidowski", new Date(2018 - 10 - 10), "raw@buziaczek.pl", 505896533, "Warszawa", null);
        CustomerEntity customerSave1 = customerDao.save(customerEntity1);
        CustomerEntity customerSave2 = customerDao.save(customerEntity2);

        ProductEntity productEntity1 = new ProductEntity("Apple", 5.0f, 0.1f, 12, null);
        ProductEntity productEntity2 = new ProductEntity("Orange", 6.0f, 0.1f, 12, null);
        ProductEntity productEntity3 = new ProductEntity("Peach", 3.0f, 0.1f, 12, null);
        ProductEntity productSave1 = productDao.save(productEntity1);
        ProductEntity productSave2 = productDao.save(productEntity2);
        ProductEntity productSave3 = productDao.save(productEntity3);

        PositionEntity positionEntity1 = new PositionEntity(1, null, productSave1);
        PositionEntity positionEntity2 = new PositionEntity(1, null, productSave1);
        PositionEntity positionEntity3 = new PositionEntity(1, null, productSave2);
        PositionEntity positionEntitySaved1 = positionDao.save(positionEntity1);
        PositionEntity positionEntitySaved2 = positionDao.save(positionEntity2);
        PositionEntity positionEntitySaved3 = positionDao.save(positionEntity3);

        PositionEntity positionEntity4 = new PositionEntity(1, null, productSave1);
        PositionEntity positionEntity5 = new PositionEntity(1, null, productSave1);
        PositionEntity positionEntity6 = new PositionEntity(1, null, productSave2);
        PositionEntity positionEntitySaved4 = positionDao.save(positionEntity4);
        PositionEntity positionEntitySaved5 = positionDao.save(positionEntity5);
        PositionEntity positionEntitySaved6 = positionDao.save(positionEntity6);

        Collection<PositionEntity> positions1 = new HashSet<>();
        positions1.add(positionEntitySaved1);
        positions1.add(positionEntitySaved2);
        positions1.add(positionEntitySaved3);

        Collection<PositionEntity> positions2 = new HashSet<>();
        positions2.add(positionEntitySaved4);
        positions2.add(positionEntitySaved5);
        positions2.add(positionEntitySaved6);

        TransactionEntity transactionEntity1 = new TransactionEntity(Status.INREALIZATION, new Date(), 0, customerSave1, positions1);
        TransactionEntity transactionEntity2 = new TransactionEntity(Status.INREALIZATION, new Date(), 0, customerSave2, positions2);

        TransactionEntity transactionSave1 = transactionDao.save(transactionEntity1);
        TransactionEntity transactionSave2 = transactionDao.save(transactionEntity2);

        positions1.forEach(position -> position.setTransaction(transactionSave1));
        positions2.forEach(position -> position.setTransaction(transactionSave2));

        Float check = myQueries.getSumTransactionStatusAllCustomers(Status.INREALIZATION);

        Assertions.assertThat(check).isEqualTo(32);
    }

    //2.d
    @Test
    @Transactional
    public void findMostPopularProducts() {
        //given
        ProductEntity productEntity1 = new ProductEntity("Apple", 5.0f, 0.1f, 12, null);
        ProductEntity productEntity2 = new ProductEntity("Orange", 6.0f, 0.1f, 12, null);
        ProductEntity productEntity3 = new ProductEntity("Peach", 3.0f, 0.1f, 12, null);
        ProductEntity productSave1 = productDao.save(productEntity1);
        ProductEntity productSave2 = productDao.save(productEntity2);
        ProductEntity productSave3 = productDao.save(productEntity3);

        PositionEntity positionEntity1 = new PositionEntity(1, null, productSave1);
        PositionEntity positionEntity2 = new PositionEntity(3, null, productSave2);
        PositionEntity positionEntity3 = new PositionEntity(5, null, productSave3);
        PositionEntity positionEntitySaved1 = positionDao.save(positionEntity1);
        PositionEntity positionEntitySaved2 = positionDao.save(positionEntity2);
        PositionEntity positionEntitySaved3 = positionDao.save(positionEntity3);

        Collection<PositionEntity> positions1 = new HashSet<>();
        positions1.add(positionEntitySaved1);
        positions1.add(positionEntitySaved2);
        positions1.add(positionEntitySaved3);

        TransactionEntity transactionEntity1 = new TransactionEntity(Status.INREALIZATION, new Date(), 0, null, positions1);

        TransactionEntity transactionSave1 = transactionDao.save(transactionEntity1);

        positions1.forEach(position -> position.setTransaction(transactionSave1));

        //when
        List<PositionEntity> check = myQueries.getTenMostPopularProducts();

        //then
        Assertions.assertThat(check.size()).isEqualTo(3);
        Assertions.assertThat(check.get(0).getProduct().getName()).isEqualTo("Peach");
    }

    //2.e
    @Test
    @Transactional
    public void shouldFind3Customers() throws ParseException {
        //given
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date1 = sdf.parse("21/12/2017");
        Date date2 = sdf.parse("21/12/2019");
        Date date3 = sdf.parse("01/01/2018");
        Date date4 = sdf.parse("01/04/2018");

        CustomerEntity customerEntity1 = new CustomerEntity("Dawid", "Dawidowski", new Date(2018 - 10 - 10), "daw@buziaczek.pl", 505896533, "Warszawa", null);
        CustomerEntity customerEntity2 = new CustomerEntity("Rafal", "Dawidowski", new Date(2018 - 10 - 10), "raw@buziaczek.pl", 505896533, "Warszawa", null);
        CustomerEntity customerEntity3 = new CustomerEntity("Marek", "Dawidowski", new Date(2018 - 10 - 10), "daw@buziaczek.pl", 505896533, "Warszawa", null);
        CustomerEntity customerEntity4 = new CustomerEntity("Tomek", "Dawidowski", new Date(2018 - 10 - 10), "raw@buziaczek.pl", 505896533, "Warszawa", null);
        CustomerEntity customerSave1 = customerDao.save(customerEntity1);
        CustomerEntity customerSave2 = customerDao.save(customerEntity2);
        CustomerEntity customerSave3 = customerDao.save(customerEntity3);
        CustomerEntity customerSave4 = customerDao.save(customerEntity4);

        ProductEntity productEntity1 = new ProductEntity("Apple", 5.0f, 0.1f, 12, null);
        ProductEntity productEntity2 = new ProductEntity("Orange", 6.0f, 0.1f, 12, null);
        ProductEntity productEntity3 = new ProductEntity("Peach", 3.0f, 0.1f, 12, null);
        ProductEntity productSave1 = productDao.save(productEntity1);
        ProductEntity productSave2 = productDao.save(productEntity2);
        ProductEntity productSave3 = productDao.save(productEntity3);

        PositionEntity positionEntity1 = new PositionEntity(1, null, productSave1);
        PositionEntity positionEntity2 = new PositionEntity(1, null, productSave2);
        PositionEntity positionEntity3 = new PositionEntity(1, null, productSave3);
        PositionEntity positionEntitySaved1 = positionDao.save(positionEntity1);
        PositionEntity positionEntitySaved2 = positionDao.save(positionEntity2);
        PositionEntity positionEntitySaved3 = positionDao.save(positionEntity3);

        PositionEntity positionEntity4 = new PositionEntity(1, null, productSave1);
        PositionEntity positionEntity5 = new PositionEntity(1, null, productSave1);
        PositionEntity positionEntity6 = new PositionEntity(1, null, productSave2);
        PositionEntity positionEntitySaved4 = positionDao.save(positionEntity4);
        PositionEntity positionEntitySaved5 = positionDao.save(positionEntity5);
        PositionEntity positionEntitySaved6 = positionDao.save(positionEntity6);

        PositionEntity positionEntity7 = new PositionEntity(1, null, productSave1);
        PositionEntity positionEntity8 = new PositionEntity(1, null, productSave1);
        PositionEntity positionEntity9 = new PositionEntity(1, null, productSave2);
        PositionEntity positionEntitySaved7 = positionDao.save(positionEntity7);
        PositionEntity positionEntitySaved8 = positionDao.save(positionEntity8);
        PositionEntity positionEntitySaved9 = positionDao.save(positionEntity9);

        PositionEntity positionEntity10 = new PositionEntity(1, null, productSave1);
        PositionEntity positionEntity11 = new PositionEntity(1, null, productSave1);
        PositionEntity positionEntity12 = new PositionEntity(1, null, productSave2);
        PositionEntity positionEntitySaved10 = positionDao.save(positionEntity10);
        PositionEntity positionEntitySaved11 = positionDao.save(positionEntity11);
        PositionEntity positionEntitySaved12 = positionDao.save(positionEntity12);

        Collection<PositionEntity> positions1 = new HashSet<>();
        positions1.add(positionEntitySaved1);
        positions1.add(positionEntitySaved2);
        positions1.add(positionEntitySaved3);

        Collection<PositionEntity> positions2 = new HashSet<>();
        positions2.add(positionEntitySaved4);
        positions2.add(positionEntitySaved5);
        positions2.add(positionEntitySaved6);

        Collection<PositionEntity> positions3 = new HashSet<>();
        positions3.add(positionEntitySaved7);
        positions3.add(positionEntitySaved8);
        positions3.add(positionEntitySaved9);

        Collection<PositionEntity> positions4 = new HashSet<>();
        positions4.add(positionEntitySaved10);
        positions4.add(positionEntitySaved11);
        positions4.add(positionEntitySaved12);

        TransactionEntity transactionEntity1 = new TransactionEntity(Status.INREALIZATION, date3, 0, customerSave1, positions1);
        TransactionEntity transactionEntity2 = new TransactionEntity(Status.INREALIZATION, date4, 0, customerSave2, positions2);
        TransactionEntity transactionEntity3 = new TransactionEntity(Status.INREALIZATION, date3, 0, customerSave3, positions3);
        TransactionEntity transactionEntity4 = new TransactionEntity(Status.INREALIZATION, date4, 0, customerSave4, positions4);
        TransactionEntity transactionSave1 = transactionDao.save(transactionEntity1);
        TransactionEntity transactionSave2 = transactionDao.save(transactionEntity2);
        TransactionEntity transactionSave3 = transactionDao.save(transactionEntity3);
        TransactionEntity transactionSave4 = transactionDao.save(transactionEntity4);

        positions1.forEach(position -> position.setTransaction(transactionSave1));
        positions2.forEach(position -> position.setTransaction(transactionSave2));
        positions3.forEach(position -> position.setTransaction(transactionSave3));
        positions4.forEach(position -> position.setTransaction(transactionSave4));

        //when

        List<Tuple> check = myQueries.getCustomersWithMostPaymentInPeriodTime(date1, date2);

        //then
        Assertions.assertThat(check.size()).isEqualTo(3);

    }

    //2.e
    @Test
    @Transactional
    public void shouldFindProductsInDelivery() throws ParseException {
        //given
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date1 = sdf.parse("21/12/2017");
        Date date2 = sdf.parse("21/12/2019");
        Date date3 = sdf.parse("01/01/2018");
        Date date4 = sdf.parse("01/04/2018");

        CustomerEntity customerEntity1 = new CustomerEntity("Dawid", "Dawidowski", new Date(2018 - 10 - 10), "daw@buziaczek.pl", 505896533, "Warszawa", null);
        CustomerEntity customerEntity2 = new CustomerEntity("Rafal", "Dawidowski", new Date(2018 - 10 - 10), "raw@buziaczek.pl", 505896533, "Warszawa", null);
        CustomerEntity customerEntity3 = new CustomerEntity("Marek", "Dawidowski", new Date(2018 - 10 - 10), "daw@buziaczek.pl", 505896533, "Warszawa", null);
        CustomerEntity customerEntity4 = new CustomerEntity("Tomek", "Dawidowski", new Date(2018 - 10 - 10), "raw@buziaczek.pl", 505896533, "Warszawa", null);
        CustomerEntity customerSave1 = customerDao.save(customerEntity1);
        CustomerEntity customerSave2 = customerDao.save(customerEntity2);
        CustomerEntity customerSave3 = customerDao.save(customerEntity3);
        CustomerEntity customerSave4 = customerDao.save(customerEntity4);

        ProductEntity productEntity1 = new ProductEntity("Apple", 5.0f, 0.1f, 12, null);
        ProductEntity productEntity2 = new ProductEntity("Orange", 6.0f, 0.1f, 12, null);
        ProductEntity productEntity3 = new ProductEntity("Peach", 3.0f, 0.1f, 12, null);
        ProductEntity productSave1 = productDao.save(productEntity1);
        ProductEntity productSave2 = productDao.save(productEntity2);
        ProductEntity productSave3 = productDao.save(productEntity3);

        PositionEntity positionEntity1 = new PositionEntity(1, null, productSave1);
        PositionEntity positionEntity2 = new PositionEntity(1, null, productSave2);
        PositionEntity positionEntity3 = new PositionEntity(1, null, productSave3);
        PositionEntity positionEntitySaved1 = positionDao.save(positionEntity1);
        PositionEntity positionEntitySaved2 = positionDao.save(positionEntity2);
        PositionEntity positionEntitySaved3 = positionDao.save(positionEntity3);

        PositionEntity positionEntity4 = new PositionEntity(1, null, productSave1);
        PositionEntity positionEntity5 = new PositionEntity(1, null, productSave1);
        PositionEntity positionEntity6 = new PositionEntity(1, null, productSave2);
        PositionEntity positionEntitySaved4 = positionDao.save(positionEntity4);
        PositionEntity positionEntitySaved5 = positionDao.save(positionEntity5);
        PositionEntity positionEntitySaved6 = positionDao.save(positionEntity6);

        PositionEntity positionEntity7 = new PositionEntity(1, null, productSave1);
        PositionEntity positionEntity8 = new PositionEntity(1, null, productSave1);
        PositionEntity positionEntity9 = new PositionEntity(1, null, productSave2);
        PositionEntity positionEntitySaved7 = positionDao.save(positionEntity7);
        PositionEntity positionEntitySaved8 = positionDao.save(positionEntity8);
        PositionEntity positionEntitySaved9 = positionDao.save(positionEntity9);

        PositionEntity positionEntity10 = new PositionEntity(1, null, productSave1);
        PositionEntity positionEntity11 = new PositionEntity(1, null, productSave1);
        PositionEntity positionEntity12 = new PositionEntity(1, null, productSave2);
        PositionEntity positionEntitySaved10 = positionDao.save(positionEntity10);
        PositionEntity positionEntitySaved11 = positionDao.save(positionEntity11);
        PositionEntity positionEntitySaved12 = positionDao.save(positionEntity12);

        Collection<PositionEntity> positions1 = new HashSet<>();
        positions1.add(positionEntitySaved1);
        positions1.add(positionEntitySaved2);
        positions1.add(positionEntitySaved3);

        Collection<PositionEntity> positions2 = new HashSet<>();
        positions2.add(positionEntitySaved4);
        positions2.add(positionEntitySaved5);
        positions2.add(positionEntitySaved6);

        Collection<PositionEntity> positions3 = new HashSet<>();
        positions3.add(positionEntitySaved7);
        positions3.add(positionEntitySaved8);
        positions3.add(positionEntitySaved9);

        Collection<PositionEntity> positions4 = new HashSet<>();
        positions4.add(positionEntitySaved10);
        positions4.add(positionEntitySaved11);
        positions4.add(positionEntitySaved12);

        TransactionEntity transactionEntity1 = new TransactionEntity(Status.INDELIVERY, date3, 0, customerSave1, positions1);
        TransactionEntity transactionEntity2 = new TransactionEntity(Status.INREALIZATION, date4, 0, customerSave2, positions2);
        TransactionEntity transactionEntity3 = new TransactionEntity(Status.INREALIZATION, date3, 0, customerSave3, positions3);
        TransactionEntity transactionEntity4 = new TransactionEntity(Status.INREALIZATION, date4, 0, customerSave4, positions4);
        TransactionEntity transactionSave1 = transactionDao.save(transactionEntity1);
        TransactionEntity transactionSave2 = transactionDao.save(transactionEntity2);
        TransactionEntity transactionSave3 = transactionDao.save(transactionEntity3);
        TransactionEntity transactionSave4 = transactionDao.save(transactionEntity4);

        positions1.forEach(position -> position.setTransaction(transactionSave1));
        positions2.forEach(position -> position.setTransaction(transactionSave2));
        positions3.forEach(position -> position.setTransaction(transactionSave3));
        positions4.forEach(position -> position.setTransaction(transactionSave4));

        //when

        List<Tuple> check = myQueries.getProductsToBeSent();

        //then
        Assertions.assertThat(check.size()).isEqualTo(3);
    }

    //2.g
    @Test
    @Transactional
    //@Rollback(false)
    public void shouldFindTransactionsProfit() throws ParseException {
        //given
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date1 = sdf.parse("21/12/2017");
        Date date2 = sdf.parse("21/12/2019");
        Date date3 = sdf.parse("01/01/2018");
        Date date4 = sdf.parse("01/04/2018");

        CustomerEntity customerEntity1 = new CustomerEntity("Dawid", "Dawidowski", new Date(2018 - 10 - 10), "daw@buziaczek.pl", 505896533, "Warszawa", null);
        CustomerEntity customerEntity2 = new CustomerEntity("Rafal", "Dawidowski", new Date(2018 - 10 - 10), "raw@buziaczek.pl", 505896533, "Warszawa", null);
        CustomerEntity customerEntity3 = new CustomerEntity("Marek", "Dawidowski", new Date(2018 - 10 - 10), "daw@buziaczek.pl", 505896533, "Warszawa", null);
        CustomerEntity customerEntity4 = new CustomerEntity("Tomek", "Dawidowski", new Date(2018 - 10 - 10), "raw@buziaczek.pl", 505896533, "Warszawa", null);
        CustomerEntity customerSave1 = customerDao.save(customerEntity1);
        CustomerEntity customerSave2 = customerDao.save(customerEntity2);
        CustomerEntity customerSave3 = customerDao.save(customerEntity3);
        CustomerEntity customerSave4 = customerDao.save(customerEntity4);

        ProductEntity productEntity1 = new ProductEntity("Apple", 5.0f, 0.1f, 12, null);
        ProductEntity productEntity2 = new ProductEntity("Orange", 6.0f, 0.1f, 12, null);
        ProductEntity productEntity3 = new ProductEntity("Peach", 3.0f, 0.1f, 12, null);
        ProductEntity productSave1 = productDao.save(productEntity1);
        ProductEntity productSave2 = productDao.save(productEntity2);
        ProductEntity productSave3 = productDao.save(productEntity3);

        PositionEntity positionEntity1 = new PositionEntity(1, null, productSave1);
        PositionEntity positionEntity2 = new PositionEntity(1, null, productSave2);
        PositionEntity positionEntity3 = new PositionEntity(1, null, productSave3);
        PositionEntity positionEntitySaved1 = positionDao.save(positionEntity1);
        PositionEntity positionEntitySaved2 = positionDao.save(positionEntity2);
        PositionEntity positionEntitySaved3 = positionDao.save(positionEntity3);

        PositionEntity positionEntity4 = new PositionEntity(1, null, productSave1);
        PositionEntity positionEntity5 = new PositionEntity(1, null, productSave1);
        PositionEntity positionEntity6 = new PositionEntity(1, null, productSave2);
        PositionEntity positionEntitySaved4 = positionDao.save(positionEntity4);
        PositionEntity positionEntitySaved5 = positionDao.save(positionEntity5);
        PositionEntity positionEntitySaved6 = positionDao.save(positionEntity6);

        PositionEntity positionEntity7 = new PositionEntity(1, null, productSave1);
        PositionEntity positionEntity8 = new PositionEntity(1, null, productSave1);
        PositionEntity positionEntity9 = new PositionEntity(1, null, productSave2);
        PositionEntity positionEntitySaved7 = positionDao.save(positionEntity7);
        PositionEntity positionEntitySaved8 = positionDao.save(positionEntity8);
        PositionEntity positionEntitySaved9 = positionDao.save(positionEntity9);

        PositionEntity positionEntity10 = new PositionEntity(1, null, productSave1);
        PositionEntity positionEntity11 = new PositionEntity(1, null, productSave1);
        PositionEntity positionEntity12 = new PositionEntity(1, null, productSave2);
        PositionEntity positionEntitySaved10 = positionDao.save(positionEntity10);
        PositionEntity positionEntitySaved11 = positionDao.save(positionEntity11);
        PositionEntity positionEntitySaved12 = positionDao.save(positionEntity12);

        Collection<PositionEntity> positions1 = new HashSet<>();
        positions1.add(positionEntitySaved1);
        positions1.add(positionEntitySaved2);
        positions1.add(positionEntitySaved3);

        Collection<PositionEntity> positions2 = new HashSet<>();
        positions2.add(positionEntitySaved4);
        positions2.add(positionEntitySaved5);
        positions2.add(positionEntitySaved6);

        Collection<PositionEntity> positions3 = new HashSet<>();
        positions3.add(positionEntitySaved7);
        positions3.add(positionEntitySaved8);
        positions3.add(positionEntitySaved9);

        Collection<PositionEntity> positions4 = new HashSet<>();
        positions4.add(positionEntitySaved10);
        positions4.add(positionEntitySaved11);
        positions4.add(positionEntitySaved12);

        TransactionEntity transactionEntity1 = new TransactionEntity(Status.INREALIZATION, date3, 0, customerSave1, positions1);
        TransactionEntity transactionEntity2 = new TransactionEntity(Status.INREALIZATION, date4, 0, customerSave2, positions2);
        TransactionEntity transactionEntity3 = new TransactionEntity(Status.INREALIZATION, date3, 0, customerSave3, positions3);
        TransactionEntity transactionEntity4 = new TransactionEntity(Status.INREALIZATION, date4, 0, customerSave4, positions4);
        TransactionEntity transactionSave1 = transactionDao.save(transactionEntity1);
        TransactionEntity transactionSave2 = transactionDao.save(transactionEntity2);
        TransactionEntity transactionSave3 = transactionDao.save(transactionEntity3);
        TransactionEntity transactionSave4 = transactionDao.save(transactionEntity4);

        positions1.forEach(position -> position.setTransaction(transactionSave1));
        positions2.forEach(position -> position.setTransaction(transactionSave2));
        positions3.forEach(position -> position.setTransaction(transactionSave3));
        positions4.forEach(position -> position.setTransaction(transactionSave4));

        Float profitTransactions = 0.0f;

        for (PositionEntity position : positions1){
            profitTransactions+=position.getAmount().floatValue()*position.getProduct().getProfit()*position.getProduct().getPrice();
        }
        for (PositionEntity position : positions2){
               profitTransactions+=position.getAmount().floatValue()*position.getProduct().getProfit()*position.getProduct().getPrice();
        }
        for (PositionEntity position : positions3){
               profitTransactions+=position.getAmount().floatValue()*position.getProduct().getProfit()*position.getProduct().getPrice();
        }
        for (PositionEntity position : positions4){
              profitTransactions+=position.getAmount().floatValue()*position.getProduct().getProfit()*position.getProduct().getPrice();
        }

        positions1.forEach(position -> position.setTransaction(transactionSave1));
        positions2.forEach(position -> position.setTransaction(transactionSave2));
        positions3.forEach(position -> position.setTransaction(transactionSave3));
        positions4.forEach(position -> position.setTransaction(transactionSave4));
        //when

        Float check = myQueries.getProfitFromPeriod(date1, date2);

        //then
        Integer checkRound = Math.round(check);
        Integer profitTransactionsRound = Math.round(profitTransactions);

        Assertions.assertThat(checkRound).isEqualTo(profitTransactionsRound);

    }
}
