package life.majd.tictactoe.domain.service.impl;

import life.majd.tictactoe.domain.exceptions.NoSuchPlayerException;
import life.majd.tictactoe.domain.model.Player;
import life.majd.tictactoe.domain.service.PlayerService;
import life.majd.tictactoe.repository.PlayerRepository;
import life.majd.tictactoe.repository.entity.PlayerEntity;
import life.majd.tictactoe.repository.mappers.PlayerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;

    @Override
    public Player createPlayer(Player player) {
        PlayerEntity saved = playerRepository.save(playerMapper.mapToEntity(player));
        return playerMapper.mapToModel(saved);
    }

    @Override
    public Player getPlayer(Long id) throws NoSuchPlayerException {
        PlayerEntity playerEntity = playerRepository.findById(id).orElseThrow(NoSuchPlayerException::new);
        return playerMapper.mapToModel(playerEntity);
    }
}
