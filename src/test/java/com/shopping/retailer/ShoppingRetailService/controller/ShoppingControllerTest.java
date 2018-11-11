package com.shopping.retailer.ShoppingRetailService.controller;

import com.shopping.retailer.ShoppingRetailService.service.ProductInfoServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class ShoppingControllerTest {

    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    private ShoppingController shoppingController;

    @Mock
    private ProductInfoServiceImpl productInfoService;

    @Mock
    ResponseEntity responseEntity;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void productAvailability() throws Exception {
        ResponseEntity<String> responseEntity = new ResponseEntity<String>("Nike", HttpStatus.ACCEPTED);
    /*    when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<String>>any()))
                .thenReturn(responseEntity);*/

        verify(restTemplate).exchange(Mockito.anyString(), Mockito.<HttpMethod>eq(HttpMethod.GET), Mockito.<HttpEntity<?>>any(), Mockito.<Class<Object>>any());
        shoppingController.productAvailability("Nike");
    }
}
