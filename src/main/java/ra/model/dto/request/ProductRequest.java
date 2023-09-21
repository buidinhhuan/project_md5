package ra.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import ra.model.domain.Category;

import javax.persistence.Column;
import java.util.List;

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
     private List<MultipartFile> imgUrl;
    private boolean status;
    private Long categoryId;
}
