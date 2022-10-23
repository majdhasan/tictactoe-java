package life.majd.tictactoe.repository.entity;

import life.majd.tictactoe.domain.model.Game.Status;
import life.majd.tictactoe.domain.model.Game.Symbol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "game")
@Entity
public class GameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Symbol.class)
    @OrderColumn
    private Symbol[] board;
    @ManyToOne(targetEntity = PlayerEntity.class)
    private PlayerEntity player1;
    @ManyToOne(targetEntity = PlayerEntity.class)
    private PlayerEntity player2;
    @ManyToOne(targetEntity = PlayerEntity.class)
    private PlayerEntity turn;
    private Status status;
    @ManyToOne(targetEntity = PlayerEntity.class)
    private PlayerEntity winner;
    private int totalMoves;

}