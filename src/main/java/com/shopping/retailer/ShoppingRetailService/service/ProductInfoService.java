package com.shopping.retailer.ShoppingRetailService.service;

public interface ProductInfoService {
    boolean checkIfProductExists(String productName);

    void updateProductCart(int noOfItems, String prodName);

    void addNewProduct(int noOfItems, String prodName);
}
