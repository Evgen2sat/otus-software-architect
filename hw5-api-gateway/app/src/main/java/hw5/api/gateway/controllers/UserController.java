package hw5.api.gateway.controllers;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import hw5.api.gateway.dto.UserDto;
import hw5.api.gateway.repositories.UserRepository;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micronaut.http.HttpHeaders;
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

    @Get("/me")
    public UserDto getMe(final HttpHeaders headers) throws Exception {
        JWTClaimsSet claimsJws = JWTParser.parse(headers.getAuthorization().get().replace("Bearer", "")).getJWTClaimsSet();
        long id = claimsJws.getLongClaim("id");

        return get(id);
    }

    @Post
    public UserDto create(@Body UserDto user) throws SQLException {
        return Timer.builder("app_request_latency")
                .description("Application Request Latency")
                .tag("method", "POST")
                .tag("endpoint", "/users")
                .publishPercentileHistogram()
                .sla(Duration.ofMillis(100))
                .minimumExpectedValue(Duration.ofMillis(1))
                .maximumExpectedValue(Duration.ofSeconds(10))
                .register(this.meterRegistry)
                .record(() -> {
                    try {
                        return userRepository.create(user);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Put("/{id}")
    public UserDto update(long id, @Body UserDto user) throws SQLException {
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
                        return userRepository.update(id, user);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Get("/{id}")
    public UserDto get(long id) throws SQLException {
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
                        return userRepository.get(id);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Delete("/{id}")
    public void delete(long id) throws SQLException {
        Timer.builder("app_request_latency")
                .description("Application Request Latency")
                .tag("method", "DELETE")
//                .tag("endpoint", "/" + id)
                .tag("endpoint", "/users")
                .publishPercentileHistogram()
                .sla(Duration.ofMillis(100))
                .minimumExpectedValue(Duration.ofMillis(1))
                .maximumExpectedValue(Duration.ofSeconds(10))
                .register(this.meterRegistry)
                .record(() -> {
                    try {
                        userRepository.delete(id);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
