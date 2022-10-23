package life.majd.tictactoe.repository;

import life.majd.tictactoe.repository.entity.PlayerEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PlayerRepositoryTest {

    @Autowired
    private PlayerRepository playerRepository;

    @BeforeEach
    public void insertGames() {
        playerRepository.save(new PlayerEntity());
    }

    @Test
    void testGetGame() {
        List<PlayerEntity> all = playerRepository.findAll();
        assertThat(all).isNotEmpty();
    }

}