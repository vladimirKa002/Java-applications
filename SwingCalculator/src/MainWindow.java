import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.MathContext;

class MainWindow {
    private JLabel io = new JLabel("0");
    private JLabel currentNum = new JLabel("5");
    private JButton[] btn09 = new JButton[11];
    private JButton[] btnOper = new JButton[6];
    private String op = "+"; private boolean ravno=false; private boolean first=false;
    private double b = 0; private int k=0; private double res=0;
    private ActionListener actionListener = new TestActionListener();
    private int posOper = 0;

    MainWindow(){
        JFrame main = new JFrame("Calculator");
        main.setSize(225, 300);
        main.setResizable(false);
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.setLayout(null);
        main.setLocation(500, 500);
        io.setHorizontalAlignment(SwingConstants.CENTER);
        io.setSize(220, 50);

        setButtons();
        JPanel gridNum = new JPanel();
        GridLayout layout = new GridLayout(4, 3, 5, 5);
        gridNum.setLayout(layout);
        gridNum.setSize(160, 180);
        gridNum.setLocation(0, 50);

        JPanel grid2 = new JPanel();
        GridLayout layout2 = new GridLayout(5, 1, 5, 5);
        grid2.setLayout(layout2);
        grid2.setSize(50, 180);
        grid2.setLocation(165, 50);

        for (int i=0;i<4;i++) grid2.add(btnOper[i]);
        grid2.add(btnOper[5]);
        for (int i=7;i>0;i-=3) for (int j=0;j<3;j++) gridNum.add(btn09[i+j]);
        gridNum.add(btnOper[4]);
        gridNum.add(btn09[0]);
        gridNum.add(btn09[10]);

        GridLayout layout3 = new GridLayout(2, 1, 0, 0);
        JPanel gridResult= new JPanel();
        gridResult.setLayout(layout3);
        gridResult.add(io);
        gridResult.add(currentNum);

        main.add(gridResult);
        main.add(grid2);
        main.add(gridNum);
        main.setVisible(true);
    }

    private class TestActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            double ost;
            if (e.getActionCommand().equals("C")){
                op = "+";
                res = 0;
                b = 0;
                k = 0;
                io.setText("0");
                first=false;
            }
            else if (e.getActionCommand().equals("=")) {
                count();
                k = 0;
                first=false;
                ravno=true;
                ost = res%1;
                if (ost !=0 ) io.setText(res+"");
                else io.setText((int)res+"");
            }
            else if (e.getActionCommand().equals("+") | e.getActionCommand().equals("-") | e.getActionCommand().equals("*") | e.getActionCommand().equals("/")) {
                if (k==2 & !first) {
                    count();
                    b = 0;
                }
                op = e.getActionCommand();
            }
            else {
                if (k == 0) {
                    if (ravno) {
                        ravno=false;
                        res=0;
                        b=0;
                        io.setText("0");
                    }
                    if (io.getText().equals("0") & !e.getActionCommand().equals(".")) io.setText("");
                    io.setText(io.getText()+e.getActionCommand());
                    first=true;
                    res = Double.parseDouble(io.getText());
                }
                else if (k >= 1) {
                    if (k==1) {
                        b = 0;
                    }
                    first=false;
                    k=2;
                    if (e.getActionCommand().equals(".") & (io.getText().charAt(io.getText().length()-1) == '+' | io.getText().charAt(io.getText().length()-1) == '-' |
                            io.getText().charAt(io.getText().length()-1) == '*' | io.getText().charAt(io.getText().length()-1) == '/')){
                        io.setText(io.getText() + "0.");
                    }
                    else io.setText(io.getText() + e.getActionCommand());
                    b = Double.parseDouble(io.getText().substring(posOper+1));
                }
            }
            if (k==0 | k==2){
                k=1;
                posOper=io.getText().length();
                io.setText(io.getText()+op);
            }
            else if (k==1){
                io.setText(io.getText().substring(0, io.getText().length()-1)+op);
            }
         }
    }

    private void setButtons(){
        for (int i=0;i<6;i++) {
            btnOper[i] = new JButton();
            btnOper[i].addActionListener(actionListener);
        }
        btnOper[0].setText("+");
        btnOper[0].setActionCommand("+");
        btnOper[1].setText("-");
        btnOper[1].setActionCommand("-");
        btnOper[2].setText("*");
        btnOper[2].setActionCommand("*");
        btnOper[3].setText("/");
        btnOper[3].setActionCommand("/");
        btnOper[4].setText("=");
        btnOper[4].setActionCommand("=");
        btnOper[5].setText("C");
        btnOper[5].setActionCommand("C");

        for (int i=0;i<10;i++) {
            btn09[i] = new JButton();
            btn09[i].setText(i+"");
            btn09[i].setActionCommand(i+"");
            btn09[i].addActionListener(actionListener);
        }
        btn09[10] = new JButton();
        btn09[10].setText(".");
        btn09[10].setActionCommand(".");
        btn09[10].addActionListener(actionListener);
    }

    private void count(){
        BigDecimal result;
        switch (op) {
            case "+":
                result = new BigDecimal(res + b, MathContext.DECIMAL32);
                res = result.doubleValue();
                break;
            case "-":
                result = new BigDecimal(res - b, MathContext.DECIMAL32);
                res = result.doubleValue();
                break;
            case "*":
                result = new BigDecimal(res * b, MathContext.DECIMAL32);
                res = result.doubleValue();
                break;
            default:
                result = new BigDecimal(res / b, MathContext.DECIMAL32);
                res = result.doubleValue();
                break;
        }
    }
}
