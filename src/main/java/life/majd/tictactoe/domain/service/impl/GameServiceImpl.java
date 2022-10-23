package life.majd.tictactoe.domain.service.impl;

import life.majd.tictactoe.application.controller.messaging.GameRequest;
import life.majd.tictactoe.domain.exceptions.*;
import life.majd.tictactoe.domain.model.Game;
import life.majd.tictactoe.domain.model.Game.Symbol;
import life.majd.tictactoe.domain.model.Player;
import life.majd.tictactoe.domain.service.GameService;
import life.majd.tictactoe.repository.GameRepository;
import life.majd.tictactoe.repository.PlayerRepository;
import life.majd.tictactoe.repository.entity.GameEntity;
import life.majd.tictactoe.repository.entity.PlayerEntity;
import life.majd.tictactoe.repository.mappers.GameMapper;
import life.majd.tictactoe.repository.mappers.PlayerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;

import static life.majd.tictactoe.domain.model.Game.Symbol.*;


@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;
    private final GameMapper gameMapper;
    private final PlayerMapper playerMapper;

    @Override
    public Game createGame(GameRequest gameRequest) throws NoSuchPlayerException {

        PlayerEntity playerEntity1 = playerRepository.findById(gameRequest.getPlayer1()).
                orElseThrow(NoSuchPlayerException::new);
        PlayerEntity playerEntity2 = playerRepository.findById(gameRequest.getPlayer2()).
                orElseThrow(NoSuchPlayerException::new);


        Symbol[] board = new Symbol[9];
        Arrays.fill(board, FREE);
        // TODO randomize symbol assigning
        Player player1 = playerMapper.mapToModel(playerEntity1);
        Player player2 = playerMapper.mapToModel(playerEntity2);
        Game game = Game.builder().
                player1(player1)
                .player2(player2)
                .board(board)
                .status(Game.Status.ACTIVE)
                .turn(player1)
                .totalMoves(0)
                .build();

        GameEntity savedGame = gameRepository.save(gameMapper.mapToEntity(game));
        return gameMapper.mapToModel(savedGame);

    }

    @Override
    public Game getGame(Long gameId) throws NoSuchGameException {
        GameEntity gameEntity = gameRepository.findById(gameId).orElseThrow(NoSuchGameException::new);
        return gameMapper.mapToModel(gameEntity);
    }

    @Override
    @Transactional
    public Game makeMove(Long gameId, int position, Long playerId) throws NotPlayerTureException, NoSuchGameException,
            PositionIsAlreadyFilledException {

        GameEntity gameEntity = gameRepository.findById(gameId).orElseThrow(NoSuchGameException::new);

        if (gameEntity.getStatus() != Game.Status.ACTIVE) {
            throw new GameIsOverException();
        }
        if (!playerId.equals(gameEntity.getTurn().getId())) {
            throw new NotPlayerTureException();
        }
        if (gameEntity.getBoard()[position] != FREE) {
            throw new PositionIsAlreadyFilledException();
        }

        gameEntity.setTotalMoves(gameEntity.getTotalMoves() + 1);

        gameEntity.getBoard()[position] =
                gameEntity.getTurn().getId().equals(gameEntity.getPlayer1().getId()) ? X : Y;

        if (checkIfMoveWins(gameEntity.getBoard())) {
            gameEntity.setStatus(Game.Status.WON);
        } else if (gameEntity.getTotalMoves() >= 9) {
            gameEntity.setStatus(Game.Status.DRAW);
        } else {
            gameEntity.setTurn(
                    gameEntity.getTurn().getId().equals(gameEntity.getPlayer1().getId())
                            ? gameEntity.getPlayer2()
                            : gameEntity.getPlayer1());
        }
        return gameMapper.mapToModel(gameRepository.save(gameEntity));
    }

    /**
     * 0  |  1  | 2
     * --------------
     * 3  |  4  | 5
     * --------------
     * 6  |  7  | 8
     *
     * @param board
     * @return boolean
     */
    private boolean checkIfMoveWins(Symbol[] board) {
        return (board[0] == board[1] && board[1] == board[2] && board[2] != FREE)
                || (board[3] == board[4] && board[4] == board[5] && board[5] != FREE)
                || (board[6] == board[7] && board[7] == board[8] && board[8] != FREE)
                || (board[0] == board[3] && board[3] == board[6] && board[6] != FREE)
                || (board[1] == board[4] && board[4] == board[7] && board[7] != FREE)
                || (board[2] == board[5] && board[5] == board[8] && board[8] != FREE)
                || (board[0] == board[4] && board[4] == board[8] && board[8] != FREE)
                || (board[2] == board[4] && board[4] == board[6] && board[6] != FREE);
    }
}
