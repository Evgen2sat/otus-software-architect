package hw10.saga.order.services;

import hw10.saga.order.dto.*;
import hw10.saga.order.externalServices.DeliveryService;
import hw10.saga.order.externalServices.PaymentService;
import hw10.saga.order.externalServices.StockService;
import hw10.saga.order.repositories.OrderRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MutableHttpResponse;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.UUID;

@Singleton
public class OrderService {

    @Inject
    private OrderRepository orderRepository;

    @Inject
    private PaymentService paymentService;

    @Inject
    private StockService stockService;

    @Inject
    private DeliveryService deliveryService;

    public HttpResponse create(String orderUuid, OrderDto data) {
        try {
            if(orderUuid == null || orderUuid.trim().isEmpty()) {
                ErrorDto errorDto = new ErrorDto();
                errorDto.setMessage("В заголовке отсутствует параметр X-OrderUUID");
                return HttpResponse.badRequest(errorDto);
            }

            //проверить orderUuid
            if(!orderRepository.checkOrderUuid(orderUuid)) {
                //если отсутствует, то продолжить сохранение
                return getOrderUuid(createOrderSaga(orderUuid, data));
            } else {
                //иначе вернуть конфликт
                return HttpResponse.status(HttpStatus.CONFLICT);
            }


        } catch (Exception e) {
            return HttpResponse.serverError();
        }
    }

    public HttpResponse getOrderUuid(OrderResponseDto orderSaga) {
        MutableHttpResponse<Object> response = HttpResponse.ok();
        response.getHeaders().add("X-OrderUUID", generateOrderUuid());

        if(orderSaga != null) {
            response.body(orderSaga);
        }

        return response;
    }

    private String generateOrderUuid() {
        return UUID.randomUUID().toString();
    }

    private OrderResponseDto createOrderSaga(String orderUuid, OrderDto data) throws Exception {
        OrderResponseDto result = new OrderResponseDto();

        //создать заказ
        long orderId = orderRepository.createOrder(data, orderUuid);
        result.setId(orderId);

        //отправить платеж
        SendPaymentResponseDto paymentResponse = paymentService.sendPayment(orderId, data.getPrice() * data.getProductCount());
        if(paymentResponse.getError() != null) {
            orderRepository.updateStatus(orderId, "CANCELLED");
            result.setStatus("CANCELLED");
            return result;
        }

        //зарезервировать товар на складе
        ReserveProductsResponseDto reserveProductsResponse = stockService.reserveProducts(orderId, data.getProductId(), data.getProductCount(), paymentResponse.getPaymentId());
        if(reserveProductsResponse.getError() != null) {
            paymentService.rejectPayment(paymentResponse.getPaymentId());
            orderRepository.updateStatus(orderId, "CANCELLED");
            result.setStatus("CANCELLED");
            return result;
        }

        //зарезервировать курьера на определенный слот времени
        ReserveCourierResponseDto reserveCourierResponse = deliveryService.reserveCourier(orderId, data.getDeliveryDateTime());
        if(reserveCourierResponse.getError() != null) {
            stockService.rejectReserveProducts(reserveProductsResponse.getReserveId());
            paymentService.rejectPayment(paymentResponse.getPaymentId());
            orderRepository.updateStatus(orderId, "CANCELLED");
            result.setStatus("CANCELLED");
            return result;
        }

        //обновить статус заказа
        orderRepository.updateStatus(orderId, "APPROVED");
        result.setStatus("APPROVED");

        //вернуть идентификатор созданного заказа
        return result;
    }
}
