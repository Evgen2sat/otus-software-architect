package hw9.api.controllers;

import hw9.api.dto.OrderDto;
import hw9.api.services.OrderService;
import io.micronaut.http.*;
import io.micronaut.http.annotation.*;

import javax.inject.Inject;

@Controller("/v1/orders")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OrderController {

    @Inject
    private OrderService orderService;

    @Post
    public HttpResponse create(final HttpHeaders headers, OrderDto data) {
        return orderService.create(headers.get("X-OrderUUID"), data);
    }

    @Get
    public HttpResponse getOrderUuid() {
        return orderService.getOrderUuid();
    }
}
