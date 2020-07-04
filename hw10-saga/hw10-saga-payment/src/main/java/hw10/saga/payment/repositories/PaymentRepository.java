package hw10.saga.payment.repositories;

import hw10.saga.payment.database.DatabaseService;
import hw10.saga.payment.database.QueryParam;
import hw10.saga.payment.dto.SendPaymentRequestDto;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.SQLException;

@Singleton
public class PaymentRepository {
    @Inject
    private DatabaseService databaseService;

    public long sendPayment(SendPaymentRequestDto data) throws SQLException {
        return databaseService.executeInsertQueryWithId(
                "INSERT INTO payments\n" +
                        "(order_id, summ)\n" +
                        "VALUES\n" +
                        "(?, ?)",
                QueryParam.getLong(data.getOrderId()),
                QueryParam.getFloat(data.getSumm())
        );
    }

    public void rejectPayment(long paymentId) throws SQLException {
        databaseService.executeInsertQueryWithId(
                "UPDATE payments\n" +
                        "SET\n" +
                        " deleted = ?\n" +
                        "WHERE id = ?",
                QueryParam.getBoolean(true),
                QueryParam.getLong(paymentId)
        );
    }
}
