package ra.service.mapper;

import org.springframework.stereotype.Component;
import ra.model.domain.Product;
import ra.model.dto.request.ProductRequest;
import ra.model.dto.response.ProductResponse;
import ra.service.IGenericMapper;
import ra.service.IProductService;
@Component
public class ProductMapper implements IGenericMapper<Product, ProductRequest, ProductResponse> {
    @Override
    public Product toEntity(ProductRequest productRequest) {
        return Product.builder()
                .productName(productRequest.getProductName())
                .price(productRequest.getPrice())
                .stock(productRequest.getStock())
                .description(productRequest.getDescription())
                .imgUrl(productRequest.getImgUrl())
                .status(productRequest.isStatus())
                .category(productRequest.getCategory())
                .build();
    }

    @Override
    public ProductResponse toResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .price(product.getPrice())
                .stock(product.getStock())
                .description(product.getDescription())
                .imgUrl(product.getImgUrl())
                .status(product.isStatus())
                .category(product.getCategory().getName())
                .build();
    }
}
