package ra.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.model.domain.Category;
import ra.model.domain.Product;
import ra.model.dto.request.CategoryRequest;
import ra.model.dto.response.CategoryResponse;
import ra.repository.ICategoryRepository;
import ra.repository.IProductRepository;
import ra.service.ICategoryService;
import ra.service.mapper.CategoryMapper;
import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService implements ICategoryService {
    @Autowired
    private ICategoryRepository categoryRepository;
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<CategoryResponse> findAll() {
        return categoryRepository.findAll().stream()
                .map(c -> categoryMapper.toResponse(c))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse findById(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            return categoryMapper.toResponse(optionalCategory.get());
        }
        return null;
    }

    @Override
    public CategoryResponse save(CategoryRequest categoryRequest) throws EntityExistsException {
        if (categoryRepository.existsCategoryByName(categoryRequest.getName())) {
            throw new EntityExistsException("Category is exist");
        }
        Category category = categoryRepository.save(categoryMapper.toEntity(categoryRequest));

        return categoryMapper.toResponse(category);
    }

    @Override
    public CategoryResponse update(CategoryRequest categoryRequest, Long id) throws EntityExistsException {
        if (!categoryRepository.findById(id).isPresent()) {
            throw new EntityExistsException("ID Category not found");
        }
        Category category = categoryMapper.toEntity(categoryRequest);
        category.setId(id);
        return categoryMapper.toResponse(categoryRepository.save(category));
    }

    @Override
    public CategoryResponse delete(Long id) throws EntityExistsException {
        Optional<Category> categoryOptional = categoryRepository.findById(id);

        if (!categoryOptional.isPresent()) {
            throw new EntityExistsException("ID Category not found");
        } else {
            List<Product> products = productRepository.findByCategoryId(id);
            if (!products.isEmpty()) {
                throw new EntityExistsException("This category contains products, which cannot be deleted.");
            } else {
                categoryRepository.deleteById(id);
                return categoryMapper.toResponse(categoryOptional.get());
            }
        }
    }
}
