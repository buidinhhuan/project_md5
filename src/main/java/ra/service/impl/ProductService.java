package ra.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ra.model.domain.ImgProduct;
import ra.model.domain.Product;
import ra.model.dto.request.ProductRequest;
import ra.model.dto.response.ProductResponse;
import ra.repository.IProductRepository;
import ra.service.IProductService;
import ra.service.mapper.ProductMapper;

import javax.persistence.EntityExistsException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ProductService implements IProductService {
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private StorageService storageService;

    @Override
    public List<ProductResponse> findAll() {
        List<Product> products = productRepository.findAll();
        for (Product product : products) {
            if (product.getStock() <= 0) {
                product.setStatus(false);
                productRepository.save(product);
            }
        }
        return products.stream()
                .map(productMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse findById(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        return optionalProduct.map(productMapper::toResponse).orElse(null);
    }

    @Override
    public Product findByIdProduct(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        return optionalProduct.orElse(null);
    }

    @Override
    public ProductResponse save(ProductRequest productRequest) throws EntityExistsException {
        if (productRepository.existsProductByProductName(productRequest.getProductName())) {
            throw new EntityExistsException("Product is exist");
        }

        Product product = productMapper.toEntity(productRequest);

        List<String> listUrl = new ArrayList<>();
        for (MultipartFile multipartFile : productRequest.getImgUrl()) {
            String imageUrl = storageService.uploadFile(multipartFile);
            listUrl.add(imageUrl);
        }

        if (!listUrl.isEmpty()) {
            // Set ảnh chính (main image) thành ảnh đầu tiên trong danh sách
            product.setImgUrl_main(listUrl.get(0));

            // Tạo danh sách ảnh sản phẩm
            List<ImgProduct> images = listUrl.stream()
                    .map(imageUrl -> ImgProduct.builder().image(imageUrl).product(product).build())
                    .collect(Collectors.toList());

            // Gán danh sách ảnh sản phẩm vào sản phẩm
            product.setImages(images);
        }

        return productMapper.toResponse(productRepository.save(product));
    }

    @Override
    public ProductResponse update(ProductRequest productRequest, Long id) throws EntityExistsException {
        Optional<Product> existingProductOptional = productRepository.findById(id);

        if (!existingProductOptional.isPresent()) {
            throw new EntityExistsException("ID Product not found");
        }
        Product existingProduct = existingProductOptional.get();
        Product updatedProduct = productMapper.toEntity(productRequest);

        // Copy thông tin từ updatedProduct vào existingProduct
        existingProduct.setProductName(updatedProduct.getProductName());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setStock(updatedProduct.getStock());
        existingProduct.setStatus(updatedProduct.isStatus());
        existingProduct.setDescription(updatedProduct.getDescription());

        // Kiểm tra xem có ảnh mới được gửi hay không
        if (!productRequest.getImgUrl().isEmpty()) {
            // Copy ảnh mới vào sản phẩm
            List<String> listUrl = new ArrayList<>();
            for (MultipartFile multipartFile : productRequest.getImgUrl()) {
                String imageUrl = storageService.uploadFile(multipartFile);
                listUrl.add(imageUrl);
            }
            // Set ảnh chính (main image) thành ảnh đầu tiên trong danh sách
            existingProduct.setImgUrl_main(listUrl.get(0));

            // Tạo danh sách ảnh sản phẩm
            List<ImgProduct> images = listUrl.stream()
                    .map(imageUrl -> ImgProduct.builder().image(imageUrl).product(existingProduct).build())
                    .collect(Collectors.toList());

            // Gán danh sách ảnh sản phẩm vào sản phẩm
            existingProduct.setImages(images);
        }

        return productMapper.toResponse(productRepository.save(existingProduct));
    }

    public ProductResponse delete(Long id) throws EntityExistsException {
        Optional<Product> productOptional = productRepository.findById(id);

        if (!productOptional.isPresent()) {
            throw new EntityExistsException("ID Product not found");
        } else {
            productRepository.deleteById(id);
            return productMapper.toResponse(productOptional.get());
        }
    }
}

