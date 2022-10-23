package life.majd.tictactoe.domain.service;

import life.majd.tictactoe.domain.exceptions.NoSuchPlayerException;
import life.majd.tictactoe.domain.model.Player;
import life.majd.tictactoe.domain.service.impl.PlayerServiceImpl;
import life.majd.tictactoe.repository.PlayerRepository;
import life.majd.tictactoe.repository.entity.PlayerEntity;
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
class PlayerServiceTest {

    @InjectMocks
    private PlayerServiceImpl service;
    @Mock
    private PlayerMapper playerMapper;
    @Mock
    private PlayerRepository playerRepository;

    @Test
    void whenCreatePlayer_ThenServiceShouldInvokeRepository() {
        //Arrange
        Player bob = Player.builder().name("bob").id(1L).build();
        PlayerEntity bobEntity = PlayerEntity.builder().name("bob").id(1L).build();
        when(playerMapper.mapToEntity(any())).thenReturn(bobEntity);
        when(playerMapper.mapToModel(any())).thenReturn(bob);

        //Act
        Player player = service.createPlayer(bob);

        //Assert
        assertThat(player).isEqualTo(bob);
        verify(playerRepository, times(1)).save(any(PlayerEntity.class));

    }

    @Test
    void whenGetExistingPlayer_ThenServiceShouldReturnPlayer() {
        //Arrange
        Player bob = Player.builder().name("bob").id(1L).build();

        PlayerEntity bobEntity = PlayerEntity.builder().name("bob").id(1L).build();
        when(playerMapper.mapToModel(any())).thenReturn(bob);
        when(playerRepository.findById(bob.getId())).thenReturn(Optional.of(bobEntity));

        //Act
        Player player = service.getPlayer(bob.getId());

        //Assert
        assertThat(player).isEqualTo(bob);
        verify(playerRepository, times(1)).findById(bob.getId());

    }

    @Test
    void whenGetNotExistingPlayer_ThenServiceShouldThrowException() {
        //Arrange
        Player bob = Player.builder().name("bob").id(1L).build();
        when(playerRepository.findById(bob.getId())).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(NoSuchPlayerException.class, () -> service.getPlayer(bob.getId()));
        verify(playerRepository, times(1)).findById(bob.getId());

    }


}