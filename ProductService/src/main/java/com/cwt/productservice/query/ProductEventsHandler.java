package com.cwt.productservice.query;

import com.cwt.core.events.ProductReservedCancelledEvent;
import com.cwt.core.events.ProductReservedEvent;
import com.cwt.productservice.core.data.ProductEntity;
import com.cwt.productservice.core.data.ProductsRepository;
import com.cwt.productservice.core.event.ProductCreatedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("product-group")
public class ProductEventsHandler {

    private final ProductsRepository productsRepository;

    public ProductEventsHandler(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    @ExceptionHandler(resultType = Exception.class)
    public void handle(Exception exception) throws Exception {
       throw exception;
    }

    @ExceptionHandler(resultType = IllegalArgumentException.class)
    public void handle(IllegalArgumentException exception) {
        // log error message
    }

    @EventHandler
    public void on(ProductCreatedEvent event) {
        ProductEntity productEntity = new ProductEntity();
        BeanUtils.copyProperties(event, productEntity);
        productsRepository.save(productEntity);
    }

    @EventHandler
    public void on(ProductReservedEvent event) {
        ProductEntity productEntity = productsRepository.findByProductId(event.getProductId());
        productEntity.setQuantity(productEntity.getQuantity() - event.getQuantity());
        productsRepository.save(productEntity);
    }

    @EventHandler
    public void on(ProductReservedCancelledEvent event) {
        ProductEntity currentlyStoredProduct = productsRepository.findByProductId(event.getProductId());
        int newQuantity = currentlyStoredProduct.getQuantity() + event.getQuantity();
        currentlyStoredProduct.setQuantity(newQuantity);
        productsRepository.save(currentlyStoredProduct)
    }
}
