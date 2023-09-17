package ra.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.model.domain.Category;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    @Column(unique = true)
    private String productName;
    private double price;
    private int stock;
    private String description;
    private String imgUrl;
    private boolean status;
    private Category category;
}
