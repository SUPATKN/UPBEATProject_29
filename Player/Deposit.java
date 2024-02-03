import java.lang.Math;
public class Deposit {
    private int max_dep = 1000000;
    private int interaest_pct = 5;
    private double Currentdep;

    public Deposit(double dep){
        this.Currentdep = dep;
    }

    public void CalDeposit(int turn){
        int b = interaest_pct;
        int t = turn;
        double d = Currentdep;
        double r = b * Math.log10(d) * Math.log(t);
        double result = (d*r)/100;
        Currentdep += result;

        if(Currentdep >= max_dep){
            Currentdep = max_dep;
        }
    }

    public void InvestDeposit(int cost){
        Currentdep += cost;
        if(Currentdep >= max_dep){
            Currentdep = max_dep;
        }
    }

    public double getCurrentdep(){
        return Currentdep;
    }

    public void CollectDeposit(int collect){
        Currentdep -= collect;
        if(Currentdep <= 0){
            Currentdep = 0;
        }
    }

}
