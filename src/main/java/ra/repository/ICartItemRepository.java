package ra.repository;
import org.springframework.data.jpa.repository.JpaRepository;
 import ra.model.domain.CartItem;

public interface ICartItemRepository extends JpaRepository<CartItem,Long> {
  boolean existsCartItemById(Long CartItemId);
    CartItem findByUserIdAndProductId(Long userId, Long productId);

}
