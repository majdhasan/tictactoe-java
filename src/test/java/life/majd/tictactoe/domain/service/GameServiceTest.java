package life.majd.tictactoe.domain.service;

import life.majd.tictactoe.application.controller.messaging.GameRequest;
import life.majd.tictactoe.domain.exceptions.NoSuchGameException;
import life.majd.tictactoe.domain.model.Game;
import life.majd.tictactoe.domain.service.impl.GameServiceImpl;
import life.majd.tictactoe.repository.GameRepository;
import life.majd.tictactoe.repository.PlayerRepository;
import life.majd.tictactoe.repository.entity.GameEntity;
import life.majd.tictactoe.repository.entity.PlayerEntity;
import life.majd.tictactoe.repository.mappers.GameMapper;
import life.majd.tictactoe.repository.mappers.PlayerMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @InjectMocks
    private GameServiceImpl service;
    @Mock
    private GameMapper gameMapper;
    @Mock
    private PlayerMapper playerMapper;
    @Mock
    private PlayerRepository playerRepository;
    @Mock
    private GameRepository gameRepository;

    @Test
    void whenCreateGame_ThenInvokeRepository() {
        //Arrange
        GameRequest gameRequest = GameRequest.builder().player1(1L).player2(2L).build();
        Game game = Game.builder().id(1L).build();
        GameEntity gameEntity = GameEntity.builder().id(1L).build();

        when(gameMapper.mapToModel(any())).thenReturn(game);
        when(gameMapper.mapToEntity(any())).thenReturn(gameEntity);
        when(playerRepository.findById(any())).thenReturn(Optional.of(PlayerEntity.builder().id(1L).build()));
        when(playerRepository.findById(any())).thenReturn(Optional.of(PlayerEntity.builder().id(2L).build()));
        when(gameRepository.save(gameEntity)).thenReturn(gameEntity);

        //Act
        Game saved = service.createGame(gameRequest);
        //Assert
        assertThat(saved.getId()).isEqualTo(1L);
        verify(playerRepository, times(2)).findById(any());
        verify(gameRepository, times(1)).save(any());

    }

    @Test
    void whenGetExistingGame_ThenReturnGame() {
        //Arrange
        Game game = Game.builder().id(1L).build();
        GameEntity gameEntity = GameEntity.builder().id(1L).build();

        when(gameMapper.mapToModel(any())).thenReturn(game);
        when(gameRepository.findById(game.getId())).thenReturn(Optional.of(gameEntity));

        //Act
        Game found = service.getGame(game.getId());

        //Assert
        assertThat(found.getId()).isEqualTo(1L);
        verify(gameRepository, times(1)).findById(game.getId());

    }

    @Test
    void whenGetNonExistingGame_ThenThrowException() {
        //Arrange
        Game game = Game.builder().id(1231L).build();

        when(gameRepository.findById(game.getId())).thenReturn(Optional.empty());

        //Act
        assertThrows(NoSuchGameException.class, () -> service.getGame(game.getId()));
        verify(gameRepository, times(1)).findById(any());

    }


}