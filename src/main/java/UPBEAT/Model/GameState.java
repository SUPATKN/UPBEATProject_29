package UPBEAT.Model;

import java.util.HashSet;
import java.util.Set;

public class GameState {
    private int m = 20;
    private int n = 15;
    private int init_plan_min = 5;
    private int init_plan_sec = 0;
    private int init_budget = 10000;
    private int init_center_dep = 100;
    private int plan_rev_min = 30;
    private int plan_rev_sec = 0;
    private int rev_cost = 100;
    private int max_dep = 1000000;
    private int interest_pct = 5;

    private Set<Player> allPlayer = new HashSet<>();
    private MapCell map;
    private Player turn;
    public GameState(){
        map = new MapCell(m,n,max_dep);
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
        map = new MapCell(m,n,max_dep);
    }

    public void addPlayer(String name){
        allPlayer.add(new Player(name,map,init_budget));
    }

    public Player[]  getAllPlayer(){
        return allPlayer.toArray(new Player[0]);
    }

    public Player getPlayer(String name){
        for(Player player : allPlayer){
            if(player.getName().equals(name)){
                return player;
            }
        }
        return null;
    }

    public GameState getGame(){
        return this;
    }

    public void setTurn(Player player){
        turn = player;
    }


}
