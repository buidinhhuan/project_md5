package ra.service;

import ra.model.domain.Product;
import ra.model.domain.Users;
import ra.model.dto.request.CategoryRequest;
import ra.model.dto.request.FormSignUpDto;
import ra.model.dto.request.ProductRequest;
import ra.model.dto.response.CategoryResponse;
import ra.model.dto.response.ProductResponse;

import java.util.List;
import java.util.Optional;

public interface IProductService{
    List<ProductResponse> findAll();
    ProductResponse findById(Long id);
    ProductResponse save(ProductRequest productRequest) ;
    ProductResponse update(ProductRequest productRequest,Long id);
    ProductResponse delete(Long id);

}
