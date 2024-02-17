package UPBEAT;

import java.util.LinkedList;
import java.util.Map;

public interface AST {
    void eval() throws SyntaxError, InvalidMoveException;

}

record WhileNode(Expr exp,LinkedList<AST> s,Map<String ,Integer> binding) implements AST{

    @Override
    public void eval() throws SyntaxError, InvalidMoveException {
        for(int i = 0 ;i<10000 && exp.eval(binding) > 0 ;i++){
            for (AST temp : s) {
                temp.eval();

            }
        }
    }
}

record MoveCommandNode(String direction,CityCrew crew,LinkedList<AST> statement,LinkedList<AST> w)implements AST{
    @Override
    public void eval() throws InvalidMoveException {
        if(crew.getPlayer().getBudget() > 0){
            crew.move(direction);
        }else{
            w.clear();
            statement.clear();
        }

        System.out.println("row = " +(crew.getPosition().getRow()+1));
        System.out.println("col = " +(crew.getPosition().getCol()+1));
    }
}


record DirectionNode(String direction){
    public String eval(){
        return direction;
    }
}
record AssignCommandNode(Expr identifier, Expr expression,Map<String ,Integer> binding) implements AST {
    @Override
    public void eval() throws SyntaxError {
        // Evaluate the expression and assign its value to the identifier
        int value = expression.eval(binding);
        if(identifier instanceof Variable) {
            Variable var = (Variable) identifier;
            binding.put(var.name(), value);
        } else {
            throw new SyntaxError("Invalid assignment target");
        }
    }
}

record IfStateNode(LinkedList<AST> s1,LinkedList<AST> s2,Expr exp,Map<String ,Integer> binding) implements AST{
    @Override
    public void eval() throws SyntaxError, InvalidMoveException {
        if(exp.eval(binding) > 0){
            for(AST temp : s1){
                temp.eval();
            }
        }else{
            for(AST temp : s2){
                temp.eval();
            }
        }
    }
}


record DoneCommandNode(LinkedList<AST> statement,LinkedList<AST> w)implements AST{
    @Override
    public void eval(){
        w.clear();
        statement.clear();
    }
}

record InvestCommandNode(Expr expr,Map<String ,Integer> binding,CityCrew crew)implements AST{
    @Override
    public void eval() throws SyntaxError {
        crew.Invest(expr.eval(binding));
        System.out.println(crew.getPosition().getDeposit().getCurrentdep());
    }
}

record CollectCommandNode(Expr expr,Map<String ,Integer> binding,CityCrew crew) implements AST{
    @Override
    public void eval() throws SyntaxError {
        crew.Collect(expr.eval(binding));
        System.out.println(crew.getPosition().getDeposit().getCurrentdep());
    }
}

record AttackCommandNode(String direction,Expr expr,Map<String ,Integer> binding,CityCrew crew)implements AST{
    @Override
    public void eval() throws SyntaxError, InvalidMoveException {
        crew.Shoot(direction,expr.eval(binding));
    }
}

record RelocateNode(CityCrew crew)implements AST{
    @Override
    public void eval(){
        crew.getPlayer().Relocate();
    }
}
