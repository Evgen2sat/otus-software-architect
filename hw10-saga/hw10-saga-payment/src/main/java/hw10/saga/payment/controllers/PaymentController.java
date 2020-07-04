package hw10.saga.payment.controllers;

import hw10.saga.payment.dto.SendPaymentRequestDto;
import hw10.saga.payment.services.PaymentService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;

import javax.inject.Inject;

@Controller("/v1/payments")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PaymentController {

    @Inject
    private PaymentService paymentService;

    @Post
    public HttpResponse sendPayment(@Body SendPaymentRequestDto data) {
        return paymentService.sendPayment(data);
    }

    @Delete("/{id}")
    public HttpResponse rejectPayment(@PathVariable("id") long paymentId) {
        return paymentService.rejectPayment(paymentId);
    }
}
