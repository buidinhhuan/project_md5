package ra.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import ra.model.domain.*;
import ra.model.dto.request.OrderRequest;
import ra.model.dto.response.OrderResponse;
import ra.repository.IOrderRepository;
import ra.repository.IOrderStatusNameRepository;
import ra.service.IOrderService;
import ra.service.mapper.OrderMapper;

import javax.persistence.EntityExistsException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements IOrderService<Order> {
    @Autowired
    private IOrderRepository orderRepository;
    @Autowired
    private IOrderStatusNameRepository orderStatusNameRepository;
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private MailService mailService;
    @Autowired
    private UserService userService;

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> findById(Long id) throws EntityExistsException {
        if (!orderRepository.findById(id).isPresent()) {
            throw new EntityExistsException("ID Order not found");
        }
        return orderRepository.findById(id);
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public OrderResponse checkOut(Users users, List<CartItem> cartItems, OrderRequest orderRequest) {
        Order order = createOrder(users, cartItems);

        // Thiết lập thông tin đơn hàng từ OrderRequest
        order.setReceiverName(orderRequest.getReceiverName());
        order.setPhone(orderRequest.getPhone());
        order.setAddress(orderRequest.getAddress());
        order.setDate(new Date());
        order.setUsers(users);
        order.setOrderStatusNames(findByIdStatusOder(orderRequest.getOrderStatusName()).get());
        order = save(order);

        // Xóa tất cả sản phẩm trong giỏ hàng khi đã checkOut
        cartItemService.deleteAll(users.getId());

        // Sử dụng mapper để chuyển đổi Order thành OrderResponse
        return orderMapper.toResponse(order);
    }

    public Order cancelled(Users users, Long id) {
        Optional<Order> o = orderRepository.findById(id);
        if (o.isPresent()) {
            Order order = o.get();
            if (order.getOrderStatusNames().getId() == 2) {
                throw new RuntimeException("Đơn hàng đang giao không thể huỷ");
            } else if (order.getOrderStatusNames().getId() == 3) {
                throw new RuntimeException("Đơn hàng đã giao thành công  không thể huỷ");
            }
            order.setOrderStatusNames(findByIdStatusOder(4L).get());
            return orderRepository.save(order);
        } else {
            throw new RuntimeException("Id not found");
        }
    }

    public Order confirmOrder(Users users, Long id) {
        Optional<Order> o = orderRepository.findById(id);
        if (o.isPresent()) {
            Order order = o.get();
            if (order.getOrderStatusNames().getId() == 1) {
                 mailService.sendEmail(order.getUsers().getEmail(),"xác nhận đơn hàng","Đơn hàng của bạn đã được xác nhận");
                order.setOrderStatusNames(findByIdStatusOder(2L).get());
                return orderRepository.save(order);
            } else if (order.getOrderStatusNames().getId() == 2) {
                order.setOrderStatusNames(findByIdStatusOder(3L).get());
                return orderRepository.save(order);
            } else if (order.getOrderStatusNames().getId() == 3) {
                order.setOrderStatusNames(findByIdStatusOder(4L).get());
                return orderRepository.save(order);
            }
        } else {
            throw new RuntimeException("Id not found");
        }
        return null;
    }

    public Order createOrder(Users users, List<CartItem> cartItems) throws EntityExistsException {
        Order order = new Order();
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();
            int orderQuantity = cartItem.getQuantity();
            if (product.getStock() >= orderQuantity) {
                product.setStock(product.getStock() - orderQuantity);
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrder(order);
                orderDetail.setProduct(cartItem.getProduct());
                orderDetail.setQuantity(cartItem.getQuantity());
                orderDetails.add(orderDetail);
            } else {
                throw new EntityExistsException("số lượng sản phẩm " + product.getProductName() + " chỉ còn " + product.getStock() + "sản phẩm");
            }
        }
        order.setOrderDetails(orderDetails);
        double totalAmount = calculateTotalAmount(orderDetails);
        order.setTotalAmount(totalAmount);
        return order;
    }

    private double calculateTotalAmount(List<OrderDetail> orderDetails) {
        double totalAmount = 0;
        for (OrderDetail od : orderDetails) {
            double itemTotal = od.getQuantity() * od.getProduct().getPrice();
            totalAmount += itemTotal;
        }
        return totalAmount;
    }

    @Override
    public Optional<OrderStatusName> findByIdStatusOder(Long id) {
        return orderStatusNameRepository.findById(id);
    }
}




