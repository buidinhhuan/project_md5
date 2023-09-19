package ra.service.mapper;

import org.springframework.stereotype.Component;
import ra.model.domain.Order;
 import ra.model.dto.request.OrderRequest;
import ra.model.dto.response.OrderResponse;
import ra.service.IGenericMapper;
@Component
public class OrderMapper implements IGenericMapper<Order, OrderRequest, OrderResponse> {
    @Override
    public Order toEntity(OrderRequest orderRequest) {
        return Order.builder()
                .receiverName(orderRequest.getReceiverName())
                .phone(orderRequest.getPhone())
                .address(orderRequest.getAddress())
                .build();
    }

    @Override
    public OrderResponse toResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .receiverName(order.getReceiverName())
                .phone(order.getPhone())
                .address(order.getAddress())
                .date(order.getDate())
                .users(order.getUsers())
                .orderDetails(order.getOrderDetails())
                .totalAmount(order.getTotalAmount())
                .orderStatusName(order.getOrderStatusNames())
                .build();
    }
}
