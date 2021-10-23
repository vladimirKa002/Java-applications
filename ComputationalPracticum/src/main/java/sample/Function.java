package sample;

import java.util.Stack;

/*
Function class - is a class that represents some expression inputted by user.
It utilizes Shunting-yard algorithm in order to parse terms and at the initialization step,
fills stack_1, that will be used for values calculation afterwards.
It has calculate method for substituting x and y values for getting the result.
 */
public class Function {
    private Stack<String> stack_1;
    private Stack<String> stack_2;

    private final String expression;

    // Initialization of a stack with expression terms
    public Function(String exp) throws MalformedExpressionException{
        // exp = exp.replaceAll(" ", "");
        while (exp.startsWith(" ")) exp = exp.substring(1);
        while (exp.endsWith(" ")) exp = exp.substring(0, exp.length()-1);
        exp = exp.toLowerCase();
        expression = exp;

        exp = checkTokenPowers(exp);

        exp = exp.replaceAll("\\(-", "(0-");
        exp = exp.replaceAll("\\( -", "(0-");
        if (exp.startsWith("-")) exp = "0" + exp;

        boolean prevNumber = false;

        stack_1 = new Stack<>();
        stack_2 = new Stack<>();
        try{
            for(int i = 0;i<exp.length();i++){
                String val = exp.charAt(i)+"";
                if (val.equals(" ")) continue;
                if (Character.isDigit(val.charAt(0))) {
                    if (prevNumber){
                        exp = exp.substring(0, i) + "*" + exp.substring(i);
                        i-=1;
                        continue;
                    }

                    int ind = getNum(exp, i);
                    val = exp.substring(i, ind);
                    i=ind-1;
                    stack_1.push(val);
                    prevNumber = true;
                }
                else if (val.equals("+") || val.equals("-")
                        || val.equals("*") || val.equals("/") || val.equals("^")){
                    while (!stack_2.isEmpty() && !stack_2.peek().equals("(") &&
                            (precedence(stack_2.peek()) >= precedence(val))){
                        stack_1.push(stack_2.pop());
                    }
                    stack_2.push(val);
                    prevNumber = false;
                }
                else if (val.equals("(")) {
                    if (prevNumber){
                        exp = exp.substring(0, i) + "*" + exp.substring(i);
                        i-=1;
                        continue;
                    }
                    stack_2.push(val);
                    prevNumber = false;
                }
                else if (val.equals("e")) {
                    if (prevNumber){
                        exp = exp.substring(0, i) + "*" + exp.substring(i);
                        i-=1;
                        continue;
                    }
                    stack_1.push(Math.E + "");
                    prevNumber = true;
                }
                else if (exp.startsWith("pi", i)) {
                    if (prevNumber){
                        exp = exp.substring(0, i) + "*" + exp.substring(i);
                        i-=1;
                        continue;
                    }
                    stack_1.push(Math.PI + "");
                    i++;
                    prevNumber = true;
                }
                else if (val.equals(")")) {
                    while (!stack_2.peek().equals("(")) stack_1.push(stack_2.pop());
                    stack_2.pop();
                    prevNumber = true;
                }
                else if (exp.startsWith("ln", i) || exp.startsWith("lg", i)
                        || exp.startsWith("tg", i)) {
                    if (prevNumber){
                        exp = exp.substring(0, i) + "*" + exp.substring(i);
                        i-=1;
                        continue;
                    }
                    stack_2.push(exp.substring(i, i+2));
                    i++;
                    prevNumber = false;
                }
                else if (exp.startsWith("ctg", i) || exp.startsWith("sin", i)
                        || exp.startsWith("cos", i) || exp.startsWith("sec", i)
                        || exp.startsWith("csc", i)) {
                    if (prevNumber){
                        exp = exp.substring(0, i) + "*" + exp.substring(i);
                        i-=1;
                        continue;
                    }
                    stack_2.push(exp.substring(i, i+3));
                    i+=2;
                    prevNumber = false;
                }
                else if (exp.startsWith("asin", i) || exp.startsWith("acos", i)
                        || exp.startsWith("atan", i) || exp.startsWith("sqrt", i)) {
                    if (prevNumber){
                        exp = exp.substring(0, i) + "*" + exp.substring(i);
                        i-=1;
                        continue;
                    }
                    stack_2.push(exp.substring(i, i+4));
                    i+=3;
                    prevNumber = false;
                }
                else if (val.equals("x") || val.equals("y") || val.equals("c")) {
                    if (prevNumber){
                        exp = exp.substring(0, i) + "*" + exp.substring(i);
                        i-=1;
                        continue;
                    }
                    stack_1.push(val);
                    prevNumber = true;
                }
            }
            while (!stack_2.isEmpty()) stack_1.push(stack_2.pop());

            while (!stack_1.isEmpty()) stack_2.push(stack_1.pop());
        }catch (Exception e){
            throw new MalformedExpressionException();
        }

        Stack<String> stack_temp = stack_1;
        stack_1 = stack_2;
        stack_2 = stack_temp;
    }

    // Setting constant C if necessary
    private double C;
    public void setC(double c){
        C = c;
    }

