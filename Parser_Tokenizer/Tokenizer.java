import java.util.NoSuchElementException;

import static java.lang.Character.*;
import static java.lang.Character.isLetter;

public class Tokenizer {
    private String src, next;
    private int pos;
    private String type = " ";
    protected String previousType;
    public Tokenizer(String src) throws SyntaxError {
        this.src = src;  pos = 0;
        computeNext();
        previousType = this.type;
    }

    public boolean peek(String s) {
        if (!hasNextToken()) return false;
        return peek().equals(s);
    }

    public void consume(String s) throws SyntaxError {
        if (peek(s))
            consume();
        else
            throw new SyntaxError(s + " expected");
    }
    public boolean hasNextToken() {
        return next != null;
    }

    public void checkNextToken() {
        if (!hasNextToken()) throw new NoSuchElementException("no more tokens");
    }

    public String peek() {
        checkNextToken();
        return next;
    }

    public String consume() throws SyntaxError {
        checkNextToken();
        previousType = this.type;
        String result = next;
        computeNext();
        return result;
    }

    public String getType(){
        return type;
    }



    public void assignType(){
        if (next.equals("if") || next.equals("then")){
            type = "ifState";
        }else if(next.equals("else")){
            type = "elseState";
        }
        else if(next.equals("while")){
            type = "whileState";
        }else if(next.equals("done") || next.equals("relocated") ||
                next.equals("move") || next.equals("invest") || next.equals("collect")
                || next.equals("shoot")) {
                type = "command";
        }else if(next.equals("up") || next.equals("down") ||
                next.equals("upright") || next.equals("downright") || next.equals("downleft")
                || next.equals("upleft")) {
            type = "direction";
        }else if(next.equals("rows") || next.equals("cols") || next.equals("currow") || next.equals("curcol") || next.equals("budget")
            || next.equals("deposit") || next.equals("int") || next.equals("maxdeposit") || next.equals("random")){
                type = "specialVariable";
        }else{
                type = "identifier";
        }
    }

    private void computeNext() throws SyntaxError{
        StringBuilder s = new StringBuilder();
        if(src.length() >= 2){
            boolean b = src.charAt(0) == '-';
            boolean p = src.charAt(1) == ' ';
            if(p && b) throw new SyntaxError("can't space minus and number");
        }
        while (pos < src.length() && isSpace(src.charAt(pos))) pos++;  // ignore whitespace
        if (pos == src.length())
        { next = null;  return; }  // no more tokens
        char c = src.charAt(pos);
        if (isDigit(c)) {  // start of number
            s.append(c);
            for (pos++; pos < src.length() && isDigit(src.charAt(pos)); pos++){
                s.append(src.charAt(pos));
            }
            type = "number";
        }else if (c == '+' || c == '-' || c == '*' || c == '/' || c == '%' || c == '(' || c == ')' || c == '=' || c=='^') {
            s.append(c);pos++;
            type = "operator";
        }else if(isLetter(c)){
            s.append(c);
            for (pos++; pos < src.length() && isLetter(src.charAt(pos)); pos++){
                s.append(src.charAt(pos));
            }

        }else if(c == '{' || c== '}'){
            s.append(c);
            pos++;
            type = "blockState";
        }else {
            throw new IllegalArgumentException("unknown character: " + c);
        }
        next = s.toString();
        assignType();
    }
}
