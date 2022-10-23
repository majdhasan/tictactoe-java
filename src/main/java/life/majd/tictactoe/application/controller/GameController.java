package life.majd.tictactoe.application.controller;

import life.majd.tictactoe.application.controller.messaging.GameRequest;
import life.majd.tictactoe.domain.exceptions.NoSuchGameException;
import life.majd.tictactoe.domain.exceptions.NoSuchPlayerException;
import life.majd.tictactoe.domain.model.Game;
import life.majd.tictactoe.domain.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/v1/game")
public class GameController {

    @Autowired
    private GameService gameService;


    @PostMapping
    ResponseEntity<?> startNewGame(@RequestBody GameRequest gameRequest) throws NoSuchGameException, NoSuchPlayerException {
        Game game = gameService.createGame(gameRequest);
        return new ResponseEntity<>(game, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getGame(@PathVariable Long id) throws NoSuchGameException {
        Game game = gameService.getGame(id);
        return new ResponseEntity<>(game, HttpStatus.OK);
    }

    @PostMapping("/makeMove")
    ResponseEntity<?> makeMove(
            @RequestHeader(value = "playerId") long playerId,
            @RequestParam long gameId,
            @RequestParam int position) throws NoSuchGameException, NoSuchPlayerException {

        Game game = gameService.makeMove(gameId, position, playerId);
        return new ResponseEntity<>(game, HttpStatus.OK);
    }



    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException rte) {
        Map<String, String> errorMessage = new HashMap<>();
        errorMessage.put("message", rte.getMessage());
        errorMessage.put("timestamp", LocalDateTime.now().toString());
        errorMessage.put("status", HttpStatus.NOT_ACCEPTABLE.toString());
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_ACCEPTABLE);
    }

}
