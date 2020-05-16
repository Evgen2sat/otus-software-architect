package hw5.api.gateway.auth.controllers;

import hw5.api.gateway.auth.dto.SignInDto;
import hw5.api.gateway.auth.dto.SignUpDto;
import hw5.api.gateway.auth.services.UserService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecuredAnnotationRule;

import javax.inject.Inject;

@Controller()
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Secured(SecuredAnnotationRule.IS_ANONYMOUS)
public class UserController {

    @Inject
    private UserService userService;

    @Post("/signin")
    public HttpResponse signIn(@Body SignInDto data) {
        return userService.signIn(data);
    }

    @Post("/signup")
    public HttpResponse singUp(SignUpDto data) {
        return userService.signUp(data);
    }
}
