import java.util.LinkedList;

public class ParserTest {
    private Tokenizer token;
    LinkedList<AST> statement = new LinkedList<>();
    ParserTest(Tokenizer t){
        this.token = t;
    }

    public void ParsePlan() throws SyntaxError {
        ParseStatement();
    }

    public LinkedList<AST> ParseStatement() throws SyntaxError {
        if(token.getType().equals("whileState")){
            token.consume();
            statement.add(ParseWhileStatement());
        }else if(token.getType().equals("blockState")) {
            token.consume();
            statement.addAll(ParseBlockStatement());
        }else if(token.getType().equals("identifier") || token.getType().equals("command")){
            statement.add(ParseCommand());
        }
        return statement;
    }

    public AST ParseWhileStatement() throws SyntaxError {
        token.consume("(");
        Expr E = parseE();
        token.consume(")");
        ParseStatement();
        AST w = new WhileNode(E, statement);
        return  w;
    }

    public LinkedList<AST> ParseBlockStatement() throws SyntaxError {
        LinkedList<AST> b = ParseStatement();
        System.out.println("dd");
        token.consume("}");
        return b;
    }

    public AST ParseCommand() throws SyntaxError {
        AST command = null;
        while (token.peek("done") || token.peek("relocate") || token.peek("move")
                || token.peek("invest") || token.peek("shoot") || token.peek("collect")){
//            if(token.peek("done")){
//                token.consume();
//                return -1;
//            }
            if(token.peek("move")){
                token.consume();
                 command = new MoveCommandNode(token.consume());
            }
        }
        return command;
    }

//    public AST ParseDirection() throws SyntaxError {
//        while (token.peek("up") || token.peek("down") || token.peek("upright")
//                || token.peek("downright") || token.peek("upleft") || token.peek("downleft")) {
//            if(token.peek("up")){
//                token.consume();
//                return 1;
//            }
//            if(token.peek("upright")){
//                token.consume();
//                return 2;
//            }
//            if(token.peek("downright")){
//                token.consume();
//                return 3;
//            }
//            if(token.peek("down")){
//                token.consume();
//                return 4;
//            }
//            if(token.peek("downleft")){
//                token.consume();
//                return 5;
//            }
//            if(token.peek("upleft")){
//                token.consume();
//                return 6;
//            }
//        }
//        return 0;
//    }

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
