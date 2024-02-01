import java.util.Map;
import java.lang.Math;
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
