package life.majd.tictactoe.application.controller.messaging;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GameRequest {

    private Long player1;
    private Long player2;

}
