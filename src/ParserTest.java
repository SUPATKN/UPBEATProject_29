public class ParserTest {
    private Tokenizer token;
    ParserTest(Tokenizer t){
        this.token = t;
    }

    public int ParsePlan() throws SyntaxError {
        return ParseStatement();
    }

    public int ParseStatement() throws SyntaxError {
        return ParseCommand();
    }

    public int ParseCommand() throws SyntaxError {
        while (token.peek("done") || token.peek("relocate") || token.peek("move")
                || token.peek("invest") || token.peek("shoot") || token.peek("collect")){
            if(token.peek("done")){
                token.consume();
                return -1;
            }
            if(token.peek("move")){
                token.consume();
                return 2+ParseDirection();
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
}
