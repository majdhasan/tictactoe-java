package life.majd.tictactoe.application.controller;

import life.majd.tictactoe.domain.model.Player;
import life.majd.tictactoe.domain.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static java.time.LocalDateTime.now;

@RestController
@RequestMapping(path = "/v1/player")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @PostMapping
    ResponseEntity<?> savePlayer(@RequestBody Player player) {
        Player savedPlayer = playerService.createPlayer(player);
        return new ResponseEntity<>(savedPlayer, HttpStatus.CREATED);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException rte) {
        Map<String, String> errorMessage = new HashMap<>();
        errorMessage.put("message", rte.getMessage());
        errorMessage.put("timestamp", now().toString());
        errorMessage.put("status", HttpStatus.NOT_ACCEPTABLE.toString());
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_ACCEPTABLE);
    }
}
