package rmhub.user.management.controller.exception;

import java.util.Collections;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.sun.nio.file.ExtendedCopyOption;
import rmhub.user.management.exception.BusinessException;

@ControllerAdvice
public class UserQueryException {

  @ExceptionHandler(value = BusinessException.class)
  public ResponseEntity<ApiError> businessException(BusinessException ex) {
    System.out.println("This is error message: " + ex.getMessage());
    System.out.println("This is error code: " + ex.getHttpStatus());
    ApiError apiError = new ApiError(ex.getMessage(), ex.getHttpStatus());
    return new ResponseEntity<ApiError>(apiError, HttpStatus.BAD_REQUEST);
  }
}