    // Calculate value of an expression for some particular x and y
    public double calculate(double x, double y) throws MalformedExpressionException{
        Stack<String> stack_1 = (Stack<String>) this.stack_1.clone();
        Stack<String> stack_2 = (Stack<String>) this.stack_2.clone();

        try{
            while (!stack_1.isEmpty()){
                String val = stack_1.pop();

                if (val.equals("+") || val.equals("-") || val.equals("*") || val.equals("/") || val.equals("^")){
                    double val1 = Double.parseDouble(stack_2.pop());
                    double val2 = Double.parseDouble(stack_2.pop());
                    if (val.equals("+")) stack_2.push(""+(val1+val2));
                    if (val.equals("-")) stack_2.push(""+(val2-val1));
                    if (val.equals("*")) stack_2.push(""+(val1*val2));
                    if (val.equals("/")) stack_2.push(""+(val2/val1));
                    if (val.equals("^")) stack_2.push(""+(Math.pow(val2, val1)));
                }
                else if (val.equals("x")){
                    stack_2.push(x + "");
                }
                else if (val.equals("y")){
                    stack_2.push(y + "");
                }
                else if (val.equals("c")){
                    stack_2.push(C + "");
                }
                else if (val.equals("e")){
                    stack_2.push(Math.E + "");
                }
                else if (val.equals("pi")){
                    stack_2.push(Math.PI + "");
                }
                else if (val.equals("ln")){
                    double v = Double.parseDouble(stack_2.pop());
                    stack_2.push("" + Math.log(v));
                }
                else if (val.equals("lg")){
                    double v = Double.parseDouble(stack_2.pop());
                    stack_2.push("" + Math.log10(v));
                }
                else if (val.equals("sin")){
                    double v = Double.parseDouble(stack_2.pop());
                    stack_2.push("" + Math.sin(v));
                }
                else if (val.equals("cos")){
                    double v = Double.parseDouble(stack_2.pop());
                    stack_2.push("" + Math.cos(v));
                }
                else if (val.equals("sec")){
                    double v = Double.parseDouble(stack_2.pop());
                    stack_2.push("" + 1/Math.cos(v));
                }
                else if (val.equals("csc")){
                    double v = Double.parseDouble(stack_2.pop());
                    stack_2.push("" + 1/Math.sin(v));
                }
                else if (val.equals("tg")){
                    double v = Double.parseDouble(stack_2.pop());
                    stack_2.push("" + Math.tan(v));
                }
                else if (val.equals("ctg")){
                    double v = Double.parseDouble(stack_2.pop());
                    stack_2.push("" + 1/Math.tan(v));
                }
                else if (val.equals("sqrt")){
                    double v = Double.parseDouble(stack_2.pop());
                    stack_2.push("" + Math.sqrt(v));
                }
                else if (val.equals("asin")){
                    double v = Double.parseDouble(stack_2.pop());
                    stack_2.push("" + Math.asin(v));
                }
                else if (val.equals("acos")){
                    double v = Double.parseDouble(stack_2.pop());
                    stack_2.push("" + Math.acos(v));
                }
                else if (val.equals("atan")){
                    double v = Double.parseDouble(stack_2.pop());
                    stack_2.push("" + Math.atan(v));
                }
                else {
                    stack_2.push(val);
                }
            }

            return Double.parseDouble(stack_2.pop());
        }
        catch (Exception e){
            throw new MalformedExpressionException();
        }
    }

    // Obtaining precedence of operator
    private int precedence(String token){
        if (token.equals("+") || token.equals("-")) return 1;
        if (token.equals("*") || token.equals("/")) return 2;
        if (token.equals("^")) return 3;
        return 4;
    }

    // Parsing number in the equation
    private int getNum(String exp, int ind){
        while (ind<exp.length() && (Character.isDigit(exp.charAt(ind)) || exp.charAt(ind)=='.'))
            ind++;
        return ind;
    }

    // Getter for the expression
    public String getExpression() {
        return expression;
    }

    // Method for checking trigonometric, ln, and lg functions for power sign presence
    private String checkTokenPowers(String exp){
        String[] tokesToCheck = {"ln", "lg", "sin", "cos", "tg", "ctg", "sec", "csc"};

        for (String token: tokesToCheck){
            while (exp.contains(token + "^")){
                exp = exp.substring(0, exp.indexOf(token + "^")) + "(" + exp.substring(exp.indexOf(token + "^"));
                int index = exp.indexOf(token + "^") + token.length() + 1;
                String val = null;
                if (Character.isDigit(exp.charAt(index))){
                    int ind = getNum(exp, index);
                    val = exp.substring(index, ind);
                    exp = exp.substring(0, index) + exp.substring(ind);
                }
                else if (exp.charAt(index) == '('){
                    int u = 1;
                    for (int i = index+1; i < exp.length();i ++){
                        if (exp.charAt(i) == '(') u++;
                        else if (exp.charAt(i) == ')') u--;
                        if (u == 0){
                            val = exp.substring(index, i+1);
                            exp = exp.substring(0, index) + exp.substring(i+1);
                            break;
                        }
                    }
                }
                index = exp.indexOf(token + "^") + token.length() + 1;
                int ind = 0;
                if (Character.isDigit(exp.charAt(index))){
                    ind = getNum(exp, index);
                }
                else if (exp.charAt(index) == '('){
                    int u = 1;
                    for (int i = index+1; i < exp.length();i ++){
                        if (exp.charAt(i) == '(') u++;
                        else if (exp.charAt(i) == ')') u--;
                        if (u == 0){
                            ind = i+1;
                            break;
                        }
                    }
                }
                exp = exp.substring(0, ind) + ")^(" + val + ")" + exp.substring(ind);
                exp = exp.replace(token + "^", token);
            }
        }

        return exp;
    }
}
