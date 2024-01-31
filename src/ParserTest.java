import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class ParserTest {
    private Tokenizer token;
    LinkedList<AST> statement = new LinkedList<>();
    ParserTest(Tokenizer t){
        this.token = t;
    }
    public void ParsePlan() throws SyntaxError {
        statement.addAll(ParseStatement());
        while(!statement.isEmpty()){
            statement.peekFirst().eval();
            statement.remove();
        }
    }

    public LinkedList<AST> ParseStatement() throws SyntaxError {
        LinkedList<AST> localState = new LinkedList<>();
        if(token.getType().equals("identifier") || token.getType().equals("command")){
            localState.add(ParseCommand());
        }else if(token.getType().equals("ifState")){
            token.consume();
            localState.addAll(ParseIfStatement());
        }else if(token.getType().equals("blockState")) {
            token.consume();
            localState.addAll(ParseBlockStatement());
        }else if(token.getType().equals("whileState")){
            token.consume();
            localState.add(ParseWhileStatement());
        }
        return localState;
    }

    public LinkedList<AST> ParseIfStatement() throws SyntaxError {
        Map<String ,Integer> binding = new HashMap<>();
        LinkedList<AST> ifState = new LinkedList<>();
        token.consume("(");
        Expr E = parseE();
        token.consume(")");
        if(E.eval(binding) > 0){
            token.consume("then");
            ifState.addAll(ParseStatement());
        }else if(E.eval(binding) < 0){
            while(!token.peek("else")){
                token.consume();
            }
            token.consume("else");
            ifState.addAll(ParseStatement());
        }
        return ifState;
    }
    public AST ParseWhileStatement() throws SyntaxError {
        token.consume("(");
        Expr E = parseE();
        token.consume(")");
        LinkedList<AST> s = ParseStatement();
        AST w = new WhileNode(E,s);
        return  w;
    }

    public LinkedList<AST> ParseBlockStatement() throws SyntaxError {
        LinkedList<AST> b = new LinkedList<>();
        while(!token.peek("}")){
            b.addAll(ParseStatement());
        }
        token.consume("}");
        return b;
    }

    public AST ParseCommand() throws SyntaxError {
        AST command = null;
//        while (token.peek("done") || token.peek("relocate") || token.peek("move")
//                || token.peek("invest") || token.peek("shoot") || token.peek("collect")){
////            if(token.peek("done")){
////                token.consume();
////                return -1;
////            }
            if(token.peek("move")){
                token.consume();
                String direction = ParseDirection().eval();
                command = new MoveCommandNode(direction);
            }
//        }
        return command;
    }

    public DirectionNode ParseDirection() throws SyntaxError {
        DirectionNode D = null;
        while (token.peek("up") || token.peek("down") || token.peek("upright")
                || token.peek("downright") || token.peek("upleft") || token.peek("downleft")) {
            if(token.peek("up")){
                token.consume();
                D = new DirectionNode("up");
            }
            if(token.peek("upright")){
                token.consume();
                D = new DirectionNode("upright");
            }
            if(token.peek("downright")){
                token.consume();
                D = new DirectionNode("downright");
            }
            if(token.peek("down")){
                token.consume();
                D = new DirectionNode("down");
            }
            if(token.peek("downleft")){
                token.consume();
                D = new DirectionNode("downleft");
            }
            if(token.peek("upleft")){
                token.consume();
                D = new DirectionNode("upleft");
            }
        }
        return D;
    }

    //E -> E+T | E-T | T
    private Expr parseE() throws SyntaxError {
        Expr E = parseT();
        while (token.peek("+") || token.peek("-")){
            if(token.peek("+")){
                token.consume();
                E = new BinaryArithExpr(E,"+",parseT());
            }else if(token.peek("-")){
                token.consume();
                E = new BinaryArithExpr(E,"-",parseT());
            }
        }
        return E;
    }

    //T -> T*F | T/F | F
    private Expr parseT() throws SyntaxError{
        Expr T = parseF();
        while(token.peek("*") || token.peek("/") || token.peek("%")){
            if(token.peek("*")){
                token.consume();
                T = new BinaryArithExpr(T,"*",parseF());
            }else if(token.peek("/")){
                token.consume();
                T = new BinaryArithExpr(T,"/",parseF());
            }else if(token.peek("%")){
                token.consume();
                T = new BinaryArithExpr(T,"%",parseF());
            }
        }
        return T;
    }

    //F -> n | x | (E)
    private Expr parseF() throws SyntaxError{
        if(token.peek("-")) {
            token.consume();
            if (isNumber(token.peek())) {
                int neg = Integer.parseInt(token.consume());
                return new IntLit(-neg);
            }
        }else if (isNumber(token.peek())) {
            int num = Integer.parseInt(token.consume());
            return new IntLit(num);
        }else if(token.peek().matches("^[a-z]+$") || token.peek().matches("^[A-Z]+$")){
            return new Variable(token.consume());
        }else if(token.peek("(") || token.peek(")")){
            token.consume("(");
            Expr F = parseE();
            token.consume(")");
            return F;
        }else if(token.peek("+") || token.peek("*") || token.peek("/") || token.peek("%")){
            throw new SyntaxError("Please check your input can't start with '"+ token.peek() + "'");
        }else{
            throw new SyntaxError("Please check your input "+ token.peek() +" is not accept");
        }
        return new IntLit(-99999999);
    }

    public static boolean isNumber(String str)
    {
        for (char c : str.toCharArray())
        {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }
}
