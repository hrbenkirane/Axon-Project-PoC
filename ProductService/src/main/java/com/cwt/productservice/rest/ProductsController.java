package com.cwt.productservice.rest;

import com.cwt.productservice.command.CreateProductCommand;
import com.netflix.discovery.converters.Auto;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductsController {

    private final Environment environment;
    private final CommandGateway commandGateway;

    @Autowired
    public ProductsController(Environment environment, CommandGateway commandGateway) {
        this.environment = environment;
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public String createProduct(@RequestBody CreateProductRestModel createProductRestModel) {
        CreateProductCommand createProductCommand = CreateProductCommand.builder()
                .price(createProductRestModel.getPrice())
                .quantity(createProductRestModel.getQuantity())
                .title(createProductRestModel.getTitle())
                .productId(UUID.randomUUID().toString()).build();
        String returnValue;
        try {
            returnValue = commandGateway.sendAndWait(createProductCommand);
        } catch (Exception ex) {
            returnValue = ex.getLocalizedMessage();
        }
        return returnValue;
    }

    @GetMapping
    public String getProduct() {
        return "http get handeled " + environment.getProperty("local.server.port");
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
