package org.example.practice.controller;

import org.example.practice.model.Product;
import org.example.practice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product") // Base URL for all endpoints in this controller.
public class ProductController {

    // Injecting ProductService to use its methods for product management
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        // Calls ProductService to get all products and returns them in the response
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products); // HTTP 200 response with the list of products
    }

    // Handle HTTP GET requests to retrieve a specific product by its unique ID
    @GetMapping("/{productUid}")
    public ResponseEntity<Product> getProductById(@PathVariable String productUid){
        // Calls ProductService to get a product by its ID
        Product product = productService.getProductById(productUid);
        if(product != null) {
            return ResponseEntity.ok(product); // If product exists, return HTTP 200 with product data
        } else {
            return ResponseEntity.notFound().build(); // If product doesn't exist, return HTTP 404
        }
    }

    // Handle HTTP POST requests to add a new product
    @PostMapping
    public ResponseEntity<String> addProduct(@RequestBody Product product){
        try {
            productService.addProduct(product);
            return ResponseEntity.ok("Product added successfully!"); // Return success message
        } catch (IllegalArgumentException e){
            // Return error message with status 400 (Bad Request)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Handle HTTP PUT requests to update an existing product
    @PutMapping("/{productUid}")
    public ResponseEntity<String> updateProduct(@PathVariable String productUid, @RequestBody Product product) {
        // Calls ProductService to update a product by its ID
        boolean updated = productService.updateProduct(productUid, product);
        if (updated) {
            return ResponseEntity.ok("Product updated successfully!"); // Return success if updated
        } else {
            return ResponseEntity.notFound().build(); // Return 404 if product ID doesn't exist
        }
    }

    // Handle HTTP DELETE requests to delete a product by its unique ID
    @DeleteMapping("/{productUid}")
    public ResponseEntity<String> deleteProduct(@PathVariable String productUid){
        boolean deleted = productService.deleteProduct(productUid);
        if(deleted){
            return ResponseEntity.ok("Product deleted successfully!"); // Return success message
        } else {
            return ResponseEntity.notFound().build(); // Return 404 if product doesn't exist
        }
    }
}
