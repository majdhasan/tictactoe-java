package life.majd.tictactoe.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class PositionIsAlreadyFilledException extends RuntimeException {

    public PositionIsAlreadyFilledException() {
        super("Position has already been filled!");
    }

    public PositionIsAlreadyFilledException(String message) {
        super(message);
    }
}
