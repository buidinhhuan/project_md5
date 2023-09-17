package ra.service;

import ra.model.domain.Order;
import ra.model.dto.request.CartItemRequest;
import ra.model.dto.response.CartItemResponse;
import ra.repository.IOrderRepository;

import java.util.List;
import java.util.Optional;

public interface IOrderService<T> {
    List<T> findAll();
    Optional<T> findById(Long id);
    T save(T t);
    void delete(Long id);

}
