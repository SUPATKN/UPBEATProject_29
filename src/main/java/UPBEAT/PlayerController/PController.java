package UPBEAT.PlayerController;

import UPBEAT.Model.MapCell;
import UPBEAT.Model.Player;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.HashSet;
import java.util.Set;

@Controller
public class PController {
    MapCell map = new MapCell(5,5,1000000);
    Set<Player> players = new HashSet<>();

    @MessageMapping("/addPlayer")
    @SendTo("/topic/public")
    public Set<Player> addPlayer(String name, SimpMessageHeaderAccessor headerAccessor){
        headerAccessor.getSessionAttributes().put("username", name);
        Player player = new Player(name, map);
        players.add(player);
        return players;
    }

    @MessageMapping("/sendPlayer")
    @SendTo("/topic/public")
    public Set<Player> sendPlayer(String name, SimpMessageHeaderAccessor headerAccessor) {
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if (name.equals(username)) {
            return players;
        } else {
            return new HashSet<>(); // ส่งกลับเป็น Set ว่างเมื่อไม่พบผู้เล่น
        }
    }
}
