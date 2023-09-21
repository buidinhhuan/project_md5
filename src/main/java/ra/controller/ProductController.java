package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ra.model.dto.request.ProductRequest;
import ra.model.dto.response.ProductResponse;
import ra.service.impl.ProductService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/product")
@CrossOrigin("*")
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping("/findAll")
    public ResponseEntity<List<ProductResponse>> getAllProducts(){
        return new  ResponseEntity<>(productService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> findById(@PathVariable Long id){
        try {
        return new  ResponseEntity<>(productService.findById(id), HttpStatus.OK);
        }catch (Exception e){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> create(@ModelAttribute ProductRequest productRequest) {
        try {
        return new ResponseEntity<>(productService.save(productRequest),HttpStatus.CREATED);
        }catch (Exception e){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")

    public ResponseEntity<?> update(@ModelAttribute ProductRequest productRequest,@PathVariable Long id){
        try {
        return new ResponseEntity<>(productService.update(productRequest,id),HttpStatus.CREATED);
        }catch (Exception e){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        try {
        return new ResponseEntity<>(productService.delete(id), HttpStatus.OK);

        }catch (Exception e){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
