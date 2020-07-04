package hw10.saga.order.externalServices;

import hw10.saga.order.dto.ReserveCourierRequestDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface DeliveryAPI {
    @Headers("Content-Type: application/json")
    @POST("v1/deliveries")
    Call<Long> reserveCourier(@Body ReserveCourierRequestDto data);
}
