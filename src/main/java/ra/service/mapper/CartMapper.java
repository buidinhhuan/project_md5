package ra.service.mapper;

import ra.model.domain.CartItem;
import ra.model.dto.request.CartRequest;
import ra.model.dto.response.CartResponse;
import ra.service.IGenericMapper;

public class CartMapper implements IGenericMapper<CartItem, CartRequest, CartResponse> {
    @Override
    public CartItem toEntity(CartRequest cartRequest) {
        return CartItem.builder()
                .product(cartRequest.getProduct())
                .quantity(cartRequest.getQuantity())
                .users(cartRequest.getUsers())
                .build();
    }

    @Override
    public CartResponse toResponse(CartItem cartItem) {
        return CartResponse.builder()
                .id(cartItem.getId())
                .product(cartItem.getProduct())
                .quantity(cartItem.getQuantity())
                .users(cartItem.getUsers())
                .build();
    }
}
