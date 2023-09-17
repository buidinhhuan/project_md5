package ra.service;

import ra.model.dto.request.CartItemRequest;
import ra.model.dto.response.CartItemResponse;

import java.util.List;

public interface ICartItemService {
    List<CartItemResponse> findAll();
    CartItemResponse findById(Long id);
    CartItemResponse save(CartItemRequest cartItemRequest) ;
    CartItemResponse update(CartItemRequest cartItemRequest, Long id);
    CartItemResponse delete(Long id);

}
