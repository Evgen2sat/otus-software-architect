package hw5.api.gateway.auth.services;

import hw5.api.gateway.auth.dto.JwtDto;
import hw5.api.gateway.auth.dto.SignInDto;
import hw5.api.gateway.auth.dto.SignUpDto;
import hw5.api.gateway.auth.dto.UserDto;
import hw5.api.gateway.auth.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.micronaut.context.annotation.Property;
import io.micronaut.http.HttpResponse;

import javax.crypto.SecretKey;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserService {
    @Property(name = "micronaut.security.token.jwt.signatures.secret.generator.secret")
    private String secretKey;

    @Inject
    private UserRepository userRepository;

    public HttpResponse signIn(SignInDto data) {
        UserDto userDto = userRepository.signIn(data);

        return HttpResponse.ok(createJwt(userDto));
    }

    public HttpResponse signUp(SignUpDto data) {
        //проверить наличие пользователя с данным логином
        Long userId = userRepository.getUserByUsername(data.getUsername());

        //если есть, то вернуть ошибку
        if (userId != null) {
            return HttpResponse.badRequest("username already exist");
        }

        //регистрируем пользователя
        userRepository.signUp(data);

        return HttpResponse.ok();
    }

    private JwtDto createJwt(UserDto data) {
        JwtDto jwtDto = new JwtDto();

        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(this.secretKey));

        String jwt = Jwts.builder()
                .claim("sub", "api-gateway")
                .claim("id", data.getId())
                .claim("username", data.getUsername())
                .claim("lastName", data.getLastName())
                .claim("firstName", data.getFirstName())
                .claim("middleName", data.getMiddleName())
                .setIssuer("auth-service")
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        jwtDto.setJwt(jwt);

        return jwtDto;
    }
}
