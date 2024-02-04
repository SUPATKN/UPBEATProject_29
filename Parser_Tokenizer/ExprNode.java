import java.util.Map;
import java.lang.Math;
import java.util.Random;

public interface ExprNode {
    void prettyPrint(StringBuilder s);
}
interface Expr extends ExprNode{
    int eval(Map<String,Integer> binding) throws SyntaxError;
}

record IntLit(int val) implements Expr {
    public int eval(Map<String, Integer> bindings) {
        return val;
    }

    public void prettyPrint(StringBuilder s) {
        s.append(val);
    }
}

record Variable(String name) implements Expr {
    public int eval(Map<String, Integer> bindings) throws SyntaxError {
        if(name.isEmpty()) return 0;
        if (bindings.containsKey(name)) return bindings.get(name);
        throw new SyntaxError("undefined variable: " + name);
    }
    public void prettyPrint(StringBuilder s) {
        s.append(name);
    }
}

record BinaryArithExpr(Expr left, String op, Expr right) implements Expr {
    public int eval(Map<String, Integer> bindings) throws SyntaxError {
        int lv = left.eval(bindings);
        int rv = right.eval(bindings);
        if (op.equals("+")) return lv + rv;
        if (op.equals("-")) return lv - rv;
        if (op.equals("*")) return lv * rv;
        if (op.equals("/")) return lv / rv;
        if (op.equals("%")) return lv % rv;
        if (op.equals("^")) return (int) Math.pow(lv,rv);

        throw new SyntaxError("unknown op: " + op);
    }
    public void prettyPrint(StringBuilder s) {
        s.append("(");
        left.prettyPrint(s);
        s.append(op);
        right.prettyPrint(s);
        s.append(")");
    }
}

record SpecialVariable(String name,Player player,CityCrew crew) implements Expr {
    public int eval(Map<String, Integer> bindings) throws SyntaxError {
        if(name.equals("rows")) return player.getMap().getRows();
        if (name.equals("cols")) return player.getMap().getCols();
        if(name.equals("currow")) return crew.getPosition().getRow();
        if(name.equals("curcol")) return crew.getPosition().getCol();
        if(name.equals("budget")) return player.getBudget();
        if(name.equals("deposit")) return (int) crew.getPosition().getDeposit().getCurrentdep();
        if(name.equals("int")) return (int) crew.getPosition().getDeposit().getInterestRatePer();
        if(name.equals("maxdeposit")) return crew.getPosition().getDeposit().getMax_dep();
        if(name.equals("random")){
            Random rand = new Random();
            return rand.nextInt(1000);
        }
        throw new SyntaxError("unknown special variable");
    }
    public void prettyPrint(StringBuilder s) {
        s.append(name);
    }
}
