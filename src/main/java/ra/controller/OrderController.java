package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ra.model.domain.CartItem;
import ra.model.domain.Order;
import ra.model.domain.OrderDetail;
import ra.model.domain.Users;
import ra.model.dto.request.OrderRequest;
import ra.model.dto.response.OrderResponse;
import ra.security.user_principle.UserDetailService;
import ra.service.impl.OrderDetailService;
import ra.service.impl.OrderService;

import javax.persistence.EntityExistsException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserDetailService userDetailService;

    @GetMapping("/findAll")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> findAllOrder() {
        List<Order> listOrder = orderService.findAll();
        return new ResponseEntity<>(listOrder, HttpStatus.OK);
    }

    @GetMapping("/findAllByUser")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<?> findAllByUser() {
        Users users = userDetailService.getUserFromAuthentication();
        List<Order> orders = users.getOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PostMapping("/checkOut")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<?> checkOut(@ModelAttribute OrderRequest orderRequest) {
        Users users = userDetailService.getUserFromAuthentication();
        List<OrderResponse> orderResponses = Collections.singletonList(orderService.checkOut(users, users.getCartItem(), orderRequest));
        return new ResponseEntity<>(orderResponses, HttpStatus.OK);
    }

    @PutMapping("/cancelled/{id}")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<?> cancelled(@PathVariable Long id) {
        Users users = userDetailService.getUserFromAuthentication();
        try {
            Order orderCancelled = orderService.cancelled(users, id);
            return new ResponseEntity<>(orderCancelled, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/confirm/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> confirmOrder(@PathVariable Long id) {
        Users users = userDetailService.getUserFromAuthentication();
        try {
            Order orderConfirm = orderService.confirmOrder(users, id);
            return new ResponseEntity<>(orderConfirm, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
