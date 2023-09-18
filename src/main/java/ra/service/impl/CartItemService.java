package ra.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.model.domain.CartItem;
import ra.model.domain.Product;
import ra.model.domain.Users;

import ra.repository.ICartItemRepository;
import ra.service.ICartItemService;

import javax.persistence.EntityExistsException;
import java.util.List;


@Service
public class CartItemService implements ICartItemService {
    @Autowired
    private ICartItemRepository icartItemRepository;

    @Override
    public List<CartItem> findAll() {
        return icartItemRepository.findAll();
    }

    @Override
    public void addToCart(Users user, Product product) {
        CartItem cartItem = icartItemRepository.findByUsersAndProduct(user, product);
        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setUsers(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(1);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        }
        icartItemRepository.save(cartItem);
    }

    @Override
    public void updateProductQuantity(Users user, Product product, Integer newQuantity) throws EntityExistsException {
        CartItem cartItem = icartItemRepository.findByUsersAndProduct(user, product);
        if (cartItem != null) {
            if (newQuantity <= 0) {
                icartItemRepository.deleteById(cartItem.getId());
            } else {
                // Cập nhật số lượng sản phẩm trong CartItem
                cartItem.setQuantity(newQuantity);
                icartItemRepository.save(cartItem);
            }
            throw new EntityExistsException("Product quantity updated");
        } else {
            throw new EntityExistsException("Product not found in cart");
        }

    }



//    public void removeProductInCart(Users user, Product product, Long id) throws EntityExistsException {
//        CartItem cartItem = icartItemRepository.findByUserAndProduct(user, product);
//        if (cartItem == null) {
//            throw new EntityExistsException("ID product not found");
//        }
//        icartItemRepository.deleteById(id);
//    }

    @Override
    public Integer countProductQuantity(Users user) {
        return icartItemRepository.sumQuantityByUser(user);
    }

    @Override
    public Long countItemQuantity(Users user) {
        return icartItemRepository.countByUsers(user);
    }

    @Override
    public Float countTotalPayment(Users user) {
        return icartItemRepository.sumTotalPaymentByUser(user);
    }
}
