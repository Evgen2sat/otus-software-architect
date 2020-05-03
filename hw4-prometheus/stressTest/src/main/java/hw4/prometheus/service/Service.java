package hw4.prometheus.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import hw4.prometheus.dto.UserDto;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

public class Service {
    private final API api;

    public Service() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .build();

        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(System.getenv("SERVICE_URI"))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();

        api = retrofit.create(API.class);
    }

    public UserDto create(UserDto user) throws IOException {
        return api.create(user).execute().body();
    }

    public void update(long id, UserDto user) throws IOException {
        api.update(id, user).execute();
    }

    public void get(long id) throws IOException {
        api.get(id).execute();
    }

    public void delete(long id) throws IOException {
        api.delete(id).execute();
    }
}
