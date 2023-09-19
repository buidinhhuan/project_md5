package ra.service;



import ra.model.domain.OrderStatusName;

import java.util.List;
import java.util.Optional;

public interface IOrderService<T> {
    List<T> findAll();
    Optional<T> findById(Long id);
    T save(T t);
    Optional<OrderStatusName> findByIdStatusOder(Long id);

}
