package com.capgemini.service.impl;

import com.capgemini.dao.ProductDao;
import com.capgemini.dao.ProductDaoSpringData;
import com.capgemini.domain.ProductEntity;
import com.capgemini.mappers.ProductMapper;
import com.capgemini.service.ProductService;
import com.capgemini.types.ProductTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductDaoSpringData productDaoSpringData;

    @Override
    public ProductTO saveProduct(ProductTO productTO) {
        ProductEntity productEntity = productDao.save(productMapper.toProductEntity(productTO));
        return ProductMapper.toProductTO((productEntity));
    }

    @Override
    public void removeProduct(Long id) {
        productDaoSpringData.removeById(id);
    }

    @Override
    public Collection<ProductTO> findAll() {
        Collection<ProductEntity> productFound = productDaoSpringData.findAll();
        return ProductMapper.map2TOs(productFound);
    }

    @Override
    public ProductTO findOne(Long id) {
        return ProductMapper.toProductTO(productDaoSpringData.findOne(id));
    }


}
