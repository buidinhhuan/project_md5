package ra.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ra.model.domain.CartItem;
import ra.model.domain.Product;
import ra.model.domain.Users;
import ra.model.dto.response.ProductResponse;

import javax.transaction.Transactional;

@Repository
public interface ICartItemRepository extends JpaRepository<CartItem,Long> {
//  boolean existsCartItemById(Long CartItemId);
    // Tìm cartItem dựa trên user và product
     CartItem findByUsersAndProduct(Users user, Product product);
      //xoa 1 sản phẩm trong giỏ hàng
     void deleteById(Long cartItemId);
    @Modifying
    @Transactional
     @Query(value = "delete\n" +
             "from cart_item\n" +
             "where user_id = :uID",nativeQuery = true)
     void deleteAllByUsersId (@Param("uID") Long uID);

    //tổng số lượng sản phẩm trong giỏ hàng của một User
    @Query("SELECT SUM(ci.quantity) FROM CartItem ci WHERE ci.users = :user")
    Integer sumQuantityByUser(@Param("user") Users user);
    // đếm số lượng đơn hàng của một User
    Long countByUsers(Users user);
    // tính tổng giá trị của các đơn hàng của một User
    @Query("SELECT SUM(ci.quantity * p.price) FROM CartItem ci JOIN ci.product p WHERE ci.users = :user")
    Float sumTotalPaymentByUser(@Param("user") Users user);


}
