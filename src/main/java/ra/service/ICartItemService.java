package ra.service;


import ra.model.domain.CartItem;
import ra.model.domain.Product;
import ra.model.domain.Users;

import java.util.List;

public interface ICartItemService {
        List<CartItem> findAll();
        CartItem findById( Long id);
        void addToCart(Users users, Product product );
        void   updateProductQuantity(Users users, Product product,Integer quantity);
         Integer countProductQuantity( Users users);
        Long countItemQuantity( Users users);
        Float countTotalPayment( Users users);
        void delete(Long id);
        void deleteAll(Long id);


}
