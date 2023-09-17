package ra.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.model.domain.CartItem;
import ra.model.domain.Users;
import ra.model.dto.request.CartItemRequest;
import ra.model.dto.response.CartItemResponse;
import ra.model.dto.response.CategoryResponse;
import ra.repository.ICartItemRepository;
import ra.service.ICartItemService;
import ra.service.mapper.CartItemMapper;

import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartItemService implements ICartItemService {
    @Autowired
    private ICartItemRepository cartItemRepository;
    @Autowired
    private CartItemMapper cartItemMapper;
    @Override
    public List<CartItemResponse> findAll() {
        return cartItemRepository.findAll().stream()
                .map(c -> cartItemMapper.toResponse(c))
                .collect(Collectors.toList());
    }

    @Override
    public CartItemResponse findById(Long id) {
        Optional<CartItem> optionalCartItem = cartItemRepository.findById(id);
        if (optionalCartItem.isPresent()){
            return cartItemMapper.toResponse(optionalCartItem.get());
        }
        return null;
    }

    @Override
    public CartItemResponse save(CartItemRequest cartItemRequest)  {
        if (cartItemRepository.existsCartItemById(cartItemRequest.getUsers().getId())) {
            cartItemRequest.setQuantity(cartItemRequest.getQuantity()+1);
        }
        CartItem cartItem = cartItemRepository.save(cartItemMapper.toEntity(cartItemRequest));

        return cartItemMapper.toResponse(cartItem);
    }

    @Override
    public CartItemResponse update(CartItemRequest cartItemRequest, Long id)  {
        CartItem cartItem = cartItemMapper.toEntity(cartItemRequest);
        if (!cartItemRepository.findById(id).isPresent()){
            throw new EntityExistsException("ID Cart not found");
        }
        cartItem.setId(id);
        return cartItemMapper.toResponse(cartItemRepository.save(cartItem));
    }

    @Override
    public CartItemResponse delete(Long id) {
        Optional<CartItem> cartItemOptional = cartItemRepository.findById(id);
        if (!cartItemOptional.isPresent()){
            throw new EntityExistsException("ID Cart not found");
        } else
        if (cartItemOptional.isPresent()){
            cartItemRepository.deleteById(id);
            return cartItemMapper.toResponse(cartItemOptional.get());
        }
        return null;
    }

//    public CartItemResponse addToCart(CartItemRequest cartItemRequest, Users currentUser) {
//        // Kiểm tra xem sản phẩm đã tồn tại trong giỏ hàng của người dùng chưa
//        CartItem existingCartItem = cartItemRepository.findByUserIdAndProductId(currentUser.getId(), cartItemRequest.getProduct().getId());
//
//        if (existingCartItem != null) {
//            // Nếu sản phẩm đã tồn tại trong giỏ hàng, cập nhật số lượng
//            int updatedQuantity = existingCartItem.getQuantity() + cartItemRequest.getQuantity();
//            existingCartItem.setQuantity(updatedQuantity);
//            return update(cartItemRequest, existingCartItem.getId());
//        } else {
//            // Nếu sản phẩm chưa tồn tại trong giỏ hàng, tạo một mục mới
//            cartItemRequest.setUsers(currentUser.getId());
//            return save(cartItemRequest);
//        }
//    }
}
