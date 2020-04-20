package hw3.postgres.database;

import org.vibur.dbcp.ViburDBCPDataSource;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Singleton;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


public class DatabaseService {
    private ViburDBCPDataSource vibur;

    public DatabaseService() {
        init();
    }

    private void init() {
        vibur = new ViburDBCPDataSource();
        vibur.setDriverClassName(System.getenv("JDBC_DRIVER_CLASS_NAME"));
        vibur.setJdbcUrl(System.getenv("JDBC_URL"));
        vibur.setUsername(System.getenv("JDBC_USER"));
        vibur.setPassword(System.getenv("JDBC_PASSWORD"));

        vibur.start();
    }

    public long executeInsertQueryWithId(String sql, QueryParam...params) throws SQLException {

        try(Connection connection = vibur.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            for(int i = 0; i < params.length; i++) {
                appendQueryParam(i + 1, params[i], statement);
            }

            int rowCount = statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();

            if (rs.next())
                return rs.getLong("id");
            else
                return -1L;

        }
    }

    public long executeInsertQuery(String sql, QueryParam...params) throws SQLException {

        try(Connection connection = vibur.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            for(int i = 0; i < params.length; i++) {
                appendQueryParam(i + 1, params[i], statement);
            }

            int rowCount = statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();

            if (rs.next())
                return rs.getLong("id");
            else
                return -1L;

        }
    }

    public <T> List<T> executeSelectQuery(
            String sql, Function<ResultSet, T> mapper, QueryParam...params) throws SQLException {

        try(Connection connection = vibur.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            for(int i = 0; i < params.length; i++) {
                appendQueryParam(i + 1, params[i], statement);
            }

            ResultSet rs = statement.executeQuery();

            List<T> result = new ArrayList<>();

            while(rs.next()) {

                result.add(mapper.apply(rs));

            }

            return result;
        }
    }

    public <T> T executeSelectItemQuery(
            String sql, Function<ResultSet, T> mapper, QueryParam...params) throws SQLException {

        try(Connection connection = vibur.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            for(int i = 0; i < params.length; i++) {
                appendQueryParam(i + 1, params[i], statement);
            }

            ResultSet rs = statement.executeQuery();

            return mapper.apply(rs);
        }
    }

    public Long idMapper(ResultSet rs) {
        try {
            return rs.getLong("id");
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка маппинга идентификатора", e);
        }
    }

    private void appendQueryParam(int index, QueryParam param, PreparedStatement statement) throws SQLException {

        if (param.getValue() == null) {
            statement.setNull(index, param.getType());
        } else {
            statement.setObject(index, param.getValue(), param.getType());
        }

    }

    @PreDestroy
    public void clean() {
        vibur.close();
    }
}
