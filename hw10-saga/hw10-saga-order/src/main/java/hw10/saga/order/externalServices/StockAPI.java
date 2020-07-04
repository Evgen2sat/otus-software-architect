package hw10.saga.order.externalServices;

import hw10.saga.order.dto.ReserveProductsRequestDto;
import retrofit2.Call;
import retrofit2.http.*;

public interface StockAPI {
    @Headers("Content-Type: application/json")
    @POST("v1/stocks")
    Call<Long> reserveProducts(@Body ReserveProductsRequestDto data);

    @Headers("Content-Type: application/json")
    @DELETE("v1/stocks/{id}")
    Call<Void> rejectReserveProducts(@Path("id") long reserveId);
}
