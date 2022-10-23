package life.majd.tictactoe.repository.mappers;

import life.majd.tictactoe.domain.model.Game;
import life.majd.tictactoe.repository.entity.GameEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface GameMapper {

    GameMapper INSTANCE = Mappers.getMapper(GameMapper.class);

    GameEntity mapToEntity(Game game);

    Game mapToModel(GameEntity game);

}
