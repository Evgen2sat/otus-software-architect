package hw10.saga.order.externalServices;

import hw10.saga.order.dto.SendPaymentRequestDto;
import retrofit2.Call;
import retrofit2.http.*;

public interface PaymentAPI {
    @Headers("Content-Type: application/json")
    @POST("v1/payments")
    Call<Long> sendPayment(@Body SendPaymentRequestDto data);

    @Headers("Content-Type: application/json")
    @DELETE("v1/payments/{id}")
    Call<Void> rejectPayment(@Path("id") long paymentId);
}
