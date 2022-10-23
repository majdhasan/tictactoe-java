package life.majd.tictactoe.domain.service;

import life.majd.tictactoe.domain.exceptions.NoSuchPlayerException;
import life.majd.tictactoe.domain.model.Player;

public interface PlayerService {

    Player createPlayer(Player player);

    Player getPlayer(Long id) throws NoSuchPlayerException;

}
