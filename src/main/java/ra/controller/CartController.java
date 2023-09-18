package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ra.model.domain.CartItem;
import ra.model.domain.Order;
import ra.model.domain.Product;
import ra.model.domain.Users;
 import ra.security.user_principle.UserDetailService;
import ra.service.impl.CartItemService;
import ra.service.impl.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin("*")
public class CartController {
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private UserDetailService userDetailService;
    @Autowired
    private ProductService productService;

    @GetMapping("/findAll")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<?> findAllByUser() {
        Users users = userDetailService.getUserFromAuthentication();
        List<CartItem> cartItems = users.getCartItem();
        return new ResponseEntity<>(cartItems, HttpStatus.OK);
    }

    @PostMapping("/addToCart/{productId}")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<?> addToCart(@PathVariable Long productId) {
        Users users = userDetailService.getUserFromAuthentication();
        Product product = productService.findByIdProduct(productId);
        if (product == null) {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }else {
        cartItemService.addToCart(users, product);
        return new ResponseEntity<>("Product added to cart", HttpStatus.OK);
        }
    }

    @PutMapping("/updateQuantity/{productId}/{newQuantity}")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<?> updateProductQuantity(@PathVariable Long productId, @PathVariable Integer newQuantity) {
        Users user = userDetailService.getUserFromAuthentication();

        // Sử dụng productId để lấy thông tin sản phẩm từ cơ sở dữ liệu
        Product product = productService.findByIdProduct(productId);

        if (product == null) {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }

        // Gọi phương thức để thay đổi số lượng sản phẩm trong giỏ hàng của người dùng
        cartItemService.updateProductQuantity(user, product, newQuantity);
        return new ResponseEntity<>("update thành công",HttpStatus.OK);
     }

    @GetMapping("/productQuantity")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<Integer> countProductQuantity() {
        Users user = userDetailService.getUserFromAuthentication();
        Integer productQuantity = cartItemService.countProductQuantity(user);
        return new ResponseEntity<>(productQuantity, HttpStatus.OK);
    }

    @GetMapping("/itemQuantity")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<Long> countItemQuantity() {
        Users user = userDetailService.getUserFromAuthentication();
        Long itemQuantity = cartItemService.countItemQuantity(user);
        return new ResponseEntity<>(itemQuantity, HttpStatus.OK);
    }

    @GetMapping("/totalPayment")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<Float> countTotalPayment() {
        Users user = userDetailService.getUserFromAuthentication();
        Float totalPayment = cartItemService.countTotalPayment(user);
        return new ResponseEntity<>(totalPayment, HttpStatus.OK);
    }


}
