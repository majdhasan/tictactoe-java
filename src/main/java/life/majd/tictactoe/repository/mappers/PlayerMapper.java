package life.majd.tictactoe.repository.mappers;

import life.majd.tictactoe.domain.model.Player;
import life.majd.tictactoe.repository.entity.PlayerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PlayerMapper {

    PlayerMapper INSTANCE = Mappers.getMapper(PlayerMapper.class);

    PlayerEntity mapToEntity(Player player);

    Player mapToModel(PlayerEntity player);
}
