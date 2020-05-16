package hw5.api.gateway.auth.repositories;

import hw5.api.gateway.auth.database.DatabaseService;
import hw5.api.gateway.auth.database.QueryParam;
import hw5.api.gateway.auth.dto.JwtDto;
import hw5.api.gateway.auth.dto.SignInDto;
import hw5.api.gateway.auth.dto.SignUpDto;
import hw5.api.gateway.auth.dto.UserDto;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Singleton
public class UserRepository {
    @Inject
    private DatabaseService databaseService;

    public UserDto signIn(SignInDto data) throws SQLException {
        //найти информацию о пользователе с логином и паролем
        //и вернуть ее

        List<UserDto> users = databaseService.executeSelectQuery(
                "SELECT id, username, last_name, first_name, middle_name\n" +
                        "FROM users\n" +
                        "WHERE deleted = FALSE\n" +
                        "   AND username = ?\n" +
                        "   AND password = ?",
                this::signInMapper,
                QueryParam.getString(data.getUsername()),
                QueryParam.getString(data.getPassword())
        );

        if(users != null && !users.isEmpty()) {
            return users.get(0);
        }

        return null;
    }

    public UserDto signUp(SignUpDto data) throws SQLException {
        //добавить пользователя в БД
        long userId = databaseService.executeInsertQueryWithId(
                "INSERT INTO users\n" +
                        "(username, password, last_name, first_name, middle_name)\n" +
                        "VALUES\n" +
                        "(?, ?, ?, ?, ?)",
                QueryParam.getString(data.getUsername()),
                QueryParam.getString(data.getPassword()),
                QueryParam.getString(data.getLastName()),
                QueryParam.getString(data.getFirstName()),
                QueryParam.getString(data.getMiddleName())
        );

        return getUserById(userId);
    }

    public UserDto getUserByUsername(String username) throws SQLException {
        List<UserDto> users = databaseService.executeSelectQuery(
                "SELECT id, username, last_name, first_name, middle_name\n" +
                        "FROM users\n" +
                        "WHERE deleted = FALSE\n" +
                        "   AND username = ?\n",
                this::signInMapper,
                QueryParam.getString(username)
        );

        if(users != null && !users.isEmpty()) {
            return users.get(0);
        }

        return null;
    }

    public UserDto getUserById(long id) throws SQLException {
        List<UserDto> users = databaseService.executeSelectQuery(
                "SELECT id, username, last_name, first_name, middle_name\n" +
                        "FROM users\n" +
                        "WHERE deleted = FALSE\n" +
                        "   AND id = ?\n",
                this::signInMapper,
                QueryParam.getLong(id)
        );

        if(users != null && !users.isEmpty()) {
            return users.get(0);
        }

        return null;
    }

    private UserDto signInMapper(ResultSet rs) {
        try {
            UserDto result = new UserDto();
            result.setId(rs.getLong("id"));
            result.setUsername(rs.getString("username"));
            result.setLastName(rs.getString("last_name"));
            result.setFirstName(rs.getString("first_name"));
            result.setMiddleName(rs.getString("middle_name"));

            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
