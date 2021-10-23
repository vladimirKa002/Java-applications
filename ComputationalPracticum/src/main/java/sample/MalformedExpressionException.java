package sample;

// Exception that is caused by inappropriate format of inputted equation
public class MalformedExpressionException extends Exception{
    @Override
    public String getMessage(){
        return "Malformed expression!";
    }

    @Override
    public String toString(){
        return "Malformed expression!";
    }
}
