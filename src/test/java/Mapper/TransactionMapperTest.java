package Mapper;

import com.capgemini.StarterKitJpaStarterApplication;
import com.capgemini.domain.ProductEntity;
import com.capgemini.domain.TransactionEntity;
import com.capgemini.enums.Status;
import com.capgemini.mappers.TransactionMapper;
import com.capgemini.types.TransactionTO;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StarterKitJpaStarterApplication.class)
public class TransactionMapperTest {

    @Test
    @Transactional
    public void shouldTransactionTOFromTransactionEntityBeCreated(){
        //given
        TransactionEntity transactionEntity1 = new TransactionEntity( Status.INREALIZATION, new Date(2018-10-10), 2, null, null);
        //when
        TransactionTO transactionTO = TransactionMapper.toTransactionTO(transactionEntity1);
        //then
        Assertions.assertThat(transactionTO.getAmount()).isEqualTo(2);
    }

    @Test
    @Transactional
    public void shouldBuildTOAndProductsSizeBeOne(){
        //given
        ProductEntity productEntity1 = new ProductEntity("Apple", 4.44f, 0.1f, 20, null);
        Collection<ProductEntity> productsEntities = new HashSet<>();
        productsEntities.add(productEntity1);

        TransactionEntity transactionEntity1 = new TransactionEntity( Status.INREALIZATION, new Date(2018-10-10), 2, null, productsEntities);

        TransactionTO transactionTO = TransactionMapper.toTransactionTO(transactionEntity1);

        //when
        int size = transactionTO.getProductIDs().size();
        TransactionTO t = transactionTO.get
        Long id = .getId();
        //then
        Assertions.assertThat(size).isEqualTo(1);
    }
}
