package hw4.prometheus.repositories;

import hw4.prometheus.database.DatabaseService;
import hw4.prometheus.database.QueryParam;
import hw4.prometheus.dto.UserDto;

import javax.inject.Singleton;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Singleton
public class UserRepository {

    private DatabaseService databaseService;

    public UserRepository() {
        this.databaseService = new DatabaseService();
    }

    public UserDto create(UserDto userDto) throws SQLException {
        long userId = databaseService.executeInsertQueryWithId(
                "INSERT INTO users\n" +
                        "(last_name, first_name, middle_name)\n" +
                        "VALUES\n" +
                        "(?, ?, ?)\n",
                QueryParam.getString(userDto.getLastName()),
                QueryParam.getString(userDto.getFirstName()),
                (userDto.getMiddleName() != null && !userDto.getMiddleName().isEmpty() ? QueryParam.getString(userDto.getFirstName()) : QueryParam.getStringNull())
        );

        return get(userId);
    }

    public UserDto update(long id, UserDto userDto) throws SQLException {
        databaseService.executeInsertQuery(
                "UPDATE users\n" +
                        "SET\n" +
                        "   last_name = ?,\n" +
                        "   first_name = ?,\n" +
                        "   middle_name = ?\n" +
                        "WHERE id = ?",
                QueryParam.getString(userDto.getLastName()),
                QueryParam.getString(userDto.getFirstName()),
                (userDto.getMiddleName() != null && !userDto.getMiddleName().isEmpty() ? QueryParam.getString(userDto.getFirstName()) : QueryParam.getStringNull()),
                QueryParam.getLong(id)
        );

        return get(id);
    }

    public UserDto get(long userId) throws SQLException {
        List<UserDto> users = databaseService.executeSelectQuery(
                "SELECT id, last_name, first_name, middle_name\n" +
                        "FROM users\n" +
                        "WHERE id = ?\n" +
                        "   AND deleted = FALSE",
                this::userMapper,
                QueryParam.getLong(userId)
        );

        if(users != null && !users.isEmpty()) {
            return users.get(0);
        }

        return null;
    }

    public void delete(long id) throws SQLException {
        databaseService.executeInsertQuery(
                "UPDATE users\n" +
                        "SET\n" +
                        "   deleted = TRUE\n" +
                        "WHERE id = ?",
                QueryParam.getLong(id)
        );
    }

    private UserDto userMapper(ResultSet rs) {
        try {
            UserDto userDto = new UserDto();
            userDto.setId(rs.getLong("id"));
            userDto.setLastName(rs.getString("last_name"));
            userDto.setFirstName(rs.getString("first_name"));
            userDto.setMiddleName(rs.getString("middle_name"));

            return userDto;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
