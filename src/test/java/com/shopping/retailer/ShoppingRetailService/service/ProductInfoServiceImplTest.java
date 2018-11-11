package com.shopping.retailer.ShoppingRetailService.service;

import com.shopping.retailer.ShoppingRetailService.pojos.ProductInfo;
import com.shopping.retailer.ShoppingRetailService.repository.ProductInfoRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ProductInfoServiceImplTest {

    @InjectMocks
    ProductInfoServiceImpl productInfoService;

    @Mock
    ProductInfoRepository productInfoRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void checkIfProductExists() {
        when(productInfoRepository.checkIfProductExists("Puma")).thenReturn(1);
        boolean result = productInfoService.checkIfProductExists("Puma");
        assertTrue(result);
    }

    @Test
    public void checkIfProductExistFalse() {
        when(productInfoRepository.checkIfProductExists("Park Avenue")).thenReturn(0);
        boolean result = productInfoService.checkIfProductExists("Park Avenue");
        assertFalse(result);
    }

    @Test
    public void updateProductCart() {
        int noOfItems = 2;
        String prodName = "Puma";
        doNothing().when(productInfoRepository).updateProductInfo(noOfItems, prodName);
        productInfoService.updateProductCart(noOfItems, prodName);
    }

    @Test
    public void addNewProduct() {
        int noOfItems = 1;
        String prodName = "Levis";
        ProductInfo productInfo = new ProductInfo();
        productInfo.setNoOfItems(noOfItems);
        productInfo.setProductName(prodName);
        when(productInfoRepository.save(productInfo)).thenReturn(productInfo);
        assertEquals(productInfo.getNoOfItems(), noOfItems);
        assertEquals(productInfo.getProductName(), prodName);
        productInfoService.addNewProduct(noOfItems, prodName);
    }


}
