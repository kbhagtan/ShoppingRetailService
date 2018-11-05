package com.shopping.retailer.ShoppingRetailService.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping.retailer.ShoppingRetailService.constants.ProductConstants;
import com.shopping.retailer.ShoppingRetailService.service.ProductInfoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import springfox.documentation.annotations.Cacheable;

import java.io.IOException;
import java.util.Arrays;

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

    @Cacheable("productAvailability")
    @GetMapping("/productAvail/{productName}")
    public String productAvailability(@RequestParam String productName) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        String json = restTemplate.exchange(availUrl, HttpMethod.GET, entity, String.class).getBody();
        boolean productAvailable = checkProduct(productName, json);
        return (productAvailable) ? "Available" : "Unavailable";
    }

    /**
     * @param quantity    the quantity
     * @param productName the product name
     * @return items added to cart
     */
    @GetMapping("/addToBag/{quantity}/{productName}")
    public String addToCart(@PathVariable int quantity, @PathVariable String productName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(callingUrl)
                .queryParam("quantity", quantity)
                .queryParam("productName", productName);

        String uri = builder.toUriString();
        String response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class).getBody();
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

    /**
     * Checks the product availability
     *
     * @param productName the productName
     * @param json        the json from service
     * @return product
     * @throws IOException
     */
    private boolean checkProduct(@RequestParam String productName, String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(json);
        int quantity;
        boolean flag = false;
        String prodName;
        for (JsonNode node : rootNode) {
            quantity = node.get("quantity").asInt();
            prodName = node.get("productName").asText();

            if (prodName.equals(productName) && quantity > 0) {
                flag = true;
            }
        }
        return flag;
    }
}