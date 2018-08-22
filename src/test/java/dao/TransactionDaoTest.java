package dao;

import com.capgemini.StarterKitJpaStarterApplication;
import com.capgemini.dao.TransactionDao;
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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StarterKitJpaStarterApplication.class)
public class TransactionDaoTest {

    @Autowired
    private TransactionDao transactionDao;

    @Test
    @Transactional
    public void shouldSaveThreeCarsAndSizeIsThree(){
        //given
        TransactionEntity transactionEntity1 = new TransactionEntity( Status.INREALIZATION, new Date(2018-10-10), 0, null, null);
        TransactionEntity transactionEntity2 = new TransactionEntity( Status.INREALIZATION, new Date(2017-10-10), 0, null, null);
        TransactionEntity transactionEntity3 = new TransactionEntity( Status.INREALIZATION, new Date(2016-10-10), 0, null, null);

        //when
        TransactionEntity save1 = transactionDao.save(transactionEntity1);
        TransactionEntity save2 = transactionDao.save(transactionEntity2);
        TransactionEntity save3 = transactionDao.save(transactionEntity3);

        //then
        long size = transactionDao.count();
        Assertions.assertThat(size).isEqualTo(3);
    }

    @Test
    @Transactional
    public void shouldDeleteOneEntityFromThreeEntitiesThenSizeIsTwo(){
        //given
        TransactionEntity transactionEntity1 = new TransactionEntity( Status.INREALIZATION, new Date(2018-10-10), 0, null, null);
        TransactionEntity transactionEntity2 = new TransactionEntity( Status.INREALIZATION, new Date(2017-10-10), 2, null, null);
        TransactionEntity transactionEntity3 = new TransactionEntity( Status.INREALIZATION, new Date(2016-10-10), 3, null, null);

        TransactionEntity save1 = transactionDao.save(transactionEntity1);
        TransactionEntity save2 = transactionDao.save(transactionEntity2);
        TransactionEntity save3 = transactionDao.save(transactionEntity3);

        //when
        transactionDao.delete(save1.getId());

        //then
        long size = transactionDao.count();
        Assertions.assertThat(size).isEqualTo(2);
        Assertions.assertThat(transactionDao.getOne(2L).getAmount()).isEqualTo(2);
        Assertions.assertThat(transactionDao.getOne(3L).getAmount()).isEqualTo(3);

    }
}
