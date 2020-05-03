package hw4.prometheus.service;

import hw4.prometheus.dto.UserDto;
import retrofit2.Call;
import retrofit2.http.*;

public interface API {
    @Headers("Host: arch.homework")
    @POST("/otusapp/users")
    Call<UserDto> create(@Body UserDto user);

    @Headers("Host: arch.homework")
    @PUT("/otusapp/users/{id}")
    Call<Void> update(@Path("id") long id, @Body UserDto user);

    @Headers("Host: arch.homework")
    @GET("/otusapp/users/{id}")
    Call<Void> get(@Path("id") long id);

    @Headers("Host: arch.homework")
    @DELETE("/otusapp/users/{id}")
    Call<Void> delete(@Path("id") long id);
}
