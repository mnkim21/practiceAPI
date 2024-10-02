package org.example.practice.service;

import org.example.practice.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
Store and manage the product in memory
* */
@Service
public class ProductService {
    private Map<String, Product> productStore = new HashMap<>();
    private long nextId = 1; // Starting ID for new products

    // Get all Products
    public List<Product> getAllProducts() {
        return new ArrayList<>(productStore.values());
    }

    // Get a product by ID
    public Product getProductById(String productUid){
        // Return the product if it exists, otherwise return null
        return productStore.get(productUid);
    }

    // Add new product
    public void addProduct(Product product){
        // Check if productUid is null or empty
//        if (product.getProductUid() == null || product.getProductUid().isEmpty()) {
//            throw new IllegalArgumentException("productUid is required.");
//        }
//
//        // Check for uniqueness of productUid
//        if(productStore.containsKey(product.getProductUid())) {
//            throw new IllegalArgumentException("productUid must be unique. A product with this uid already exists.");
//        }
        // Use the product ID as the key and store the product in the HashMap
        product.setProductUid(String.valueOf(nextId++)); // Set a unique productUid before storing it
        productStore.put(product.getProductUid(), product);
    }

    // Update product by ID
    public boolean updateProduct(String productUid, Product product){
        // Check if the product exists in the store
        if (productStore.containsKey(productUid)) {
            // If it exists, update it with the new product details
            productStore.put(productUid, product);
            return true; // Indicate that the update was successful
        }
        return false; // Return false if the product does not exist
    }

    // Delete a product by its ID
    public boolean deleteProduct(String productUid) {
        if (productStore.containsKey(productUid)) {
            // If it exists, remove it from the store
            productStore.remove(productUid);
            return true; // Indicate that the deletion was successful
        }
        return false; // Return false if the product does not exist
    }
}
