package ra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.model.domain.OrderStatusName;

public interface IOrderStatusNameRepository extends JpaRepository<OrderStatusName, Long> {
}
