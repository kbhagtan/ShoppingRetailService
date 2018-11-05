package com.shopping.retailer.ShoppingRetailService.repository;

import com.shopping.retailer.ShoppingRetailService.pojos.ProductInfo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface ProductInfoRepository extends CrudRepository<ProductInfo,Long> {

    @Query("SELECT COUNT(p.productName) FROM ProductInfo p WHERE p.productName = :proName")
    int checkIfProductExists(@Param("proName") String prodName);

    @Transactional
    @Modifying
    @Query("UPDATE ProductInfo set noOfItems = noOfItems + :quan where productName = :proName")
    void updateProductInfo(@Param("quan") int noOfItems, @Param("proName") String prodName);

    @Override
    <S extends ProductInfo> S save(S s);
}
