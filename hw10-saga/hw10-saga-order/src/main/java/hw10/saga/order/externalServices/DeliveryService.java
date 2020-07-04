package hw10.saga.order.externalServices;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import hw10.saga.order.dto.ErrorDto;
import hw10.saga.order.dto.ReserveCourierRequestDto;
import hw10.saga.order.dto.ReserveCourierResponseDto;
import hw10.saga.order.gsonAdapters.LocalDateAdapter;
import hw10.saga.order.gsonAdapters.LocalDateTimeAdapter;
import hw10.saga.order.gsonAdapters.LocalTimeAdapter;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.inject.Singleton;
import java.lang.annotation.Annotation;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

@Singleton
public class DeliveryService {
    private final DeliveryAPI api;
    private final Converter<ResponseBody, ErrorDto> errorConverter;

    public DeliveryService() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .build();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(System.getenv("delivery_uri"))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();

        errorConverter = retrofit.responseBodyConverter(ErrorDto.class, new Annotation[0]);

        api = retrofit.create(DeliveryAPI.class);
    }

    public ReserveCourierResponseDto reserveCourier(long orderId, String deliveryLocalDateTime) {
        ReserveCourierResponseDto response = new ReserveCourierResponseDto();

        ReserveCourierRequestDto request = new ReserveCourierRequestDto();
        request.setOrderId(orderId);
        request.setDeliveryDateTime(LocalDateTime.parse(deliveryLocalDateTime));

        try {
            Response<Long> executeResponse = api.reserveCourier(request).execute();

            if(!executeResponse.isSuccessful()) {
                response.setError(errorConverter.convert(executeResponse.errorBody()));
            } else {
                response.setReserveId(executeResponse.body());
            }

        } catch (Exception e) {
            ErrorDto errorDto = new ErrorDto();
            errorDto.setMessage(e.getMessage());
            response.setError(errorDto);
        }

        return response;
    }
}
