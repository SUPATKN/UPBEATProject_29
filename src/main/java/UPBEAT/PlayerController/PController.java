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
    Player player;
    @MessageMapping("/addPlayer")
    @SendTo("/topic/public")
    public Player addPlayer(String name, SimpMessageHeaderAccessor headerAccessor){
        headerAccessor.getSessionAttributes().put("username", name);
        player = new Player(name, map);
        return player;
    }


    @MessageMapping("/sendPlayer")
    @SendTo("/topic/public")
    public String addPlayer(String name){
        return player.getName();
    }
}
