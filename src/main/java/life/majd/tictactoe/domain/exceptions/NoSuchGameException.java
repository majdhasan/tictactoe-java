package life.majd.tictactoe.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class NoSuchGameException extends RuntimeException {

    public NoSuchGameException() {
        super("No Such Game was found");
    }

    public NoSuchGameException(String message) {
        super(message);
    }
}
