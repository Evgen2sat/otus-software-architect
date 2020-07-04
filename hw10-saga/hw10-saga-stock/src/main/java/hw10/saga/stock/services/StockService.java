package hw10.saga.stock.services;

import hw10.saga.stock.dto.ErrorDto;
import hw10.saga.stock.dto.ReserveProductsRequestDto;
import hw10.saga.stock.repositories.StockRepository;
import io.micronaut.http.HttpResponse;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class StockService {
    @Inject
    private StockRepository stockRepository;

    public HttpResponse reserveProducts(ReserveProductsRequestDto data) {
        try {
            long paymentId = stockRepository.reserveProducts(data);
            return HttpResponse.ok(paymentId);
        } catch (Exception e) {
            ErrorDto errorDto = new ErrorDto();
            errorDto.setMessage(e.getMessage());
            return HttpResponse.serverError(errorDto);
        }
    }

    public HttpResponse rejectReserveProducts(long reserveId) {
        try {
            stockRepository.rejectReserveProducts(reserveId);
            return HttpResponse.ok();
        } catch (Exception e) {
            ErrorDto errorDto = new ErrorDto();
            errorDto.setMessage(e.getMessage());
            return HttpResponse.serverError(errorDto);
        }
    }
}
