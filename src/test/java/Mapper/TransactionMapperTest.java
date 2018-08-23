package Mapper;

import com.capgemini.StarterKitJpaStarterApplication;
import com.capgemini.domain.PositionEntity;
import com.capgemini.domain.ProductEntity;
import com.capgemini.domain.TransactionEntity;
import com.capgemini.enums.Status;
import com.capgemini.mappers.TransactionMapper;
import com.capgemini.types.ProductTO;
import com.capgemini.types.TransactionTO;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.Position;
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
        PositionEntity positionEntity = new PositionEntity(5, null, null);
        Collection<PositionEntity> positionEntities = new HashSet<>();
        positionEntities.add(positionEntity);

        TransactionEntity transactionEntity1 = new TransactionEntity( Status.INREALIZATION, new Date(2018-10-10), 2, null, positionEntities);


        //when
        TransactionTO transactionTO = TransactionMapper.toTransactionTO(transactionEntity1);

        //then
        int size = transactionTO.getPositions().size();
        Assertions.assertThat(size).isEqualTo(1);
    }
}
