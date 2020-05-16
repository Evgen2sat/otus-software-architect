package hw5.api.gateway.auth.controllers;

import hw5.api.gateway.auth.dto.SignInDto;
import hw5.api.gateway.auth.dto.SignUpDto;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecuredAnnotationRule;

@Controller()
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Secured(SecuredAnnotationRule.IS_ANONYMOUS)
public class UserController {

    @Post("/signin")
    public HttpResponse signIn(@Body SignInDto data) {
        return HttpResponse.ok(data.getUsername() + " " + data.getPassword());
    }

    @Post("/signup")
    public HttpResponse singUp(SignUpDto data) {
        return HttpResponse.ok(data.getUsername() + " " + data.getPassword() + " " + data.getLastName() + " " +
                data.getFirstName() + " " + data.getMiddleName());
    }
}
