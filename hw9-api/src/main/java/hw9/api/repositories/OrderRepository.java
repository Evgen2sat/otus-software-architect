package hw9.api.repositories;

import hw9.api.database.DatabaseService;
import hw9.api.database.QueryParam;
import hw9.api.dto.OrderDto;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Singleton
public class OrderRepository {
    @Inject
    private DatabaseService databaseService;

    public boolean checkOrderUuid(String orderUuid) throws SQLException {
        List<Long> ids = databaseService.executeSelectQuery(
                "SELECT id\n" +
                        "FROM \"orders\"\n" +
                        "WHERE uuid = ?",
                this::checkOrderUuidMapper,
                QueryParam.getString(orderUuid)
        );

        if(ids != null && !ids.isEmpty()) {
            return true;
        }

        return false;
    }

    public long createOrder(OrderDto data, String orderUuid) throws SQLException {
        return databaseService.executeInsertQueryWithId(
                "INSERT INTO \"orders\"\n" +
                        "(product_id, user_id, product_count, uuid)\n" +
                        "VALUES\n" +
                        "(?, ?, ?, ?)",
                QueryParam.getLong(data.getProductId()),
                QueryParam.getLong(data.getUserId()),
                QueryParam.getInt(data.getProductCount()),
                QueryParam.getString(orderUuid)
        );
    }

    private Long checkOrderUuidMapper(ResultSet rs) {
        try {
            long id = rs.getLong("id");
            if(rs.wasNull()) {
                return null;
            }

            return id;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
