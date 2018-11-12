package com.shopping.retailer.ShoppingRetailService.controller;

import com.shopping.retailer.ShoppingRetailService.service.ProductInfoServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
public class ShoppingControllerTest {

    @Mock
    private RestTemplate restTemplate;
    @Mock
    private ProductInfoServiceImpl productInfoServiceImpl;
    @InjectMocks
    private ShoppingController shoppingController;
    @Mock
    ResponseEntity responseEntity;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void productAvailability() throws Exception {
        ReflectionTestUtils.setField(shoppingController, "availUrl", "availUrl");
        Mockito.when(restTemplate.getForEntity(
                Mockito.anyString(),
                any(Class.class)
        ))
                .thenReturn(responseEntity);
        when(productInfoServiceImpl.checkProduct("Nike", "String")).thenReturn(true);
        shoppingController.productAvailability("Nike");
    }

    @Test
    public void addToCart() throws Exception {
        ReflectionTestUtils.setField(shoppingController, "callingUrl", "callingUrl");
        Mockito.when(restTemplate.getForEntity(
                Mockito.anyString(),
                any(Class.class)
        ))
                .thenReturn(responseEntity);
        when(responseEntity.getBody()).thenReturn("Added to cart");
        when(productInfoServiceImpl.checkIfProductExists("Nike")).thenReturn(true);
        doNothing().when(productInfoServiceImpl).updateProductCart(1, "Nike");
        shoppingController.addToCart(1, "Nike");
    }

    @Test
    public void addToCart_Unavailable() {
        ReflectionTestUtils.setField(shoppingController, "callingUrl", "callingUrl");
        Mockito.when(restTemplate.getForEntity(
                Mockito.anyString(),
                any(Class.class)
        ))
                .thenReturn(responseEntity);
        when(responseEntity.getBody()).thenReturn("Unavailable");
        when(productInfoServiceImpl.checkIfProductExists("Nike")).thenReturn(false);
        doNothing().when(productInfoServiceImpl).updateProductCart(1, "Nike");
        shoppingController.addToCart(1, "Nike");
    }
}
