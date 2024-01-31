import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public interface AST {
    void eval() throws SyntaxError;
}

record WhileNode(Expr exp,LinkedList<AST> s) implements AST{

    @Override
    public void eval() throws SyntaxError {
        Map<String ,Integer> binding = new HashMap<>();
        for(int i = 0 ;i<10000 && exp.eval(binding) > 0 ;i++){
            s.peekFirst().eval();
            s.remove();
            if(s.isEmpty()) break;
        }
    }
}

record MoveCommandNode(String direction)implements AST{
    @Override
    public void eval() throws SyntaxError {
        System.out.println("move " + direction);
    }
}


record DirectionNode(String direction){
    public String eval() throws SyntaxError{
        return direction;
    }
}