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
import java.util.Optional;


@Service
public class CartItemService implements ICartItemService {
    @Autowired
    private ICartItemRepository icartItemRepository;

    @Override
    public List<CartItem> findAll() {
        return icartItemRepository.findAll();
    }

    @Override
    public CartItem findById(Long id) {
        CartItem cartItem = icartItemRepository.findById(id).get();
         return cartItem;
    }

    @Override
    public void addToCart(Users user, Product product)  throws EntityExistsException{
        CartItem cartItem = icartItemRepository.findByUsersAndProduct(user, product);
        if (product.isStatus()==false || product.getStock()<=0){
            throw  new EntityExistsException("sản phẩm đã hết hàng");
        }
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
            throw new EntityExistsException("Số lượng sản phẩm đã được cập nhật");
        } else {
            throw new EntityExistsException("không thấy sản phẩm trong giỏ hàng");
        }
    }

    @Override
    public void deleteAll(Long id) {
        icartItemRepository.deleteAllByUsersId(id);
    }

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

    @Override
    public void delete(Long id) {
        icartItemRepository.deleteById(id);
    }
}
