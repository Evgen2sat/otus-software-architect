package hw10.saga.payment.services;

import hw10.saga.payment.dto.ErrorDto;
import hw10.saga.payment.dto.SendPaymentRequestDto;
import hw10.saga.payment.repositories.PaymentRepository;
import io.micronaut.http.HttpResponse;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PaymentService {
    @Inject
    private PaymentRepository paymentRepository;

    public HttpResponse sendPayment(SendPaymentRequestDto data) {
        try {
            long paymentId = paymentRepository.sendPayment(data);
            return HttpResponse.ok(paymentId);
        } catch (Exception e) {
            ErrorDto errorDto = new ErrorDto();
            errorDto.setMessage(e.getMessage());
            return HttpResponse.serverError(errorDto);
        }
    }

    public HttpResponse rejectPayment(long paymentId) {
        try {
            paymentRepository.rejectPayment(paymentId);
            return HttpResponse.ok();
        } catch (Exception e) {
            ErrorDto errorDto = new ErrorDto();
            errorDto.setMessage(e.getMessage());
            return HttpResponse.serverError(errorDto);
        }
    }
}
