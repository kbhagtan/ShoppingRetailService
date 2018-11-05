package com.shopping.retailer.ShoppingRetailService.service;

import com.shopping.retailer.ShoppingRetailService.pojos.ProductInfo;
import com.shopping.retailer.ShoppingRetailService.repository.ProductInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Override
    public boolean checkIfProductExists(String productName) {
        int count = productInfoRepository.checkIfProductExists(productName);
        if (count > 0)
            return true;
        else
            return false;
    }

    @Override
    public void updateProductCart(int noOfItems, String prodName) {
        productInfoRepository.updateProductInfo(noOfItems, prodName);
    }

    @Override
    public void addNewProduct(int noOfItems, String prodName) {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setNoOfItems(noOfItems);
        productInfo.setProductName(prodName);
        productInfoRepository.save(productInfo);
    }

}
