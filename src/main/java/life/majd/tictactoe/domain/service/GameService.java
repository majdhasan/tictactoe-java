package life.majd.tictactoe.domain.service;

import life.majd.tictactoe.application.controller.messaging.GameRequest;
import life.majd.tictactoe.domain.exceptions.NoSuchGameException;
import life.majd.tictactoe.domain.exceptions.NoSuchPlayerException;
import life.majd.tictactoe.domain.exceptions.NotPlayerTureException;
import life.majd.tictactoe.domain.exceptions.PositionIsAlreadyFilledException;
import life.majd.tictactoe.domain.model.Game;

public interface GameService {

    Game createGame(GameRequest game) throws NoSuchGameException, NoSuchPlayerException;

    Game getGame(Long gameId) throws NoSuchGameException;

    Game makeMove(Long gameId, int position, Long playerId) throws NotPlayerTureException, NoSuchGameException, PositionIsAlreadyFilledException;

}
