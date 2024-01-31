import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public interface AST {
    void eval() throws SyntaxError;
}

record WhileNode(Expr exp,LinkedList<AST> statement) implements AST{

    @Override
    public void eval() throws SyntaxError {
        Map<String ,Integer> binding = new HashMap<>();
        for(int i = 0 ;i<10 && exp.eval(binding) > 0 ;i++){
            statement.peekLast().eval();
        }
    }
}

record MoveCommandNode(String direction)implements AST{
    @Override
    public void eval() throws SyntaxError {
        System.out.println("move" + direction);
    }
}
