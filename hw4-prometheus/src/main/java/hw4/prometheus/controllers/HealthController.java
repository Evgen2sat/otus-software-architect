package hw4.prometheus.controllers;

import hw4.prometheus.database.DatabaseService;
import hw4.prometheus.dto.HealthDto;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

import javax.inject.Inject;
import java.sql.SQLException;

@Controller("/health")
public class HealthController {

    @Get
    public HealthDto getHealth() {
        HealthDto result = new HealthDto();
        result.setStatus("OK");

        return result;
    }
}
