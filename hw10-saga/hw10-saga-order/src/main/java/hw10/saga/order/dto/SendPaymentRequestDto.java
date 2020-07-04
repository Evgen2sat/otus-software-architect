package hw10.saga.order.dto;

public class SendPaymentRequestDto {
    private long orderId;
    private float summ;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public float getSumm() {
        return summ;
    }

    public void setSumm(float summ) {
        this.summ = summ;
    }
}
