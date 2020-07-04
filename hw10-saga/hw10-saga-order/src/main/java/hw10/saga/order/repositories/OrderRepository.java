package hw10.saga.order.repositories;

import hw10.saga.order.database.DatabaseService;
import hw10.saga.order.database.QueryParam;
import hw10.saga.order.dto.OrderDto;

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
                        "(product_id, user_id, product_count, uuid, summ)\n" +
                        "VALUES\n" +
                        "(?, ?, ?, ?, ?)",
                QueryParam.getLong(data.getProductId()),
                QueryParam.getLong(data.getUserId()),
                QueryParam.getInt(data.getProductCount()),
                QueryParam.getString(orderUuid),
                QueryParam.getFloat(data.getPrice() * data.getProductCount())
        );
    }

    public void updateStatus(long orderId, String status) throws SQLException {
        databaseService.executeInsertQuery(
                "UPDATE \"orders\"\n" +
                        "SET status = ?\n" +
                        "WHERE id = ?",
                QueryParam.getString(status),
                QueryParam.getLong(orderId)
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
