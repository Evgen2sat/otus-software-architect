package hw10.saga.delivery.controllers;

import hw10.saga.delivery.dto.ReserveCourierRequestDto;
import hw10.saga.delivery.services.DeliveryService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;

import javax.inject.Inject;

@Controller("/v1/deliveries")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DeliveryController {

    @Inject
    private DeliveryService deliveryService;

    @Post
    public HttpResponse reserveCourier(@Body ReserveCourierRequestDto data) {
        return deliveryService.reserveCourier(data);
    }
}
