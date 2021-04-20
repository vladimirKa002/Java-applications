import javax.swing.*;
import java.math.BigDecimal;
import java.math.MathContext;

public class Calc {
    private JLabel io;
    private JLabel currentNum;
    private JButton MC;
    private JButton MR;
    private JButton MS;
    private JButton mPl;
    private JButton mMi;
    private JButton delet;
    private JButton CE;
    private JButton C;
    private JButton plusMin;
    private JButton sqrt;
    private JButton div;
    private JButton pers;
    private JButton mult;
    private JButton divOnThis;
    private JButton subs;
    private JButton b7;
    private JButton b8;
    private JButton b9;
    private JButton b4;
    private JButton b5;
    private JButton b6;
    private JButton b3;
    private JButton b2;
    private JButton b1;
    private JButton b0;
    private JButton[] numButtons = new JButton[10];
    private JButton point;
    private JButton plus;
    private JButton getVal;
    private JPanel panel;
    private JLabel memoryActive;
    private JButton[] operButtons = new JButton[4];

    private String expression = "";
    private boolean newNum = false;
    private String operator="";
    private String lastNum="";
    private boolean changeOper = false;
    private boolean error = false;

    private String memory="0";
    private String func="";
    private boolean memoryEntered = false;

    private Calc(){
        memoryActive.setVisible(false);
        {
            numButtons[0] = b0;
            numButtons[1] = b1;
            numButtons[2] = b2;
            numButtons[3] = b3;
            numButtons[4] = b4;
            numButtons[5] = b5;
            numButtons[6] = b6;
            numButtons[7] = b7;
            numButtons[8] = b8;
            numButtons[9] = b9;
        }
        for (JButton numButton : numButtons) {
            numButton.addActionListener(e -> {
                changeOper = false;
                if (!lastNum.equals("")){
                    operator="";
                    lastNum="";
                    expression = "";
                }
                if (newNum | memoryEntered) {
                    currentNum.setText("0");
                    newNum = false;
                    memoryEntered=false;
                }
                if (currentNum.getText().equals("0")| currentNum.getText().equals("ERR") | !func.equals("")) currentNum.setText("");
                currentNum.setText(currentNum.getText()+numButton.getText());
                func="";
            });
        }
        point.addActionListener(e -> {
            changeOper = false;
            if (currentNum.getText().contains(".") & !newNum) return;
            if (!lastNum.equals("")){
                operator="";
                lastNum="";
                expression = "";
            }
            if (newNum | memoryEntered) {
                currentNum.setText("0");
                newNum = false;
                memoryEntered=false;
            }
            if (currentNum.getText().equals("ERR") | !func.equals("")) currentNum.setText("0");
            currentNum.setText(currentNum.getText()+".");
            func="";
        });
        {
            operButtons[0] = plus;
            operButtons[1] = subs;
            operButtons[2] = mult;
            operButtons[3] = div;
        }
        for (JButton opButton : operButtons) {
            opButton.addActionListener(e -> {
                if (changeOper) {
                    io.setText(io.getText().substring(0, io.getText().length()-1)+opButton.getText());
                    expression=expression.substring(0, expression.length()-1)+opButton.getText();
                    return;
                }
                if (!lastNum.equals("")){
                    changeOper = true;
                    operator="";
                    lastNum="";
                    if (!func.equals("")) expression=currentNum.getText();
                    else expression = expression.substring(0, expression.length()-1);
                    currentNum.setText(expression);
                    expression+=opButton.getText();
                    io.setText(expression);
                }
                else {
                    changeOper = true;
                    res();
                    if (error){
                        func="";
                        error = false;
                        newNum = false;
                        operator="";
                        lastNum="";
                        changeOper = false;
                        io.setText("");
                        expression="";
                        currentNum.setText("ERR");
                        return;
                    }
                    io.setText(io.getText()+opButton.getText());
                    currentNum.setText(expression);
                    expression+=opButton.getText();
                }
            });
        }
        getVal.addActionListener(e -> {
            if (lastNum.equals("")) {
                if (io.getText().equals("")) return;
                operator = io.getText().substring(io.getText().length()-1);
                lastNum=currentNum.getText();
            }
            else {
                io.setText(currentNum.getText());
                currentNum.setText(lastNum);
            }
            res();
            if (error){
                func="";
                error = false;
                newNum = false;
                operator="";
                lastNum="";
                changeOper = false;
                io.setText("");
                expression="";
                currentNum.setText("ERR");
                return;
            }
            currentNum.setText(expression);
            expression+=operator;
            io.setText("");
            newNum=true;
        });
        C.addActionListener(e -> {
            func="";
            newNum = false;
            operator="";
            lastNum="";
            changeOper = false;
            io.setText("");
            expression="";
            currentNum.setText("0");
        });
        CE.addActionListener(e -> {
            func="";
            newNum=true;
            currentNum.setText("0");
        });
        delet.addActionListener(e -> {
            if (!lastNum.equals("") | changeOper | memoryEntered | currentNum.getText().equals("ERR") | !func.equals("")) return;
            if (currentNum.getText().length()>=1) currentNum.setText(currentNum.getText().
                    substring(0, currentNum.getText().length()-1));
            if (currentNum.getText().length()==0)  currentNum.setText("0");
            if (currentNum.getText().equals("-")) currentNum.setText("0");
        });
        plusMin.addActionListener(e -> {
            if (currentNum.getText().charAt(0)=='-') currentNum.setText(currentNum.getText().substring(1));
            else currentNum.setText("-"+currentNum.getText());
            if (!lastNum.equals("")) {
                if (currentNum.getText().charAt(0)!='-') expression = expression.substring(1);
                else expression = "-"+expression;
            }
        });
        mPl.addActionListener(e -> {
            try{
                Double.parseDouble(currentNum.getText());
            }catch (Exception ee){
                func="";
                error = false;
                newNum = false;
                operator="";
                lastNum="";
                changeOper = false;
                io.setText("");
                expression="";
                currentNum.setText("ERR");
                return;
            }
            memoryEntered=true;
            BigDecimal mem  = new BigDecimal(Double.parseDouble(memory)+
                    Double.parseDouble(currentNum.getText()), MathContext.DECIMAL32);
            memory = mem.toPlainString();
            if (mem.compareTo(new BigDecimal(0))==0) {
                memoryActive.setVisible(false);
                return;
            }
            memoryActive.setVisible(true);
        });
        MS.addActionListener(e -> {
            try{
                Double.parseDouble(currentNum.getText());
            }catch (Exception ee){
                func="";
                error = false;
                newNum = false;
                operator="";
                lastNum="";
                changeOper = false;
                io.setText("");
                expression="";
                currentNum.setText("ERR");
                return;
            }
            memoryEntered=true;
            BigDecimal mem  = new BigDecimal(Double.parseDouble(currentNum.getText()), MathContext.DECIMAL32);
            memory = mem.toPlainString();
            if (mem.compareTo(new BigDecimal(0))==0) {
                memoryActive.setVisible(false);
                return;
            }
            memoryActive.setVisible(true);
        });
        mMi.addActionListener(e -> {
            try{
                Double.parseDouble(currentNum.getText());
            }catch (Exception ee){
                func="";
                error = false;
                newNum = false;
                operator="";
                lastNum="";
                changeOper = false;
                io.setText("");
                expression="";
                currentNum.setText("ERR");
                return;
            }
            memoryEntered=true;
            BigDecimal mem  = new BigDecimal(Double.parseDouble(memory)-
                    Double.parseDouble(currentNum.getText()), MathContext.DECIMAL32);
            memory = mem.toPlainString();
            if (mem.compareTo(new BigDecimal(0))==0) {
                memoryActive.setVisible(false);
                return;
            }
            memoryActive.setVisible(true);
        });
        MR.addActionListener(e -> {
            changeOper = false;
            memoryEntered=true;
            currentNum.setText(memory);
            memoryEntered=true;
        });
        MC.addActionListener(e -> {
            memory="0";
            memoryActive.setVisible(false);
        });
        sqrt.addActionListener(e -> {
            changeOper = false;
            try {
                if (func.equals("")) {
                    func="sqrt("+currentNum.getText()+")";
                }
                else func="sqrt("+func+")";
                BigDecimal result = new BigDecimal(Math.sqrt(Double.parseDouble(currentNum.getText())), MathContext.DECIMAL32);
                currentNum.setText(result.toPlainString());

            }
            catch (Exception ex){
                func="";
                error = false;
                newNum = false;
                operator="";
                lastNum="";
                changeOper = false;
                io.setText("");
                expression="";
                currentNum.setText("ERR");
            }
        });
        divOnThis.addActionListener(e -> {
            changeOper = false;
            try {
                if (func.equals("")) {
                    func="(1/"+currentNum.getText()+")";
                }
                else func="(1/"+func+")";
                BigDecimal result = new BigDecimal(1/Double.parseDouble(currentNum.getText()), MathContext.DECIMAL32);
                currentNum.setText(result.toPlainString());
            }
            catch (Exception ex){
                func="";
                error = false;
                newNum = false;
                operator="";
                lastNum="";
                changeOper = false;
                io.setText("");
                expression="";
                currentNum.setText("ERR");
            }
        });
        pers.addActionListener(e -> {
            changeOper = false;
            try {
                if (expression.equals("")){
                    currentNum.setText("0");
                    return;
                }
                BigDecimal result = new BigDecimal((Double.parseDouble(currentNum.getText())/100)*
                        Double.parseDouble(expression.substring(0, expression.length()-1)), MathContext.DECIMAL32);
                currentNum.setText(result.toPlainString());
            }
            catch (Exception ex){
                func="";
                error = false;
                newNum = false;
                operator="";
                lastNum="";
                changeOper = false;
                io.setText("");
                expression="";
                currentNum.setText("ERR");
            }
        });
    }

