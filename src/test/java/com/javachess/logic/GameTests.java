package com.javachess.logic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTests {
  @Test
  @DisplayName("create should return a fresh board with 16 pawns")
  public void createPawns() {
    List<Piece> pieces = Game.getInitialPieces();
    assertEquals(Game.getPieceAt("a", "2", pieces).get(), Piece.of("a", "2", Color.WHITE, PieceType.PAWN));
    assertEquals(Game.getPieceAt("b", "2", pieces).get(), Piece.of("b", "2", Color.WHITE, PieceType.PAWN));
    assertEquals(Game.getPieceAt("c", "2", pieces).get(), Piece.of("c", "2", Color.WHITE, PieceType.PAWN));
    assertEquals(Game.getPieceAt("d", "2", pieces).get(), Piece.of("d", "2", Color.WHITE, PieceType.PAWN));
    assertEquals(Game.getPieceAt("e", "2", pieces).get(), Piece.of("e", "2", Color.WHITE, PieceType.PAWN));
    assertEquals(Game.getPieceAt("f", "2", pieces).get(), Piece.of("f", "2", Color.WHITE, PieceType.PAWN));
    assertEquals(Game.getPieceAt("g", "2", pieces).get(), Piece.of("g", "2", Color.WHITE, PieceType.PAWN));
    assertEquals(Game.getPieceAt("h", "2", pieces).get(), Piece.of("h", "2", Color.WHITE, PieceType.PAWN));

    assertEquals(Game.getPieceAt("a", "7", pieces).get(), Piece.of("a", "7", Color.BLACK, PieceType.PAWN));
    assertEquals(Game.getPieceAt("b", "7", pieces).get(), Piece.of("b", "7", Color.BLACK, PieceType.PAWN));
    assertEquals(Game.getPieceAt("c", "7", pieces).get(), Piece.of("c", "7", Color.BLACK, PieceType.PAWN));
    assertEquals(Game.getPieceAt("d", "7", pieces).get(), Piece.of("d", "7", Color.BLACK, PieceType.PAWN));
    assertEquals(Game.getPieceAt("e", "7", pieces).get(), Piece.of("e", "7", Color.BLACK, PieceType.PAWN));
    assertEquals(Game.getPieceAt("f", "7", pieces).get(), Piece.of("f", "7", Color.BLACK, PieceType.PAWN));
    assertEquals(Game.getPieceAt("g", "7", pieces).get(), Piece.of("g", "7", Color.BLACK, PieceType.PAWN));
    assertEquals(Game.getPieceAt("h", "7", pieces).get(), Piece.of("h", "7", Color.BLACK, PieceType.PAWN));
  }

  @Test
  @DisplayName("create should return a fresh board with 4 rooks")
  public void createRooks() {
    List<Piece> pieces = Game.getInitialPieces();
    assertEquals(Game.getPieceAt("a", "1", pieces).get(), Piece.of("a", "1", Color.WHITE, PieceType.ROOK));
    assertEquals(Game.getPieceAt("h", "1", pieces).get(), Piece.of("h", "1", Color.WHITE, PieceType.ROOK));

    assertEquals(Game.getPieceAt("a", "8", pieces).get(), Piece.of("a", "8", Color.BLACK, PieceType.ROOK));
    assertEquals(Game.getPieceAt("h", "8", pieces).get(), Piece.of("h", "8", Color.BLACK, PieceType.ROOK));
  }

  @Test
  @DisplayName("create should return a fresh board with 4 rooks")
  public void createBishops() {
    List<Piece> pieces = Game.getInitialPieces();
    assertEquals(Game.getPieceAt("c", "1", pieces).get(), Piece.of("c", "1", Color.WHITE, PieceType.BISHOP));
    assertEquals(Game.getPieceAt("f", "1", pieces).get(), Piece.of("f", "1", Color.WHITE, PieceType.BISHOP));

    assertEquals(Game.getPieceAt("c", "8", pieces).get(), Piece.of("c", "8", Color.BLACK, PieceType.BISHOP));
    assertEquals(Game.getPieceAt("f", "8", pieces).get(), Piece.of("f", "8", Color.BLACK, PieceType.BISHOP));
  }

  @Test
  @DisplayName("create should return a fresh board with 4 knights")
  public void createKnights() {
    List<Piece> pieces = Game.getInitialPieces();
    assertEquals(Game.getPieceAt("b", "1", pieces).get(), Piece.of("b", "1", Color.WHITE, PieceType.KNIGHT));
    assertEquals(Game.getPieceAt("g", "1", pieces).get(), Piece.of("g", "1", Color.WHITE, PieceType.KNIGHT));

    assertEquals(Game.getPieceAt("b", "8", pieces).get(), Piece.of("b", "8", Color.BLACK, PieceType.KNIGHT));
    assertEquals(Game.getPieceAt("g", "8", pieces).get(), Piece.of("g", "8", Color.BLACK, PieceType.KNIGHT));
  }

  @Test
  @DisplayName("create should return a fresh board with 2 queens")
  public void createQueens() {
    List<Piece> pieces = Game.getInitialPieces();
    assertEquals(Game.getPieceAt("d", "1", pieces).get(), Piece.of("d", "1", Color.WHITE, PieceType.QUEEN));

    assertEquals(Game.getPieceAt("d", "8", pieces).get(), Piece.of("d", "8", Color.BLACK, PieceType.QUEEN));
  }

  @Test
  @DisplayName("create should return a fresh board with 2 kings")
  public void createKings() {
    List<Piece> pieces = Game.getInitialPieces();
    assertEquals(Game.getPieceAt("e", "1", pieces).get(), Piece.of("e", "1", Color.WHITE, PieceType.KING));

    assertEquals(Game.getPieceAt("e", "8", pieces).get(), Piece.of("e", "8", Color.BLACK, PieceType.KING));
  }

  @Test
  @DisplayName("When creating a new game without player isComplete should be false")
  public void initialGameNotComplete() {
    Game game = Game.of(1);
    assertEquals(false, Game.isComplete(game));
  }

  @Test
  @DisplayName("When creating a new game with 2 players isComplete should be true")
  public void initialGameComplete() {
    Game game = Game.of(1, Player.of("John"), Player.of("Jess"));
    assertEquals(true, Game.isComplete(game));
  }

  @Test
  @DisplayName("addPlayer should set the first player on an empty game")
  public void addPlayerOnEmptyGame() {
    Game game = Game.of(1);
    game = Game.addPlayer(Player.of("John"), game);
    assertEquals(Player.of("John"), game.getPlayer1());
  }

  @Test
  @DisplayName("addPlayer should add the first and then the second player when called twice")
  public void addPlayerTwoTimes() {
    Game game = Game.of(1);
    game = Game.addPlayer(Player.of("John"), game);
    game = Game.addPlayer(Player.of("Jess"), game);
    assertEquals(Player.of("John"), game.getPlayer1());
    assertEquals(Player.of("Jess"), game.getPlayer2());
  }

  @Test
  @DisplayName("equals should return true if the two games have the same id and same players")
  public void equals() {
    Game game1 = Game.of(1, Player.of("John"), Player.of("Jess"));
    Game game2 = Game.of(1, Player.of("John"), Player.of("Jess"));
    assertEquals(true, game1.equals(game2));
  }

  @Test
  @DisplayName("equals should return true even with empty players")
  public void equalsWithNullPlayer() {
    Game game1 = Game.of(1, null, Player.of("Jess"));
    Game game2 = Game.of(1, null, Player.of("Jess"));
    assertEquals(true, game1.equals(game2));

    Game game3 = Game.of(1, Player.of("John"), null);
    Game game4 = Game.of(1, Player.of("John"), null);
    assertEquals(true, game3.equals(game4));
  }

  @Test
  @DisplayName("doMove should return a new game with the piece at the new position")
  public void doMove() {
    Game g = Game.of(1, Player.of("white", Color.WHITE), Player.of("black", Color.BLACK));
    Game result = Game.doMove(Move.of(Position.of("b", "7"), Position.of("b", "3")), g);
    Piece movedPiece = Game.getPieceAt("b", "3", result).get();
    assertEquals(Piece.of("b", "3", Color.BLACK, PieceType.PAWN), movedPiece);
  }

  @Test
  @DisplayName("getPossibleMoves should return a list of possible moves")
  public void getPossibleMoves() {
    Game g = Game.of(1, Player.of("white", Color.WHITE), Player.of("black", Color.BLACK));

    // White Pawn at start of game should have two options
    Position[] result = Game.getPossibleMoves("b", "2", Game.getPieces(g));
    List<Position> expected = new ArrayList<>();
    expected.add(Position.of("b", "3"));
    expected.add(Position.of("b", "4"));
    assertArrayEquals(expected.toArray(), result);
  }
}
