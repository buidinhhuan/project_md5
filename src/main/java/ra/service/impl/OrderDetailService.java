package ra.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.model.domain.Order;
import ra.model.domain.OrderDetail;
import ra.repository.IOrderDetailRepository;
import ra.repository.IOrderRepository;
import ra.service.IOrderDetailService;
import ra.service.IOrderService;

import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailService implements IOrderDetailService<OrderDetail> {
    @Autowired
    private IOrderDetailRepository orderDetailRepository;

    @Override
    public Optional<OrderDetail> findById(Long id) throws EntityExistsException {
        if (!orderDetailRepository.findById(id).isPresent()){
            throw new EntityExistsException("ID OrderDetail not found");
        }
        return orderDetailRepository.findById(id);
    }

}
