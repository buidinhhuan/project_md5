package ra.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
 import ra.model.domain.Product;
import ra.model.domain.Users;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemRequest {
    private Product product;
    private int quantity;
    private Users users;

}
