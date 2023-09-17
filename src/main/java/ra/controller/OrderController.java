package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ra.model.domain.Order;
import ra.model.domain.OrderDetail;
import ra.model.domain.Users;
import ra.security.user_principle.UserDetailService;
import ra.service.impl.OrderDetailService;
import ra.service.impl.OrderService;

import javax.persistence.EntityExistsException;
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
      List<Order> orders =users.getOrders();
      return new ResponseEntity<>(orders,HttpStatus.OK);
    }



}
