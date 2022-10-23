package life.majd.tictactoe.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class NotPlayerTureException extends RuntimeException {

    public NotPlayerTureException() {
        super("It's not your turn!");
    }

    public NotPlayerTureException(String message) {
        super(message);
    }
}
