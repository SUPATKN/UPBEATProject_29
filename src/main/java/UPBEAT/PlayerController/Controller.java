package UPBEAT.PlayerController;

import UPBEAT.Model.GameState;
import UPBEAT.Model.Player;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@CrossOrigin
@RestController
public class Controller {
    GameState game = new GameState();
    @PostMapping("/player")
    public Player addPlayer(@RequestBody String name){
        game.addPlayer(name);
        return game.getPlayer(name);
    }

    @GetMapping("/allPlayer")
    public Player [] getAllPlayer(){
        return game.getAllPlayer();
    }
}
