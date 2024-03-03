package UPBEAT.PlayerController;

import UPBEAT.Model.GameState;
import UPBEAT.Model.MapCell;
import UPBEAT.Model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@CrossOrigin
@RestController
public class Controller {
    GameState game = new GameState();
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @PostMapping("/addplayer")
    public Player addPlayer(@RequestBody String name) {
        game.addPlayer(name);
        Player player = game.getPlayer(name);
        messagingTemplate.convertAndSend("/topic/players", player);

        return player;
    }

    @GetMapping("/allPlayer")
    public Player [] getAllPlayer(){
        return game.getAllPlayer();
    }

    @GetMapping("/getGameState")
    public GameState getGame(){
        return game;
    }

    @GetMapping("/getMap")
    public MapCell getMap(){
        return game.getMap();
    }

    @GetMapping("/StateChange")
    public boolean getState(){
        return game.isGameChange();
    }
}
