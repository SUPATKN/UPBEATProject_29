import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


public class ParserGrammar {
    private Tokenizer token;
    private Map<String ,Integer> binding = new HashMap<>();
    private LinkedList<AST> statement = new LinkedList<>();
    private LinkedList<AST> WhileState = new LinkedList<>();
    private CityCrew crew;
    public ParserGrammar(Tokenizer t,CityCrew crew){
        this.token = t;
        this.crew = crew;
    }


    public void setToken(Tokenizer token){
        this.token = token;
    }


    public void ParsePlan() throws SyntaxError, InvalidMoveException {
            while(token.hasNextToken()){
                statement.addAll(ParseStatement());
            }

            while(!statement.isEmpty()){
                if(statement.peekFirst() == null){
                    statement.remove();
                }
                if(statement.peekFirst() != null)statement.peekFirst().eval();
                if(!statement.isEmpty()){
                    statement.remove();
                }

            }

            System.out.println(binding.keySet());
            System.out.println(binding.values());

        }

    public LinkedList<AST> ParseStatement() throws SyntaxError, InvalidMoveException {
        LinkedList<AST> localState = new LinkedList<>();
        if(token.getType().equals("identifier") || token.getType().equals("command") || token.getType().equals("specialVariable")){
            localState.addAll(ParseCommand());
        }else if(token.getType().equals("blockState")) {
            token.consume();
            localState.addAll(ParseBlockStatement());
        }else if(token.getType().equals("ifState") || token.getType().equals("elseState")){
            token.consume();
            localState.add(ParseIfStatement());
        }else if(token.getType().equals("whileState")){
            token.consume();
            localState.add(ParseWhileStatement());
        }else{
            throw new SyntaxError("not following grammar");
        }
        return localState;
    }

    public AST ParseIfStatement() throws SyntaxError, InvalidMoveException {
        token.consume("(");
        Expr E = parseE();
        token.consume(")");
        token.consume("then");
        LinkedList<AST> s1 = ParseStatement();
        token.consume("else");
        LinkedList<AST> s2 = ParseStatement();
        return new IfStateNode(s1,s2,E,binding);
    }
    public AST ParseWhileStatement() throws SyntaxError, InvalidMoveException {
        token.consume("(");
        Expr E = parseE();
        token.consume(")");
        WhileState.addAll(ParseStatement());
        AST w = new WhileNode(E,WhileState,binding);
        return  w;
    }

    public LinkedList<AST> ParseBlockStatement() throws SyntaxError, InvalidMoveException {
        LinkedList<AST> b = new LinkedList<>();
        while(!token.peek("}")){
            b.addAll(ParseStatement());
        }
        token.consume("}");
        return b;
    }

    public LinkedList<AST> ParseCommand() throws SyntaxError {
        LinkedList<AST> command = new LinkedList<>();
        if(token.getType().equals("identifier") || token.getType().equals("specialVariable")){
            LinkedList<AST> assign = new LinkedList<>();
            assign.add(ParseAssignCommand());
            return assign;
        }else {
            command.add(ParseActionCommand());
        }
        return  command;
    }

    public AST ParseActionCommand() throws SyntaxError{
        AST Action = null;
        if(token.peek("done")){
            token.consume();
            Action = new DoneCommandNode(statement,WhileState);
        }else if(token.peek("relocate")){
            token.consume();
            Action = new RelocateNode(crew);
        }else if(token.peek("move")){
            token.consume();
            Action = ParseMoveCommand();
        }else if(token.peek("invest") || token.peek("collect")){
            Action = ParseRegionCommand();
        }else if(token.peek("shoot")){
            token.consume();
            Action = ParseAttackCommand();
        }
        return Action;
    }

    public AST ParseRegionCommand() throws SyntaxError{
        AST Region = null;
        if(token.peek("invest")){
            token.consume();
            Expr E = parseE();
            Region = new InvestCommandNode(E,binding,crew);
        }else if(token.peek("collect")){
            token.consume();
            Expr E = parseE();
            Region = new CollectCommandNode(E,binding);
        }
        return Region;
    }

    public AST ParseMoveCommand() throws SyntaxError{
        if(!token.getType().equals("direction")){
            throw new SyntaxError("Move command must follow by direction");
        }
        String direction = ParseDirection().eval();
        return new MoveCommandNode(direction,crew);
    }

    public AST ParseAttackCommand() throws SyntaxError {
        if(!token.getType().equals("direction")){
            throw new SyntaxError("Shoot command must follow by direction");
        }
        String direction = ParseDirection().eval();
        Expr E = parseE();
        return new AttackCommandNode(direction,E,binding);
    }

    public AST ParseAssignCommand() throws SyntaxError{
        AST Assign = null;
        if(token.getType().equals("command") || token.getType().equals("ifState") || token.getType().equals("direction") || token.getType().equals("whileState")){
            throw new SyntaxError("Can't use a reserved word as identifier");
        }
        if(token.getType().equals("specialVariable") || token.previousType.equals("elseState")){
            token.consume();
            token.consume("=");
            Expr E = parseE();
        }else if(!token.getType().equals("specialVariable")){
            String variable = token.consume();
            Expr Identifier = new Variable(variable);
            Variable var = (Variable) Identifier;
            if(!binding.containsKey(var.name())){
                binding.put(var.name(), 0);
            }
            token.consume("=");
            Expr E = parseE();
            Assign = new AssignCommandNode(Identifier,E,binding);
        }

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
        }else if(token.getType().equals("identifier")){
            return new Variable(token.consume());
        }else if(token.getType().equals("specialVariable")){
            return new SpecialVariable(token.consume(),crew.getPlayer(),crew);
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
