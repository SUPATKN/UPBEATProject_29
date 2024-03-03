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
    @MessageMapping("/updatePlayers")
    @SendTo("/topic/players")
    public void updatePlayers() {
        // Call your existing method to fetch all players from the database
        // Notify clients about the updated players
    }
}
