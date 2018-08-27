package com.capgemini.dao;

import com.capgemini.domain.*;
import com.capgemini.enums.Status;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQuery;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class MyQueries {

    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    CustomerDao customerDao;

    QCustomerEntity customer = QCustomerEntity.customerEntity;
    QTransactionEntity transaction = QTransactionEntity.transactionEntity;
    QProductEntity product = QProductEntity.productEntity;
    QPositionEntity position = QPositionEntity.positionEntity;

    public List<CustomerEntity> getCustomers(String name) {
        JPAQuery<CustomerEntity> query = new JPAQuery(entityManager);
        List<CustomerEntity> customers = query.select(customer).from(customer).where(customer.name.eq(name)).fetch();
        return customers;
    }

    public int getAllTransactionsFromCustomer(Long customerId) {
        CustomerEntity customer1 = customerDao.findOne(customerId);
        JPAQuery<CustomerEntity> query = new JPAQuery(entityManager);
        List<TransactionEntity> transactions = query.select(transaction)
                .from(transaction)
                .where(transaction.customer.eq(customer1))
                .fetch();

        return transactions.size();
    }

    //2.a

    /**
     * finding transaction by four parameters, may be given seperately and together
     * @param sc - search criteria
     * @return list of transactionEntities
     */
    public List<TransactionEntity> findTransactionByCriteria(SearchCriteria sc){
        JPAQuery<TransactionEntity> query = new JPAQuery(entityManager);
        query.select(transaction)
                .from(transaction);

        if (sc.getCustomerName()==null && (sc.getStart()==null|| sc.getEnd()==null) && sc.getProduct()==null && sc.getPriceTransaction()==null){
            return null;
        }

        if(sc.getCustomerName()!=null){
            query.join(transaction.customer, customer);
        }
        if (sc.getStart() != null && sc.getEnd() != null){
        }
        if (sc.getProduct()!=null || sc.getPriceTransaction()!=null){
            query.join(transaction.positions, position);
            query.join(position.product, product);
        }

        if(sc.getCustomerName()!=null){
            query.where(customer.name.eq(sc.getCustomerName()));
        }
        if (sc.getStart() != null && sc.getEnd() != null){
            query.where(transaction.date.between(sc.getStart(),sc.getEnd()));
        }
        if (sc.getProduct()!=null){
            query.where(product.eq(sc.getProduct()));
        }

        query.groupBy(transaction);

        if (sc.getPriceTransaction()!=null){

            query.having(position.amount
                    .multiply(product.price)
                    .floatValue().sum()
                    .eq(sc.getPriceTransaction()));
        }

        List<TransactionEntity> transactionsFound = query.fetch();

        if (transactionsFound == null){
            return null;
        }
        return transactionsFound;

    }


    //2.b

    /**
     * count all transaction price of given customer
     * @param customerId
     * @return float totalSum
     */
    public Float getAllTransactionsPriceFromCustomer(Long customerId) {
        JPAQuery<CustomerEntity> query = new JPAQuery(entityManager);
        Float totalSum = query.select(product.price.sum())
                .from(transaction)
                .join(transaction.positions, position)
                .join(position.product, product)
                .where(transaction.customer.id.eq(customerId))
                .fetchOne();

        return totalSum;
    }

    /**
     * calculate total sum of customer's transactions  with given status
     * @param customerId
     * @param status
     * @return float totalSum
     */
    //2.c.1
    public Float getSumTransactionStatusCustomer(Long customerId, Status status) {
        JPAQuery<CustomerEntity> query = new JPAQuery(entityManager);
        Float totalSum = query.select((product.price.multiply(position.amount)).sum())
                .from(transaction)
                .join(transaction.positions, position)
                .join(position.product, product)
                .where(transaction.customer.id.eq(customerId)
                        .and(transaction.status.eq(status)))
                .fetchOne();
        return totalSum;
    }

    /**
     * calculate total sum of all customers' transactions  with given status
     * @param status
     * @param status
     * @return float totalSum
     */
    //2.c.2
    public Float getSumTransactionStatusAllCustomers(Status status) {
        JPAQuery<CustomerEntity> query = new JPAQuery(entityManager);
        Float totalSum = query.select((product.price.multiply(position.amount)).sum())
                .from(transaction)
                .join(transaction.positions, position)
                .join(position.product, product)
                .where(transaction.status.eq(status))
                .fetchOne();
        return totalSum;
    }

    /**
     * find 10 best selling products
     * @return List of popularProducts
     */
    //2.d
    public List<PositionEntity> getTenMostPopularProducts() {
        JPAQuery<PositionEntity> query = new JPAQuery(entityManager);
        List<PositionEntity> popularProducts = query.select(position)
                .from(position)
                .join(position.product, product)
                .groupBy(position.product)
                .orderBy(position.amount.desc())
                .limit(10)
                .fetch();
        return popularProducts;
    }

    //2.e

    /**
     * find 3 customers that spent the greatest amount of money in certain data range
     * @param start
     * @param end
     * @return List of Tuple
     */
    public List<Tuple> getCustomersWithMostPaymentInPeriodTime(Date start, Date end) {
        NumberPath<Float> sumPayment = Expressions.numberPath(Float.class, "sumPayment");
        JPAQuery<CustomerEntity> query = new JPAQuery(entityManager);
        List<Tuple> customers = query.select(customer, product.price.multiply(position.amount).floatValue().as(sumPayment))
                .from(customer)
                .join(customer.transactions, transaction)
                .join(transaction.positions, position)
                .join(position.product, product)
                .groupBy(customer)
                .where(transaction.date.between(start, end))
               .orderBy(sumPayment.desc())
                .limit(3)
                .fetch();
        return customers;
    }

    //2.f

    /**
     * find products in preparation to be sent (status indelivery)
     * @return List of tuple
     */
    public List<Tuple> getProductsToBeSent() {
        JPAQuery<PositionEntity> query = new JPAQuery(entityManager);
        List<Tuple> productsToBeSent = query.select(position.amount, position.product.name)
                .from(transaction)
                .join(transaction.positions, position)
                .where(transaction.status.eq(Status.INDELIVERY))
                .fetch();
        return productsToBeSent;
    }

    /**
     * get profit of transactions in data range
     * @param start
     * @param end
     * @return float totalSum
     */
    //2.g
    public Float getProfitFromPeriod(Date start, Date end) {
        JPAQuery<TransactionEntity> query = new JPAQuery(entityManager);
        Float totalSum = query.select(((position.amount.multiply(product.price)).floatValue().multiply(product.profit)).sum())
                .from(transaction)
                .join(transaction.positions, position)
                .join(position.product, product)
                .where(transaction.date.between(start, end))
                .fetchOne();

        return totalSum;
    }


}

