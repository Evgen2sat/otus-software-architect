package hw10.saga.delivery.services;

import hw10.saga.delivery.dto.ErrorDto;
import hw10.saga.delivery.dto.ReserveCourierRequestDto;
import hw10.saga.delivery.repositories.DeliveryRepository;
import io.micronaut.http.HttpResponse;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DeliveryService {
    @Inject
    private DeliveryRepository deliveryRepository;

    public HttpResponse reserveCourier(ReserveCourierRequestDto data) {
        try {
            if (data.getOrderId() % 4 == 0) {
                ErrorDto errorDto = new ErrorDto();
                errorDto.setMessage("Имитируем, что произошла ошибка в резервировании курьера");
                return HttpResponse.serverError(errorDto);
            }

            long reserveId = deliveryRepository.reserveCourier(data);
            return HttpResponse.ok(reserveId);
        } catch (Exception e) {
            ErrorDto errorDto = new ErrorDto();
            errorDto.setMessage(e.getMessage());
            return HttpResponse.serverError(errorDto);
        }
    }
}
