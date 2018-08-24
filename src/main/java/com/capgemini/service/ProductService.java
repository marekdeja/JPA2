package com.capgemini.service;

import com.capgemini.types.PositionTO;
import com.capgemini.types.ProductTO;

import java.util.Collection;

public interface ProductService {

    ProductTO saveProduct(ProductTO productTO);
    void removeProduct(Long id);
    Collection<ProductTO> findAll();
    ProductTO findOne(Long id);

}
