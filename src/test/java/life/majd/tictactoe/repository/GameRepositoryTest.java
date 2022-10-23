package life.majd.tictactoe.repository;

import life.majd.tictactoe.repository.entity.GameEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class GameRepositoryTest {


    @Autowired
    private GameRepository gameRepository;

    @BeforeEach
    public void insertGames() {
        gameRepository.save(new GameEntity());
    }

    @Test
    void testGetGame() {
        List<GameEntity> all = gameRepository.findAll();
        assertThat(all).isNotEmpty();
    }


}