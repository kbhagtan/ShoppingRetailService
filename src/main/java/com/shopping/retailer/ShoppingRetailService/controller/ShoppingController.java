package com.shopping.retailer.ShoppingRetailService.controller;

import com.shopping.retailer.ShoppingRetailService.constants.ProductConstants;
import com.shopping.retailer.ShoppingRetailService.service.ProductInfoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@RestController
public class ShoppingController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ProductInfoServiceImpl productInfoServiceImpl;

    @Value("${addToCart.url}")
    private String callingUrl;

    @Value("${avail.url}")
    private String availUrl;

    /**
     * @param productName the productName
     * @return productAvailability
     * @throws IOException
     */

    @GetMapping("/productAvail/{productName}")
    public String productAvailability(@RequestParam String productName) throws IOException {
        String json = restTemplate.getForEntity(availUrl, String.class).getBody();
        boolean productAvailable = productInfoServiceImpl.checkProduct(productName, json);
        return (productAvailable) ? "Available" : "Unavailable";
    }

    /**
     * @param quantity    the quantity
     * @param productName the product name
     * @return items added to cart
     */
    @GetMapping("/addToBag/{quantity}/{productName}")
    public String addToCart(@PathVariable int quantity, @PathVariable String productName) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(callingUrl)
                .queryParam("quantity", quantity)
                .queryParam("productName", productName);

        String uri = builder.toUriString();
        String response = restTemplate.getForEntity(uri, String.class).getBody();
        updateProductCart(quantity, productName);
        StringBuilder sb = new StringBuilder();
        if (response != null && response.equalsIgnoreCase("Added to cart"))
            return sb.append(quantity).append(ProductConstants.ADDED_TO_CART).toString();
        else
            return ProductConstants.ITEM_UNAVAILABLE;
    }

    /**
     * Checks if Product already exists in database,If yes it is updated else new record is created
     *
     * @param quantity    the quantity
     * @param productName the productName
     */
    private void updateProductCart(@PathVariable int quantity, @PathVariable String productName) {
        boolean productExists = productInfoServiceImpl.checkIfProductExists(productName);
        if (productExists)
            productInfoServiceImpl.updateProductCart(quantity, productName);
        else
            productInfoServiceImpl.addNewProduct(quantity, productName);
    }


}