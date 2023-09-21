package ra.repository;

 import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
 import ra.model.domain.Order;

import java.util.List;

public interface IOrderRepository extends JpaRepository<Order,Long> {
 List<Order> findByUsersIdAndOrderStatusNamesId(Long userId, Long StatusId);
 List<Order> findOrdersByOrderStatusNamesId(Long StatusId);
 }
