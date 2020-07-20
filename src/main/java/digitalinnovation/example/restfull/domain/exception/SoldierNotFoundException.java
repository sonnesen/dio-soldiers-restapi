package digitalinnovation.example.restfull.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class SoldierNotFoundException extends Exception {

  private static final long serialVersionUID = 1L;

  public SoldierNotFoundException(Long id) {
    super(String.format("Soldado %s não encontrado!", id));
  }
}
