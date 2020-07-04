package hw10.saga.delivery.dto;

import java.time.LocalDateTime;

public class ReserveCourierRequestDto {
    private long orderId;
    private LocalDateTime deliveryDateTime;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getDeliveryDateTime() {
        return deliveryDateTime;
    }

    public void setDeliveryDateTime(LocalDateTime deliveryDateTime) {
        this.deliveryDateTime = deliveryDateTime;
    }
}
