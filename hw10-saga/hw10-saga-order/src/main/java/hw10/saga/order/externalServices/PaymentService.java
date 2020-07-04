package hw10.saga.order.externalServices;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import hw10.saga.order.dto.ErrorDto;
import hw10.saga.order.dto.SendPaymentRequestDto;
import hw10.saga.order.dto.SendPaymentResponseDto;
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
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

@Singleton
public class PaymentService {
    private final PaymentAPI api;
    private final Converter<ResponseBody, ErrorDto> errorConverter;

    public PaymentService() {

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
                .baseUrl(System.getenv("payment_uri"))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();

        errorConverter = retrofit.responseBodyConverter(ErrorDto.class, new Annotation[0]);

        api = retrofit.create(PaymentAPI.class);
    }

    public SendPaymentResponseDto sendPayment(long orderId, float summ) {
        SendPaymentResponseDto response = new SendPaymentResponseDto();

        SendPaymentRequestDto request = new SendPaymentRequestDto();
        request.setOrderId(orderId);
        request.setSumm(summ);

        try {
            Response<Long> executeResponse = api.sendPayment(request).execute();

            if(!executeResponse.isSuccessful()) {
                response.setError(errorConverter.convert(executeResponse.errorBody()));
            } else {
                response.setPaymentId(executeResponse.body());
            }

        } catch (Exception e) {
            ErrorDto errorDto = new ErrorDto();
            errorDto.setMessage(e.getMessage());
            response.setError(errorDto);
        }

        return response;
    }

    public void rejectPayment(long paymentId) throws IOException {
        api.rejectPayment(paymentId).execute();
    }
}
