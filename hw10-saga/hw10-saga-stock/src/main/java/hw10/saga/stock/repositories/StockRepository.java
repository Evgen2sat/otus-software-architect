package hw10.saga.stock.repositories;

import hw10.saga.stock.database.DatabaseService;
import hw10.saga.stock.database.QueryParam;
import hw10.saga.stock.dto.ReserveProductsRequestDto;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.SQLException;

@Singleton
public class StockRepository {
    @Inject
    private DatabaseService databaseService;

    public long reserveProducts(ReserveProductsRequestDto data) throws SQLException {
        return databaseService.executeInsertQueryWithId(
                "INSERT INTO stocks\n" +
                        "(order_id, product_id, product_count, payment_id)\n" +
                        "VALUES\n" +
                        "(?, ?, ?, ?)",
                QueryParam.getLong(data.getOrderId()),
                QueryParam.getLong(data.getProductId()),
                QueryParam.getInt(data.getProductCount()),
                QueryParam.getLong(data.getPaymentId())
        );
    }

    public void rejectReserveProducts(long reserveId) throws SQLException {
        databaseService.executeInsertQueryWithId(
                "UPDATE stocks\n" +
                        "SET\n" +
                        " deleted = ?\n" +
                        "WHERE id = ?",
                QueryParam.getBoolean(true),
                QueryParam.getLong(reserveId)
        );
    }
}
