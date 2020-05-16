package hw5.api.gateway.controllers;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import hw5.api.gateway.dto.UserDto;
import hw5.api.gateway.repositories.UserRepository;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;

import javax.inject.Inject;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.Duration;

@Controller("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserController {

    private MeterRegistry meterRegistry;

    public UserController(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Inject
    private UserRepository userRepository;

//    @Get("/me")
//    public HttpResponse me(final HttpHeaders headers) {
//        return HttpResponse.ok(headers.get("X-UserId"));
//    }

    @Put("/{id}")
    public HttpResponse update(long id, @Body UserDto user, final HttpHeaders headers) throws Exception {
        return Timer.builder("app_request_latency")
                .description("Application Request Latency")
                .tag("method", "UPDATE")
//                .tag("endpoint", "/" + id)
                .tag("endpoint", "/users")
                .publishPercentileHistogram()
                .sla(Duration.ofMillis(100))
                .minimumExpectedValue(Duration.ofMillis(1))
                .maximumExpectedValue(Duration.ofSeconds(10))
                .register(this.meterRegistry)
                .record(() -> {
                    try {
//                        JWTClaimsSet claimsJws = JWTParser.parse(headers.getAuthorization().get().replace("Bearer", "")).getJWTClaimsSet();
//                        long userIdFromJwt = claimsJws.getLongClaim("id");
                        String userIdFromJwt = headers.get("X-UserId");
                        if(userIdFromJwt == null || userIdFromJwt.isEmpty()
                            || Long.valueOf(userIdFromJwt) != id) {
                            return HttpResponse.status(HttpStatus.FORBIDDEN);
                        }

                        return HttpResponse.ok(userRepository.update(id, user));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Get("/{id}")
    public HttpResponse get(long id, final HttpHeaders headers) throws Exception {
        return Timer.builder("app_request_latency")
                .description("Application Request Latency")
                .tag("method", "GET")
//                .tag("endpoint", "/" + id)
                .tag("endpoint", "/users")
                .publishPercentileHistogram()
                .sla(Duration.ofMillis(100))
                .minimumExpectedValue(Duration.ofMillis(1))
                .maximumExpectedValue(Duration.ofSeconds(10))
                .register(this.meterRegistry)
                .record(() -> {
                    try {
//                        JWTClaimsSet claimsJws = JWTParser.parse(headers.getAuthorization().get().replace("Bearer", "")).getJWTClaimsSet();
//                        long userIdFromJwt = claimsJws.getLongClaim("id");
                        String userIdFromJwt = headers.get("X-UserId");
                        if(userIdFromJwt == null || userIdFromJwt.isEmpty()
                                || Long.valueOf(userIdFromJwt) != id) {
                            return HttpResponse.status(HttpStatus.FORBIDDEN);
                        }

                        return HttpResponse.ok(userRepository.get(id));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
