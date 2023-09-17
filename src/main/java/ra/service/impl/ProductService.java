package ra.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.model.domain.Product;
import ra.model.dto.request.CategoryRequest;
import ra.model.dto.request.ProductRequest;
 import ra.model.dto.response.ProductResponse;
import ra.repository.IProductRepository;
import ra.service.IProductService;
import ra.service.mapper.ProductMapper;

import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ProductService implements IProductService {
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<ProductResponse> findAll() {
        return productRepository.findAll().stream()
                .map(c->productMapper.toResponse(c))
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse findById(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()){
            return productMapper.toResponse(optionalProduct.get());
        }
        return null;
    }

    @Override
    public ProductResponse save(ProductRequest productRequest)  throws EntityExistsException {
        if (productRepository.existsProductByProductName(productRequest.getProductName())){
            throw  new EntityExistsException("Product is exist");
        }
        Product category = productRepository.save(productMapper.toEntity(productRequest));

        return productMapper.toResponse(category);
    }

    @Override
    public ProductResponse update(ProductRequest productRequest, Long id) throws EntityExistsException {
        Product product =productMapper.toEntity(productRequest);
        if (!productRepository.findById(id).isPresent()){
            throw new EntityExistsException("ID Product not found");
        }
        product.setId(id);
        return productMapper.toResponse(productRepository.save(product));
    }

    @Override
    public ProductResponse delete(Long id) throws EntityExistsException{
        Optional<Product> productOptional = productRepository.findById(id);
        if (!productOptional.isPresent()){
            throw new EntityExistsException("ID Product not found");
        } else
        if (productOptional.isPresent()){
            productRepository.deleteById(id);
            return productMapper.toResponse(productOptional.get());
        }
        return null;
    }
}
