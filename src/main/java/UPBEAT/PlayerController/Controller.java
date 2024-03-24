package UPBEAT.PlayerController;

import UPBEAT.Model.*;
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
    public void addPlayer(@RequestBody String name) {
        if(!game.isNumeric(name)){
            name = name.substring(1, name.length() - 1);
        }
        game.addPlayer(name);
        Player player = game.getPlayer(name);
        messagingTemplate.convertAndSend("/topic/gameState", player);
    }

    @GetMapping("/allPlayer")
    public Set<Player> getAllPlayer(){
        return game.getAllPlayer();
    }

    @PostMapping("/checkName")
    public boolean checkName(@RequestBody String name){
        if(!game.isNumeric(name)){
            name = name.substring(1, name.length() - 1);
        }
        System.out.println("check name :" + name);
        if(game.Checkname(name)){
            messagingTemplate.convertAndSend("/topic/tryAgain", game.getAllPlayer());
            return true;
        }
        return false;
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
    public boolean PlayerReady(@RequestBody String name) {
        name = name.substring(1, name.length() - 1);
            game.getPlayer(name).setReady();
        messagingTemplate.convertAndSend("/topic/ready", game.getPlayer(name).isReady());
        messagingTemplate.convertAndSend("/topic/Allready", game.Allready());
        return game.getPlayer(name).isReady();
    }

    @PutMapping("/InitialPlan")
    public boolean PlayerInitial(@RequestBody String name) {
        name = name.substring(1, name.length() - 1);
        game.getPlayer(name).setInitial();
        messagingTemplate.convertAndSend("/topic/initial", game.getPlayer(name).isInitial());
        return game.getPlayer(name).isInitial();
    }

    @GetMapping("/allReady")
    public boolean AllPlayerReady(){
        return getGame().Allready();
    }

    @GetMapping("/allInitialPlan")
    public boolean AllInitial(){
        return game.Allinitial(messagingTemplate);
    }

    @PostMapping("/gamestart")
    public void startGame(){

        game.StartGame();
        messagingTemplate.convertAndSend("/topic/startgame", game.Allready());
    }

    @PutMapping("/ParsePlan")
    public void ParsePlan(@RequestBody String name) throws SyntaxError, InvalidMoveException {
        name = name.substring(1, name.length() - 1);
        System.out.println("Parse!!!");
        System.out.println("name it "+name);
        System.out.println("game turn name " + game.getTurn().getName());
        if(game.getTurn().getName().equals(name)){
            game.getPlayer(name).Plan(messagingTemplate);
            game.computeNextTurn();
            messagingTemplate.convertAndSend("/topic/nextPlayerTurn", game.Allready());
        }
    }

    @PutMapping("/Plan")
    public void Plan(@RequestBody PlanRequestBody requestBody) throws SyntaxError, InvalidMoveException {
        System.out.println(requestBody.getPlan());
        String name = requestBody.getName();
        String plan = requestBody.getPlan();
        game.getPlayer(name).setMyPlan(plan);
    }

}

