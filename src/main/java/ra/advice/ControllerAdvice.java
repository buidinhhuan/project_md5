package ra.advice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ra.exception.CartException;
import ra.exception.CategoryException;
import ra.exception.LoginException;
import ra.exception.ProductException;

import javax.persistence.EntityExistsException;
import java.rmi.AccessException;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(LoginException.class)
    public ResponseEntity<String> loginFail(LoginException loginException){
        return new  ResponseEntity<>(loginException.getMessage(), HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(AccessException.class)
    public ResponseEntity<String> loginFail(AccessException accessException){
        return new  ResponseEntity<>("ban không có quyền truy cập", HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<String> loginFail(EntityExistsException entityExistsException){
        return new  ResponseEntity<>( entityExistsException.getMessage(), HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(CategoryException.class)
    public  ResponseEntity<String> categoryFail(EntityExistsException entityExistsException){
        return new ResponseEntity<>(entityExistsException.getMessage(),HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(ProductException.class)
    public  ResponseEntity<String> productFail(EntityExistsException entityExistsException){
        return new ResponseEntity<>(entityExistsException.getMessage(),HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(CartException.class)
    public  ResponseEntity<String> cartFail(EntityExistsException entityExistsException){
        return new ResponseEntity<>(entityExistsException.getMessage(),HttpStatus.UNAUTHORIZED);
    }
}
