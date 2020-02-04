package com.javachess;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import java.util.Optional;
import com.javachess.utils.FP;

public class Pawn extends Piece {
  private Pawn(String x, String y, Color c) {
    this.x = x;
    this.y = y;
    this.color = c;
  }

  public static Pawn of(String x, String y, Color c) {
    return new Pawn(x, y, c);
  }

  public static int computeYOffset(String y1, String y2) {
    return FP.pipe(
      (Entry<String, String> m) -> new SimpleEntry<>(Position.yAsInt(m.getKey()), Position.yAsInt(m.getValue())),
      (Entry<Integer, Integer> m) -> m.getKey() - m.getValue()
    ).apply(new SimpleEntry<>(y1, y2));
  }

  @Override
  public boolean canMoveTo(String x, String y, Board board) {
    return FP.pipe(
      (Board b) -> Board.getPieceAt(x, y, b),
      o -> FP.ifElse(
        Optional<Piece>::isPresent,
        opt -> false, // Check if position is in diagonal and is enemy ( Feind )
        opt -> computeYOffset(y, this.y) < 3
      ).apply(o)
    ).apply(board);
  }

  @Override
  public boolean equals(Object o) {
    return super.equals(o) && o instanceof Pawn;
  }
}