package ra.advice;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ra.exception.*;

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
    public  ResponseEntity<String> categoryFail(CategoryException categoryException){
        return new ResponseEntity<>(categoryException.getMessage(),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ProductException.class)
    public  ResponseEntity<String> productFail(ProductException productException){
        return new ResponseEntity<>(productException.getMessage(),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(CartException.class)
    public  ResponseEntity<String> cartFail(CartException cartException){
        return new ResponseEntity<>(cartException.getMessage(),HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(OrderException.class)
//    public ResponseEntity<String> OrderFail( ChangeSetPersister.NotFoundException notFoundException) {
//        return new ResponseEntity<>("ID not found", HttpStatus.NOT_FOUND);
//    }
}
