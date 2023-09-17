package ra.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.model.dto.request.CategoryRequest;
import ra.model.dto.response.CategoryResponse;
import ra.service.impl.CategoryService;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/category")
@CrossOrigin("*")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        return new  ResponseEntity<>(categoryService.findAll(),HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> findById(@PathVariable Long id ){
        return  new ResponseEntity<>(categoryService.findById(id),HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<CategoryResponse> create(@RequestBody @Valid CategoryRequest categoryRequest) {
        return new ResponseEntity<>(categoryService.save(categoryRequest),HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> update(@RequestBody @Valid CategoryRequest categoryRequest,@PathVariable Long id){
        return new ResponseEntity<>(categoryService.update(categoryRequest,id),HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryResponse> deleteById(@PathVariable Long id ){
        return  new ResponseEntity<>(categoryService.delete(id),HttpStatus.OK);
    }

}
