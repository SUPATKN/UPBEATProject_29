package UPBEAT.PlayerController;

import UPBEAT.MapCell;
import UPBEAT.Player;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class PController {
    MapCell map = new MapCell(5,5);
    Player[] players = new Player[2];

    @MessageMapping("/addPlayer")
    @SendTo("/topic/public")
    public Player addPlayer(String name, SimpMessageHeaderAccessor headerAccessor){
        headerAccessor.getSessionAttributes().put("username", name);
        Player player = new Player(name, map);
        if (players[0] == null) {
            players[0] = player;
            player.setIndex(0);
        } else if (players[1] == null) {
            players[1] = player;
            player.setIndex(1);
        }
        return player;
    }

    @MessageMapping("/sendPlayer")
    @SendTo("/topic/public")
    public Player[] sendPlayer(String name, SimpMessageHeaderAccessor headerAccessor) {
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if (name.equals(username)) {
            return players;
        } else {
            return new Player[0];
        }
    }


}