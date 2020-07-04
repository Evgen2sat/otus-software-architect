package hw10.saga.delivery.repositories;

import hw10.saga.delivery.database.DatabaseService;
import hw10.saga.delivery.database.QueryParam;
import hw10.saga.delivery.dto.ReserveCourierRequestDto;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.SQLException;

@Singleton
public class DeliveryRepository {
    @Inject
    private DatabaseService databaseService;

    public long reserveCourier(ReserveCourierRequestDto data) throws SQLException {
        return databaseService.executeInsertQueryWithId(
                "INSERT INTO deliveries\n" +
                        "(order_id, delivery_date_time)\n" +
                        "VALUES\n" +
                        "(?, ?)",
                QueryParam.getLong(data.getOrderId()),
                QueryParam.getLocalDateTime(data.getDeliveryDateTime())
        );
    }
}
