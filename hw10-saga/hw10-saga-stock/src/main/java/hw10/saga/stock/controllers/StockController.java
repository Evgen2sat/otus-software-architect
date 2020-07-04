package hw10.saga.stock.controllers;

import hw10.saga.stock.dto.ReserveProductsRequestDto;
import hw10.saga.stock.services.StockService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;

import javax.inject.Inject;

@Controller("/v1/stocks")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StockController {

    @Inject
    private StockService stockService;

    @Post
    public HttpResponse reserveProducts(@Body ReserveProductsRequestDto data) {
        return stockService.reserveProducts(data);
    }

    @Delete("/{id}")
    public HttpResponse rejectReserveProducts(@PathVariable("id") long paymentId) {
        return stockService.rejectReserveProducts(paymentId);
    }
}
