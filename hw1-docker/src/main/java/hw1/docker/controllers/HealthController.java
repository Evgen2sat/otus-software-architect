package hw1.docker.controllers;

import hw1.docker.dto.ResponseDto;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/health")
public class HealthController {

    @Get
    public ResponseDto getHealth() {
        ResponseDto result = new ResponseDto();
        result.setStatus("OK");

        return result;
    }
}
