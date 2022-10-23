package life.majd.tictactoe.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class NoSuchPlayerException extends RuntimeException {

    public NoSuchPlayerException() {
        super("No Such Player was found");
    }

    public NoSuchPlayerException(String message) {
        super(message);
    }
}
