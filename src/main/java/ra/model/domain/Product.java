package ra.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String productName;
    private double price;
    private int stock;
    private String description;
    private String imgUrl;
    private boolean status;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
