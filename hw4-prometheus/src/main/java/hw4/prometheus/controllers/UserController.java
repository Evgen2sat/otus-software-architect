package hw4.prometheus.controllers;

import hw4.prometheus.dto.UserDto;
import hw4.prometheus.repositories.UserRepository;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;

import javax.inject.Inject;
import java.sql.SQLException;

@Controller("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserController {

    @Inject
    private UserRepository userRepository;

    @Post
    public UserDto create(@Body UserDto user) throws SQLException {
        return userRepository.create(user);
    }

    @Put("/{id}")
    public UserDto create(long id, @Body UserDto user) throws SQLException {
        return userRepository.update(id, user);
    }

    @Get("/{id}")
    public UserDto get(long id) throws SQLException {
        return userRepository.get(id);
    }

    @Delete("/{id}")
    public void delete(long id) throws SQLException {
        userRepository.delete(id);
    }
}
