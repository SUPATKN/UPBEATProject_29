import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public interface AST {
    void eval() throws SyntaxError;

}

record WhileNode(Expr exp,LinkedList<AST> s,Map<String ,Integer> binding) implements AST{

    @Override
    public void eval() throws SyntaxError {
        for(int i = 0 ;i<10000 && exp.eval(binding) > 0 ;i++){
            for (AST temp : s) {
                temp.eval();
            }
        }
    }
}

record MoveCommandNode(String direction)implements AST{
    @Override
    public void eval() throws SyntaxError {
        System.out.println("move " + direction);
//        player.move(direction);
    }
}


record DirectionNode(String direction){
    public String eval() throws SyntaxError{
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
    public void eval() throws SyntaxError {
        statement.addAll(s1);
    }
}
