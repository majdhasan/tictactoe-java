package life.majd.tictactoe.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Game {

    private Long id;
    private Symbol[] board;
    private Player player1;
    private Player player2;
    private Player turn;
    private Status status;
    private Player winner;
    private int totalMoves;

    public enum Status{
        ACTIVE, DRAW, WON
    }


    public enum Symbol {
        X("X"), Y("Y"), FREE("FREE");

        private String value;

        Symbol(String symbol) {

        }

        @Override
        public String toString() {
            return super.toString();
        }
    }

}
