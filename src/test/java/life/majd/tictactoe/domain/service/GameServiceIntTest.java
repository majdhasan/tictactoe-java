package life.majd.tictactoe.domain.service;

import life.majd.tictactoe.application.controller.messaging.GameRequest;
import life.majd.tictactoe.domain.model.Game;
import life.majd.tictactoe.domain.model.Player;
import life.majd.tictactoe.repository.GameRepository;
import life.majd.tictactoe.repository.PlayerRepository;
import life.majd.tictactoe.repository.entity.GameEntity;
import life.majd.tictactoe.repository.mappers.GameMapper;
import life.majd.tictactoe.repository.mappers.PlayerMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static life.majd.tictactoe.domain.model.Game.Status.*;
import static life.majd.tictactoe.domain.model.Game.Symbol.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class GameServiceIntTest {

    @Autowired
    private GameService gameService;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PlayerMapper playerMapper;
    @Autowired
    private GameMapper gameMapper;
    @Autowired
    private GameRepository gameRepository;

    private Player player1;
    private Player player2;
    private GameEntity gameEntity;

    @BeforeEach
    void insertData() {
        player1 = playerService.createPlayer(Player.builder().id(1L).build());
        player2 = playerService.createPlayer(Player.builder().id(2L).build());
        gameEntity = gameRepository.save(GameEntity.builder()
                .id(1L)
                .player1(playerMapper.mapToEntity(player1))
                .player2(playerMapper.mapToEntity(player2))
                .status(ACTIVE)
                .turn(playerMapper.mapToEntity(player1))
                .totalMoves(8)
                .build());
    }

    @AfterEach
    void deleteData() {
        gameRepository.deleteAll();
        playerRepository.deleteAll();
    }

    @Test
    void whenAllPositionsAreFilledWithNoWin_ThenEndGameWithDraw() {
        //Arrange
        Game.Symbol[] board = {FREE, X, Y,
                Y, Y, X,
                X, Y, Y};

        gameEntity.setBoard(board);
        gameRepository.save(gameEntity);

        //Act
        Game afterMove = gameService.makeMove(gameEntity.getId(), 0, player1.getId());

        assertThat(afterMove.getStatus()).isEqualTo(DRAW);
        assertThat(afterMove.getTotalMoves()).isEqualTo(9);
        assertThat(afterMove.getBoard()).isEqualTo(new Game.Symbol[]{X, X, Y, Y, Y, X, X, Y, Y});


    }


    @Test
    void whenMoveIsWinning_ThenEndGameWithWin() {
        //Arrange
        Game.Symbol[] board = {Y, FREE, Y,
                Y, X, X,
                X, X, Y};

        gameEntity.setBoard(board);
        gameRepository.save(gameEntity);

        //Act
        Game afterMove = gameService.makeMove(gameEntity.getId(), 1, player1.getId());

        assertThat(afterMove.getStatus()).isEqualTo(WON);
        assertThat(afterMove.getTotalMoves()).isEqualTo(9);
        assertThat(afterMove.getBoard()).isEqualTo(new Game.Symbol[]{Y, X, Y, Y, X, X, X, X, Y});
    }

    @Test
    void whenFirstMoveIsPlayedInTheMiddle_ThenSymbolXIsInTheRightPosition() {
        //Arrange
        Game newGame = gameService.createGame(GameRequest.builder().player1(player1.getId()).player2(player2.getId()).build());

        //Act
        Game afterMove = gameService.makeMove(newGame.getId(), 4, player1.getId());

        assertThat(afterMove.getStatus()).isEqualTo(ACTIVE);
        assertThat(afterMove.getTotalMoves()).isEqualTo(1);
        assertThat(afterMove.getBoard()).isEqualTo(new Game.Symbol[]{FREE, FREE, FREE, FREE, X, FREE, FREE, FREE, FREE});
    }

}