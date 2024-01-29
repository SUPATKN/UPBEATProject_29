import java.util.NoSuchElementException;

import static java.lang.Character.*;
import static java.lang.Character.isLetter;

public class Tokenizer {
    private String src, next,type;  private int pos;
    public Tokenizer(String src) throws SyntaxError {
        this.src = src;  pos = 0;
        computeNext();
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
        String result = next;
        computeNext();
        return result;
    }

    public String getType(){
        return type;
    }

    public void assignType(){
        if (next.equals("if")){
            type = "ifState";
        }else if(next.equals("while")){
            type = "whileState";
        }else if(next.matches("^[a-z]+$") || next.matches("^[A-Z]+$")){
            type = "identifier";
        }else{
            type = "command";
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
        }else if (c == '+' || c == '-' || c == '*' || c == '/' || c == '%' || c == '(' || c == ')') {
            s.append(c);  pos++;
            type = "operator";
        }else if(isLetter(c)){
            s.append(c);
            for (pos++; pos < src.length() && isLetter(src.charAt(pos)); pos++){
                s.append(src.charAt(pos));
            }

        }else if(c == '{' || c== '}'){
            s.append(c);
            type = "blockState";
        }else {
            throw new IllegalArgumentException("unknown character: " + c);
        }
        next = s.toString();
        assignType();
    }
}
