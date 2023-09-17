package ra.model.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private boolean status;
}