    public static void main(String[] args) {
        JFrame window = new JFrame("Calculator");
        window.setContentPane(new Calc().panel);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocation(450, 200);
        window.setResizable(false);
        window.setSize(350, 300);
        window.setVisible(true);
    }

    private void res (){
        try{
            BigDecimal result = null;
            expression+=currentNum.getText();
            if (!func.equals("")){
                if (currentNum.getText().charAt(0)=='-') io.setText(io.getText()+"-"+func);
                else io.setText(io.getText()+func);
                func="";
            }
            else io.setText(io.getText()+currentNum.getText());
            while (expression.contains("*") | expression.contains("/")){
                int kMult=-1;
                if (expression.contains("*")) kMult =  expression.indexOf('*');
                int kDiv=-1;
                if (expression.contains("/")) kDiv =  expression.indexOf('/');
                if ((kMult!=-1 & kMult<kDiv) | (kDiv==-1)){
                    String num1 = expression.substring(0, expression.indexOf('*'));
                    int i = num1.length()-1;
                    for (;i>=0;i--){
                        if (num1.charAt(i) == '+' | num1.charAt(i) == '-' | num1.charAt(i) == '*' | num1.charAt(i) == '/') break;
                    }
                    double a1 = Double.parseDouble(num1.substring(i+1));
                    i++;
                    String num2 = expression.substring(expression.indexOf('*')+1);
                    int i1 = 0;
                    int  i2 =0;
                    while(num2.charAt(0)=='-') {
                        expression=expression.substring(0, expression.indexOf('*')+1)+expression.substring(expression.indexOf('*')+2);
                        expression=expression.substring(0, i)+"-"+expression.substring(i);
                        i++;
                        num2=num2.substring(1);
                        i2++;
                    }
                    for (;i1<num2.length();i1++){
                        if (num2.charAt(i1) == '+' | num2.charAt(i1) == '-' | num2.charAt(i1) == '*' | num2.charAt(i1) == '/') break;
                    }
                    double a2 = Double.parseDouble(num2.substring(0, i1));
                    result = new BigDecimal(a1*a2, MathContext.DECIMAL32);
                    double d = result.doubleValue();
                    String str2 = expression.substring(0, i);
                    int y = expression.indexOf('*')+i1+1;
                    String str3 = expression.substring(y);
                    expression = str2+d+ str3;
                }
                else {
                    String num1 = expression.substring(0, expression.indexOf('/'));
                    int i = num1.length()-1;
                    for (;i>=0;i--){
                        if (num1.charAt(i) == '+' | num1.charAt(i) == '-' | num1.charAt(i) == '*' | num1.charAt(i) == '/') break;
                    }
                    double a1 = Double.parseDouble(num1.substring(i+1));
                    i++;
                    String num2 = expression.substring(expression.indexOf('/')+1);
                    int i1 = 0;
                    int  i2 =0;
                    while(num2.charAt(0)=='-') {
                        expression=expression.substring(0, expression.indexOf('/')+1)+expression.substring(expression.indexOf('/')+2);
                        expression=expression.substring(0, i)+"-"+expression.substring(i);
                        i++;
                        num2=num2.substring(1);
                        i2++;
                    }
                    for (;i1<num2.length();i1++){
                        if (num2.charAt(i1) == '+' | num2.charAt(i1) == '-' | num2.charAt(i1) == '*' | num2.charAt(i1) == '/') break;
                    }
                    double a2 = Double.parseDouble(num2.substring(0, i1));
                    result = new BigDecimal(a1/a2, MathContext.DECIMAL32);
                    double d = result.doubleValue();
                    String str2 = expression.substring(0, i);
                    int y = expression.indexOf('/')+i1+1;
                    String str3 = expression.substring(y);
                    expression = str2+d+ str3;
                }
            }
            while (expression.length()>=2 && expression.substring(0, 2).equals("--")) expression = expression.replace("--", "");
            expression = expression.replaceAll("--", "+");
            expression = expression.replaceAll("\\+-", "-");
            while (expression.substring(1).contains("-")){
                expression = expression.replaceAll("--", "+");
                expression = expression.replaceAll("\\+-", "-");
                if (expression.charAt(0)=='-') expression="_"+expression.substring(1);
                String num1 = expression.substring(0, expression.indexOf('-'));
                int i = num1.length()-1;
                for (;i>=0;i--){
                    if (num1.charAt(i) == '+') {
                        break;
                    }
                    else if (num1.charAt(i) == '-' ) {
                        i--;
                        break;
                    }
                }
                if (num1.charAt(0)=='_') num1="-"+num1.substring(1);
                double a1 = Double.parseDouble(num1.substring(i+1));
                i++;
                String num2 = expression.substring(expression.indexOf('-')+1);
                int i1 = 0;
                for (;i1<num2.length();i1++){
                    if (num2.charAt(i1) == '+' | num2.charAt(i1) == '-' | num2.charAt(i1) == '*' | num2.charAt(i1) == '/') break;
                }
                double a2 = Double.parseDouble(num2.substring(0, i1));
                result = new BigDecimal(a1-a2, MathContext.DECIMAL32);
                double d = result.doubleValue();
                String str2 = expression.substring(0, i);
                String str3 = expression.substring(expression.indexOf('-')+1+i1);
                expression = str2+d+str3;
                if (expression.charAt(0)=='_') expression="-"+expression.substring(1);
            }
            while (expression.contains("+")){
                String num1 = expression.substring(0, expression.indexOf('+'));
                int i = num1.length()-1;
                for (;i>=0;i--){
                    if (num1.charAt(i) == '+') {
                        break;
                    }
                    else if (num1.charAt(i) == '-' ) {
                        i--;
                        break;
                    }
                }
                double a1 = Double.parseDouble(num1.substring(i+1));
                i++;
                String num2 = expression.substring(expression.indexOf('+')+1);
                int i1 = 0;
                for (;i1<num2.length();i1++){
                    if (num2.charAt(i1) == '+' | num2.charAt(i1) == '-' | num2.charAt(i1) == '*' | num2.charAt(i1) == '/') break;
                }
                double a2 = Double.parseDouble(num2.substring(0, i1));
                result = new BigDecimal(a1+a2, MathContext.DECIMAL32);
                double d = result.doubleValue();

                String str2 = expression.substring(0, i);
                String str3 = expression.substring(expression.indexOf('+')+1+i1);
                expression = str2+d+ str3;
            }
            newNum = true;
            if (!expression.contains(".")) return;
            int i = expression.indexOf('.')+1;
            for (; i<expression.length();i++){
                if (expression.charAt(i)!='0') return;
            }
            expression=expression.substring(0, expression.indexOf('.'));
        }
        catch (Exception e) {
            error=true;
        }
    }
}