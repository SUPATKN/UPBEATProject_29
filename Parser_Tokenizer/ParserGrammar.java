import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class ParserGrammar {
    private Tokenizer token;
    private Tokenizer Pretoken;
    Map<String ,Integer> binding = new HashMap<>();
    LinkedList<AST> statement = new LinkedList<>();
    ParserGrammar(Tokenizer t){
        this.token = t;
    }
    public void ParsePlan() throws SyntaxError {
        while(token.hasNextToken()){
            statement.addAll(ParseStatement());
        }
        while(!statement.isEmpty()){
            statement.peekFirst().eval();
            statement.remove();
        }
        System.out.println(binding.values());
    }

    public LinkedList<AST> ParseStatement() throws SyntaxError {
        LinkedList<AST> localState = new LinkedList<>();
        if(token.getType().equals("identifier") || token.getType().equals("command")){
            localState.addAll(ParseCommand());
        }else if(token.getType().equals("blockState")) {
            token.consume();
            localState.addAll(ParseBlockStatement());
        }else if(token.getType().equals("ifState")){
            token.consume();
            localState.addAll(ParseIfStatement());
        }else if(token.getType().equals("whileState")){
            token.consume();
            localState.add(ParseWhileStatement());
        }else{
            throw new SyntaxError("not following grammar");
        }
        return localState;
    }

    public LinkedList<AST> ParseIfStatement() throws SyntaxError {
        AST calculateIf = null;
        LinkedList<AST> ifState = new LinkedList<>();
        token.consume("(");
        Expr E = parseE();
        token.consume(")");
        token.consume("then");
        LinkedList<AST> s1 = ParseStatement();
        token.consume("else");
        LinkedList<AST> s2 = ParseStatement();
        if(E.eval(binding) > 0){
            calculateIf = new IfStateNode(ifState,s1);
            calculateIf.eval();
        }else if(E.eval(binding) < 0){
            calculateIf = new IfStateNode(ifState,s2);
            calculateIf.eval();
        }
        return ifState;
    }
    public AST ParseWhileStatement() throws SyntaxError {
        token.consume("(");
        Expr E = parseE();
        token.consume(")");
        LinkedList<AST> s = new LinkedList<>();
        s.addAll(ParseStatement());
        AST w = new WhileNode(E,s,binding);
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

    public LinkedList<AST> ParseCommand() throws SyntaxError {
        LinkedList<AST> command = new LinkedList<>();
        if(token.getType().equals("identifier")){
            LinkedList<AST> assign = new LinkedList<>();
            while (token.getType().equals("identifier")){
                assign.add(ParseAssignCommand());
            }
            return assign;
        }else {
            command.add(ParseActionCommand());
        }
        return  command;
    }

    public AST ParseActionCommand() throws SyntaxError{
        AST Action = null;
        if(token.peek("move")){
            token.consume();
            Action = ParseMoveCommand();
        }
        return Action;
    }

    public AST ParseMoveCommand() throws SyntaxError{
        AST MoveCom = null;
        String direction = ParseDirection().eval();
        MoveCom = new MoveCommandNode(direction);
        return  MoveCom;
    }

    public AST ParseAssignCommand() throws SyntaxError{
        AST Assign = null;
        if(!token.previousType.equals("identifier")){
            throw new SyntaxError("Can't use a reserved word as identifier");
        }
        String variable = token.consume();
        Expr Identifier = new Variable(variable);
        Variable var = (Variable) Identifier;
        binding.put(var.name(), 0);
        token.consume("=");
        Expr E = parseE();
        Assign = new AssignCommandNode(Identifier,E,binding);
        return Assign;
    }
    public DirectionNode ParseDirection() throws SyntaxError {
        DirectionNode D = null;
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

    //T -> T*F | T/F | T%F | F
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

    //F -> P ^ F | P
    private Expr parseF() throws SyntaxError{
        Expr F = parseP();
        while(token.peek("^")){
            if(token.peek("^")){
                token.consume();
                F = new BinaryArithExpr(parseP(),"^",F);
            }
        }
        return F;

    }

    //P -> <number> | <identifier> | (E) | infoExpr
    private Expr parseP() throws SyntaxError{
        if(token.peek("-")) {
            token.consume();
            if (isNumber(token.peek())) {
                int neg = Integer.parseInt(token.consume());
                return new IntLit(-neg);
            }
        }
        if (isNumber(token.peek())) {
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
