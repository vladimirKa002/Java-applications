package sample;

// Exception that is caused by connection, request, and etc error while running WolframAlpha
public class WolframAlphaException extends Exception{
    @Override
    public String getMessage(){
        return "Something went wrong while running WolframAlpha!";
    }

    @Override
    public String toString(){
        return "Something went wrong while running WolframAlpha!";
    }
}
