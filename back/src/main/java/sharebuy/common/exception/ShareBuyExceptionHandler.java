package sharebuy.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice
public class ShareBuyExceptionHandler {

    @ExceptionHandler(ShareBuyException.class)
    public ResponseEntity<ErrorResponse> handle(ShareBuyException e){
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage(),e.getErrorCode().name()));
    }
}
