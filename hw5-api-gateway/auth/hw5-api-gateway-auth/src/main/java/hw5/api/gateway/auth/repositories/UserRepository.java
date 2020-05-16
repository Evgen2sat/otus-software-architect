package hw5.api.gateway.auth.repositories;

import hw5.api.gateway.auth.database.DatabaseService;
import hw5.api.gateway.auth.dto.JwtDto;
import hw5.api.gateway.auth.dto.SignInDto;
import hw5.api.gateway.auth.dto.SignUpDto;
import hw5.api.gateway.auth.dto.UserDto;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserRepository {
//    @Inject
//    private DatabaseService databaseService;

    public UserDto signIn(SignInDto data) {
        //проверить что пользователь с логином и паролем есть
        //найти информацию о нем и вернуть

        UserDto userDto = new UserDto();
        userDto.setId(1);
        userDto.setUsername("my username");
        userDto.setLastName("my lastName");
        userDto.setFirstName("my firstName");
        userDto.setMiddleName("my middleName");

        return userDto;
    }

    public Long signUp(SignUpDto data) {
        //добавить пользователя в БД
        //и вернуть его ид

        return 1L;
    }

    public Long getUserByUsername(String username) {
        //по логину найти пользователя и вернуть его ид
        return 0L;
    }
}
