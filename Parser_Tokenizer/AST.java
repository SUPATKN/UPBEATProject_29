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

record MoveCommandNode(String direction,CityCrew crew)implements AST{
    @Override
    public void eval() throws InvalidMoveException {
        crew.move(direction);
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

record IfStateNode(LinkedList<AST> statement,LinkedList<AST> s1) implements AST{
    @Override
    public void eval(){
        statement.addAll(s1);
    }
}

record DoneCommandNode(LinkedList<AST> statement)implements AST{
    @Override
    public void eval(){
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

record CollectCommandNode(Expr expr,Map<String ,Integer> binding)implements AST{
    @Override
    public void eval() throws SyntaxError {
        System.out.println("Collect " + expr.eval(binding));
    }
}

record AttackCommandNode(String direction,Expr expr,Map<String ,Integer> binding)implements AST{
    @Override
    public void eval() throws SyntaxError {
        System.out.println("Shoot " + direction + " " +  expr.eval(binding));
    }
}
