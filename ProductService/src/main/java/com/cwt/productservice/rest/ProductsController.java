package com.cwt.productservice.rest;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @PostMapping
    public String createProduct() {
        return "http post handled";
    }

    @GetMapping
    public String getProduct() {
        return "http get handeled";
    }

    @PutMapping
    public String updateProduct() {
        return "http put handled";
    }

    @DeleteMapping
    public String deleteProduct() {
        return "http deleted handled";
    }


}
