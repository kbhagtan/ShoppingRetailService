package com.shopping.retailer.ShoppingRetailService.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping.retailer.ShoppingRetailService.pojos.ProductInfo;
import com.shopping.retailer.ShoppingRetailService.repository.ProductInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

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

    /**
     * Checks the product availability
     *
     * @param productName the productName
     * @param json        the json from service
     * @return product
     * @throws IOException
     */
    public boolean checkProduct(@RequestParam String productName, String json) throws IOException {
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
