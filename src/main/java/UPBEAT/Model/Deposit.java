package UPBEAT.Model;

import lombok.Getter;

import java.lang.Math;
@Getter
public class Deposit {
    private int max_dep;
    private int interaest_pct;
    double r;
    private double Currentdep;

    public Deposit(double dep,int Max_dep,int interaest_pct){
        this.max_dep = Max_dep;
        this.Currentdep = dep;
        this.interaest_pct = interaest_pct;
        r = interaest_pct * Math.log10(Currentdep) * Math.log(1);
    }

    public void CalDeposit(int turn){
        int b = interaest_pct;
        double d = Currentdep;
        r = b * Math.log10(d) * Math.log(turn);
        double result = (d*r)/100;
        Currentdep += result;

        if(Currentdep >= max_dep){
            Currentdep = max_dep;
        }
    }

    public int getMax_dep(){
        return max_dep;
    }

    public double getInterestRatePer(){
        return r;
    }

    public void IncreaseDeposit(int cost){
        Currentdep += cost;
        if(Currentdep >= max_dep){
            Currentdep = max_dep;
        }
    }

    public double getCurrentdep(){
        return Currentdep;
    }

    public void DecreaseDeposit(int collect){
        Currentdep -= collect;
        if(Currentdep <= 0){
            Currentdep = 0;
        }
    }

}
