package hw10.saga.order.dto;

public class SendPaymentResponseDto {
    private Long paymentId;
    private ErrorDto error;

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public ErrorDto getError() {
        return error;
    }

    public void setError(ErrorDto error) {
        this.error = error;
    }
}
