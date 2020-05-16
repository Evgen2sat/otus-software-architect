package hw5.api.gateway.auth.controllers;

import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecuredAnnotationRule;
import io.reactivex.Single;

@Controller("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Secured(SecuredAnnotationRule.IS_AUTHENTICATED)
public class AuthController {

    @Get
    public HttpResponse<Void> auth(final HttpHeaders headers) {
        return HttpResponse.ok();
    }
}
