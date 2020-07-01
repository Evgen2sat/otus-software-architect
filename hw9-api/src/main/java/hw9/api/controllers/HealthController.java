package hw9.api.controllers;

import hw9.api.dto.HealthDto;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;

@Controller("/health")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class HealthController {

    @Get
    public HttpResponse health() {
        HealthDto healthDto = new HealthDto();
        healthDto.setStatus("OK");
        return HttpResponse.ok(healthDto);
    }
}
