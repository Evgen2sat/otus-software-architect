package hw9.api.services;

import hw9.api.dto.ErrorDto;
import hw9.api.dto.OrderDto;
import hw9.api.repositories.OrderRepository;
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
                orderRepository.createOrder(data, orderUuid);
            } else {
                //иначе вернуть конфликт
                return HttpResponse.status(HttpStatus.CONFLICT);
            }

            return getOrderUuid();
        } catch (Exception e) {
            return HttpResponse.serverError();
        }
    }

    public HttpResponse getOrderUuid() {
        MutableHttpResponse<Object> response = HttpResponse.ok();
        response.getHeaders().add("X-OrderUUID", generateOrderUuid());

        return response;
    }

    private String generateOrderUuid() {
        return UUID.randomUUID().toString();
    }
}
