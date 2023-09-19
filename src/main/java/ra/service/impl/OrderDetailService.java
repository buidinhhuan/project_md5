package ra.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.model.domain.Order;
import ra.model.domain.OrderDetail;
import ra.model.domain.Users;
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
    public Optional<OrderDetail> findById(Users users, Long id) throws EntityExistsException {
        if (!orderDetailRepository.findOrderDetailByOrder_UsersAndId(users,id).isPresent()){
            throw new EntityExistsException("ID OrderDetail not found");
        }
        return orderDetailRepository.findOrderDetailByOrder_UsersAndId(users, id);
    }

}
