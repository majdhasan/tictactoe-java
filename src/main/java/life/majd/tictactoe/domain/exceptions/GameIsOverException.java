package life.majd.tictactoe.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class GameIsOverException extends RuntimeException {
    public GameIsOverException() {
        super("Game is over");
    }

    public GameIsOverException(String message) {
        super(message);
    }
}
