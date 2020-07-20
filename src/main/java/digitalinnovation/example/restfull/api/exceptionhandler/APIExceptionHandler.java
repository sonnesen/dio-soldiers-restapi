package digitalinnovation.example.restfull.api.exceptionhandler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import digitalinnovation.example.restfull.domain.exception.SoldierNotFoundException;

@ControllerAdvice
public class APIExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(SoldierNotFoundException.class)
  public ResponseEntity<Object> handleEntidadeNaoEncontrada(Exception e, WebRequest request) {
    var status = HttpStatus.NOT_FOUND;
    return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), status, request);
  }
}
