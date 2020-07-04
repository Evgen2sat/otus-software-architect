package hw10.saga.order.dto;

public class ReserveProductsResponseDto {
    private Long reserveId;
    private ErrorDto error;

    public Long getReserveId() {
        return reserveId;
    }

    public void setReserveId(Long reserveId) {
        this.reserveId = reserveId;
    }

    public ErrorDto getError() {
        return error;
    }

    public void setError(ErrorDto error) {
        this.error = error;
    }
}
