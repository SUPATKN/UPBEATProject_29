package UPBEAT.Model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.HashSet;
import java.util.Set;

public class GameState {
    private int m = 5;
    private int n = 5;
    private int init_plan_min = 5;
    private int init_plan_sec = 0;
    private int init_budget = 10000;
    private int init_center_dep = 100;
    private int plan_rev_min = 30;
    private int plan_rev_sec = 0;
    private int rev_cost = 100;
    private int max_dep = 1000000;
    private int interest_pct = 5;

    @Setter
    private boolean receive = false;

    private boolean allReady = true;
    private boolean allIninitial = true;
    private boolean newTurn = false;

    private int currentNumPlayer = 1;
    @Getter
    private int TotalTurn = 1;
    private int Pturn = 1;

    private Set<Player> allPlayer = new HashSet<>();
    @Getter
    private MapCell map;
    @Getter
    private Player turn;
    public GameState(){
        map = new MapCell(m,n,max_dep,interest_pct);
    }

    public GameState(int m,int n,int init_plan_min,int init_plan_sec,int init_budget,int init_center_dep,int plan_rev_min,
                     int plan_rev_sec,int rev_cost,int max_dep,int interest_pct){
        this.m = m;
        this.n = n;
        this.init_plan_min = init_plan_min;
        this.init_plan_sec = init_plan_sec;
        this.init_budget = init_budget;
        this.init_center_dep = init_center_dep;
        this.plan_rev_min = plan_rev_min;
        this.plan_rev_sec = plan_rev_sec;
        this.rev_cost = rev_cost;
        this.max_dep = max_dep;
        this.interest_pct = interest_pct;
        map = new MapCell(m,n,max_dep,interest_pct);
    }

    public void addPlayer(String name){
        Player newPlayer = new Player(name,map,init_budget);
        if(allPlayer.isEmpty()){
            newPlayer.setHost();
        }
        newPlayer.setMyTurn(currentNumPlayer);
        currentNumPlayer++;
        allPlayer.add(newPlayer);
    }

    public Set<Player>  getAllPlayer(){
        return allPlayer;
    }

    public Player getPlayer(String name){
        for(Player player : allPlayer){
            if(player.getName().equals(name)){
                return player;
            }
        }
        return null;
    }

    public boolean Allready(){
        for(Player player : allPlayer){
            if(!player.isReady()){
                return false;
            }
        }
        System.out.println("All player ready status : " + allReady);
        return allReady;
    }

    public boolean Allinitial(SimpMessagingTemplate messagingTemplate){
        for(Player player : allPlayer){
            if(!player.isInitial()){
                return false;
            }
        }
        System.out.println("All player initial status : " + allIninitial);
        if(allIninitial){
            messagingTemplate.convertAndSend("/topic/StartRun",this);
        }

        return allIninitial;
    }

    public void StartGame(){
        System.out.println("Game Start!!!");
        computeNextTurn();
    }

    public void computeNextTurn(){
        if(newTurn){
            IncreaseTurn();
            newTurn = false;
        }
        for(Player player : allPlayer) {
            if (player.getMyTurn() == Pturn) {
                turn = player;
            }
        }
        Pturn++;
        if(Pturn > allPlayer.size()){
            Pturn = 1;
            newTurn = true;
        }
    }

    public boolean Checkname(String name){
        return allPlayer.contains(getPlayer(name));
    }

    public void IncreaseTurn(){
        TotalTurn++;
        for(Player player : allPlayer) {
            player.sendTurnToDep(TotalTurn);
        }
    }

    public boolean isNumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    public void CheckLoseGame(){
        for(Player player : allPlayer) {
            if(!player.isAlive()){
                allPlayer.remove(player);
                System.out.println(player.getName() + " has lose and remove from game");
            }
        }
    }



}
