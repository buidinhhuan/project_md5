package ra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ra.model.domain.Order;
import ra.model.domain.OrderDetail;
import ra.model.domain.Users;

import java.util.List;
import java.util.Optional;

public interface IOrderDetailRepository extends JpaRepository<OrderDetail,Long> {

    Optional<OrderDetail> findOrderDetailByOrder_UsersAndId(Users users, Long aLong);
}
