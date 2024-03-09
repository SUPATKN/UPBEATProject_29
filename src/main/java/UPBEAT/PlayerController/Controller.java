package UPBEAT.PlayerController;

import UPBEAT.Model.GameState;
import UPBEAT.Model.MapCell;
import UPBEAT.Model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Set;

@CrossOrigin
@RestController
public class Controller {
    GameState game = new GameState();
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @PostMapping("/addplayer")
    public Player addPlayer(@RequestBody String name) {
        name = name.substring(1, name.length() - 1);
        game.addPlayer(name);
        Player player = game.getPlayer(name);
        messagingTemplate.convertAndSend("/topic/gameState", player);

        return player;
    }

    @GetMapping("/allPlayer")
    public Set<Player> getAllPlayer(){
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

    @PutMapping("/Ready")
    public boolean PlayerReady(@RequestBody String name) throws UnsupportedEncodingException {
        name = name.substring(1, name.length() - 1);
            game.getPlayer(name).setReady();
        messagingTemplate.convertAndSend("/topic/ready", game.getPlayer(name).isReady());
        messagingTemplate.convertAndSend("/topic/Allready", game.Allready());
        return game.getPlayer(name).isReady();
    }

    @GetMapping("/allReady")
    public boolean AllPlayerReady(){
        return getGame().Allready();
    }

    @PostMapping("/gamestart")
    public void startGame(){
        messagingTemplate.convertAndSend("/topic/startgame", game.Allready());
        game.StartGame();
    }
}
