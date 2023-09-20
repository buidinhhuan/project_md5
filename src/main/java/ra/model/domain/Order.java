package ra.model.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String receiverName;
    private String address;
    private String phone;
    private Date date;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private Users users;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "order")
    @JsonIgnore
    private List<OrderDetail> orderDetails= new ArrayList<>();
    private double totalAmount;
    @ManyToOne
    @JoinColumn(name = "orderStatusName_id")
    private OrderStatusName orderStatusNames;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", receiverName='" + receiverName + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", date=" + date +
                ", users=" + users +
                ", orderDetails=" + orderDetails +
                ", totalAmount=" + totalAmount +
                ", orderStatusNames=" + orderStatusNames +
                '}';
    }
}
