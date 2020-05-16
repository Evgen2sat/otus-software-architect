package hw5.api.gateway.auth.controllers;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.MutableHttpResponse;
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
    public HttpResponse auth(final HttpHeaders headers) {
        try {
            JWTClaimsSet claimsJws = JWTParser.parse(headers.getAuthorization().get().replace("Bearer", "")).getJWTClaimsSet();
            Long userId = claimsJws.getLongClaim("id");

            MutableHttpResponse<Object> response = HttpResponse.ok();
            response.getHeaders().add("X-UserId", userId.toString());

            return response;
        } catch (Exception e) {
            return HttpResponse.serverError(e);
        }
    }
}
