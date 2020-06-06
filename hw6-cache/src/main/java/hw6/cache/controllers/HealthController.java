package hw6.cache.controllers;

import hw6.cache.dto.HealthDto;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;

@Controller("/health")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class HealthController {
    @Get
    public HttpResponse auth(final HttpHeaders headers) {
        HealthDto healthDto = new HealthDto();
        healthDto.setStatus("OK");
        return HttpResponse.ok(healthDto);
    }
}
