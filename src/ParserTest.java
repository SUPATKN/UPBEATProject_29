public class ParserTest {
    private Tokenizer token;
    ParserTest(Tokenizer t){
        this.token = t;
    }

    public int ParsePlan() throws SyntaxError {
        return ParseStatement();
    }

    public int ParseStatement() throws SyntaxError {
        if(token.getType().equals("whileState")){
            token.consume();
            ParseWhileStatement();
        }else if(token.getType().equals("blockState")){
            token.consume();
            ParseBlockStatement();
        }else if(token.getType().equals("identifier") || token.getType().equals("command")){
            token.consume();
            ParseCommand();
        }
        return 0;
    }

    public int ParseWhileStatement() throws SyntaxError {
        token.consume("(");
        parseE();
        token.consume(")");
        ParseStatement();
        System.out.println("do while");
        return 8888;
    }

    public int ParseBlockStatement() throws SyntaxError {
        ParseStatement();
        token.consume("}");
        System.out.println("do block");
        return 123124;
    }

    public int ParseCommand() throws SyntaxError {
        int m = 0;
        while (token.peek("done") || token.peek("relocate") || token.peek("move")
                || token.peek("invest") || token.peek("shoot") || token.peek("collect")){
            if(token.peek("done")){
                token.consume();
                return -1;
            }
            if(token.peek("move")){
                token.consume();
                m += m + 2+ParseDirection();
                return m;
            }
        }
        return -99999;
    }

    public int ParseDirection() throws SyntaxError {
        while (token.peek("up") || token.peek("down") || token.peek("upright")
                || token.peek("downright") || token.peek("upleft") || token.peek("downleft")) {
            if(token.peek("up")){
                token.consume();
                return 1;
            }
            if(token.peek("upright")){
                token.consume();
                return 2;
            }
            if(token.peek("downright")){
                token.consume();
                return 3;
            }
            if(token.peek("down")){
                token.consume();
                return 4;
            }
            if(token.peek("downleft")){
                token.consume();
                return 5;
            }
            if(token.peek("upleft")){
                token.consume();
                return 6;
            }
        }
        return 0;
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
