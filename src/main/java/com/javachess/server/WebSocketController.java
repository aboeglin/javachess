package com.javachess.server;

import com.google.gson.Gson;
import com.javachess.exception.*;
import com.javachess.logic.*;
import com.javachess.server.message.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class WebSocketController {

  @Autowired
  private GameOrchestrator orchestrator;

  @MessageMapping("/game/{id}/join")
  @SendTo("/queue/game/{id}/ready")
  public String handleJoinGame(@Payload String messageString, @DestinationVariable("id") int id) throws PlayerNotInGameException, GameNotFoundException {
    Gson gson = new Gson();
    JoinGame input = gson.fromJson(messageString, JoinGame.class);

    // Look up the dude, find the game, if complete, return game ready with initial board to players
    Player p = Player.of(input.getPlayerId());
    orchestrator.join(p);

    // Should be find game by ID and that should fix the tests as we would then return game with id 2
    Game g = orchestrator.findGameById(id);

    if (g == null) {
      throw new GameNotFoundException("The game was not found !");
    }

    // TODO: Write Game::hasPlayer(String id), and/or Game::hasPlayer(Player p)
    if (!p.equals(g.getPlayer1()) && !p.equals(g.getPlayer2())) {
      throw new PlayerNotInGameException("You are not in this game !");
    }

    // We only start the game if the game is full
    if (Game.isComplete(g)) {
      return gson.toJson(new GameUpdate("READY", g), GameUpdate.class);
    }
    else {
      // TODO: return a message that says it waits for a second user ?
      return null;
    }
  }

  @MessageMapping("/game/{id}/select-piece")
  @SendTo("/queue/game/{id}/possible-moves")
  public String handleSelectPiece(@Payload String messageString, @DestinationVariable("id") int id) throws PlayerNotInGameException, GameNotFoundException {
    Gson gson = new Gson();
    SelectPiece input = gson.fromJson(messageString, SelectPiece.class);
    Game g = orchestrator.findGameById(id);
    Player p = Player.of(input.getPlayerId());

    if (g == null) {
      throw new GameNotFoundException("The game was not found !");
    }

    if (!p.equals(g.getPlayer1()) && !p.equals(g.getPlayer2())) {
      throw new PlayerNotInGameException("You are not in this game !");
    }

    Position[] moves = Game.getPossibleMoves(input.getX(), input.getY(), Game.getPieces(g));
    return gson.toJson(new GameUpdate("UPDATE", g, moves), GameUpdate.class);
  }

  @MessageMapping("/game/{id}/perform-move")
  @SendTo("/queue/game/{id}/piece-moved")
  public String handlePerformMove(@Payload String messageString, @DestinationVariable("id") int id)
    throws NotYourTurnException, MoveNotAllowedException, PlayerNotInGameException, GameNotFoundException {
    Gson gson = new Gson();
    PerformMove input = gson.fromJson(messageString, PerformMove.class);
    Player p = Player.of(input.getPlayerId());

    Game g = orchestrator.findGameById(id);
    GameUpdate response = null;

    if (g == null) {
      throw new GameNotFoundException("The game was not found !");
    }

    if (!p.equals(g.getPlayer1()) && !p.equals(g.getPlayer2())) {
      throw new PlayerNotInGameException("You are not in this game !");
    }

    Optional<Piece> movingPiece = Game.getPieceAt(input.getFromX(), input.getFromY(), g);

    if (movingPiece.isPresent()) {
      if (Piece.canMoveTo(input.getToX(), input.getToY(), Game.getPieces(g), movingPiece.get())) {
        Game newGame = orchestrator.performMove(input.getFromX(), input.getFromY(), input.getToX(), input.getToY(), g);

        // If it's the same reference, that means that the game was not updated and therefore
        // that the move was not allowed for some reason.
        if (newGame == g) {
          throw new NotYourTurnException("It's not your turn !");
        }
        else {
          response = new GameUpdate("UPDATE", newGame);
        }
      }
      else {
        // Piece can't move there
        throw new MoveNotAllowedException("This move is not valid !");
      }
    }

    return gson.toJson(response, GameUpdate.class);
  }

  @MessageExceptionHandler
  @SendToUser("/queue/errors")
  public String handleException(ChessException e) {
    e.printStackTrace();

    Gson gson = new Gson();
    return gson.toJson(new GameUpdate("ERROR", null, e.getErrorCode(), e.getMessage()), GameUpdate.class);
  }
}
