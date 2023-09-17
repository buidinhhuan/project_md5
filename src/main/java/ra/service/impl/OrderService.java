package ra.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.model.domain.Order;
import ra.repository.IOrderRepository;
import ra.service.IOrderService;

import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.Optional;
@Service
public class OrderService implements IOrderService<Order> {
    @Autowired
    private IOrderRepository orderRepository;
    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> findById(Long id) throws EntityExistsException {
        if (!orderRepository.findById(id).isPresent()){
            throw  new EntityExistsException("ID Order not found");
        }
        return orderRepository.findById(id);
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public void delete(Long id) throws  EntityExistsException{
        Optional<Order> optionalOrder= orderRepository.findById(id);
        if (!optionalOrder.isPresent()){
            throw new EntityExistsException("ID Order not found");
        }
        orderRepository.deleteById(id);
    }

}
