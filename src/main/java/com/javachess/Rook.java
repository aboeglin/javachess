package com.javachess;

public class Rook extends Piece {

	private Rook(String x, String y, Color c) {
		this.x = x;
		this.y = y;
		this.color = c;
	}

	public static Rook of(String x, String y, Color c) {
		return new Rook(x, y, c);
	}

	@Override
	public boolean equals(Object o) {
		return super.equals(o) && o instanceof Rook;
	}
}
