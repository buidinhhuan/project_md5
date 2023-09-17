package ra.service.mapper;

import org.springframework.stereotype.Component;
import ra.model.domain.CartItem;
import ra.model.dto.request.CartItemRequest;
import ra.model.dto.response.CartItemResponse;
import ra.service.IGenericMapper;
@Component
public class CartItemMapper implements IGenericMapper<CartItem, CartItemRequest, CartItemResponse> {
    @Override
    public CartItem toEntity(CartItemRequest cartItemRequest) {
        return CartItem.builder()
                .product(cartItemRequest.getProduct())
                .quantity(cartItemRequest.getQuantity())
                .users(cartItemRequest.getUsers()).build();
    }

    @Override
    public CartItemResponse toResponse(CartItem cartItem) {
        return CartItemResponse.builder()
                .id(cartItem.getId())
                .product(cartItem.getProduct())
                .quantity(cartItem.getQuantity())
                .users(cartItem.getUsers()).build();
    }
}
