package ra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ra.model.domain.Order;
import ra.model.domain.OrderDetail;

import java.util.List;

public interface IOrderDetailRepository extends JpaRepository<OrderDetail,Long> {

}
