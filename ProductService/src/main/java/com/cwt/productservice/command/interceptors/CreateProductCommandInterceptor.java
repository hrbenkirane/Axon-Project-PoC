package com.cwt.productservice.command.interceptors;

import com.cwt.productservice.command.CreateProductCommand;
import com.cwt.productservice.core.data.ProductLookupEntity;
import com.cwt.productservice.core.data.ProductLookupRepository;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.BiFunction;

@Component
public class CreateProductCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

    private final ProductLookupRepository productLookupRepository;

    public CreateProductCommandInterceptor(ProductLookupRepository productLookupRepository) {
        this.productLookupRepository = productLookupRepository;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateProductCommandInterceptor.class);

    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(List<? extends CommandMessage<?>> list) {
        return (index, command) -> {
            LOGGER.info("Intercepted command: " + command.getPayloadType());
            if(CreateProductCommand.class.equals(command.getPayloadType())) {
                CreateProductCommand createProductCommand = (CreateProductCommand) command.getPayload();
                ProductLookupEntity productLookupEntity =
                        productLookupRepository.findByProductIdOrTitle(createProductCommand.getProductId(),
                                                                       createProductCommand.getTitle());
                if(productLookupEntity != null) {
                    throw new IllegalStateException(
                            String.format("Productt with productId %s or title %s already exist",
                                    createProductCommand.getProductId(), createProductCommand.getTitle());
                    );
                }
//                if(createProductCommand.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
//                    throw new IllegalArgumentException("Price cannot be less or equal than zero");
//                }
//                if(createProductCommand.getTitle() == null || createProductCommand.getTitle().isBlank()) {
//                    throw new IllegalArgumentException("Title cannot be empty!");
//                }
            }
            return command;
        };
    }
}
