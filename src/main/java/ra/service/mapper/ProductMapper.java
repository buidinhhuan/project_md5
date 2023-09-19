package ra.service.mapper;

import org.springframework.stereotype.Component;
import ra.model.domain.Category;
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
                .imgUrl_main(String.valueOf(productRequest.getImgUrl()))
                .status(productRequest.isStatus())
                .category( productRequest.getCategory())
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
                .imgUrl(product.getImgUrl_main())
                .status(product.isStatus())
                .category(product.getCategory().getName())
                .build();
    }
}
