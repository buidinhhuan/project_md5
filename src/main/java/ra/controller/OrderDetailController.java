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
@RequestMapping("api/orderDetail")
public class OrderDetailController {
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private UserDetailService userDetailService;
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> OrderDetail(@PathVariable("id") Long id) throws EntityExistsException {
        Users users = userDetailService.getUserFromAuthentication();
        Optional<OrderDetail> orderDetailOptional = orderDetailService.findById(users,id);
        if (!orderDetailOptional.isPresent()) {
            throw  new  EntityExistsException("id oderDetail not found");
        }
         return new ResponseEntity<>(orderDetailOptional, HttpStatus.OK);
    }

}
