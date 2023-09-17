package ra.controller;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ra.model.domain.CartItem;
import ra.model.domain.Product;
import ra.model.domain.Users;
import ra.model.dto.request.CartItemRequest;
 import ra.model.dto.response.CartItemResponse;
import ra.model.dto.response.ProductResponse;
import ra.security.user_principle.UserDetailService;
import ra.service.impl.CartItemService;
import ra.service.impl.ProductService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin("*")
public class CartController {
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private UserDetailService userDetailService;
    @GetMapping("/findAll")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<List<CartItemResponse>> getAllCartItem() {
        Long userId= userDetailService.getUserFromAuthentication().getId();
        List<CartItemResponse> cartItemResponses= (List<CartItemResponse>) cartItemService.findById(userId);
        return new  ResponseEntity<>(cartItemResponses, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<CartItemResponse> findById(@PathVariable Long id ){
        return  new ResponseEntity<>(cartItemService.findById(id),HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<CartItemResponse> create(@RequestBody @Valid CartItemRequest cartItemRequest) {
        return new ResponseEntity<>(cartItemService.save(cartItemRequest),HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<CartItemResponse> update(@RequestBody @Valid CartItemRequest cartItemRequest,@PathVariable Long id){
        return new ResponseEntity<>(cartItemService.update(cartItemRequest,id),HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<CartItemResponse> deleteById(@PathVariable Long id ){
        return  new ResponseEntity<>(cartItemService.delete(id),HttpStatus.OK);
    }
}
