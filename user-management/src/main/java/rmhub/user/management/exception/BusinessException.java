package rmhub.user.management.exception;

import org.springframework.http.HttpStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessException  extends RuntimeException {
  private HttpStatus httpStatus;
  public BusinessException(String message, HttpStatus httpStatus) {
    super(message);
    this.httpStatus = httpStatus;
  }
}
