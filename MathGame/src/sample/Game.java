package sample;

import java.util.Random;

public class Game {
    private int Score =0;
    private int index=0;
    private int[] records=null;
    private String mode;
    private Random rnd = new Random();
    private int num1=0; private int num2=0; private int correctAns = 0;
    int bounds=5;

    Game(String mode, int index, int[] records){
        this.records=records;
        this.index = index;
        this.mode = mode;
        count();
    }

    public void count(){
        if (Score%5==0 && Score!=0) bounds*=2;
        if (mode.equals("+")){
            num1 = rnd.nextInt(bounds);
            num2 = rnd.nextInt(bounds);
            correctAns=num1+num2;
        }
        else if (mode.equals("-")){
            correctAns = rnd.nextInt(bounds);
            num2 = rnd.nextInt(bounds);
            num1=correctAns+num2;
        }
        else if (mode.equals("*")){
            num1 = rnd.nextInt(bounds);
            num2 = rnd.nextInt(bounds);
            correctAns=num1*num2;
        }
        else if (mode.equals("/")){
            correctAns = rnd.nextInt(bounds)+1;
            num2 = rnd.nextInt(bounds)+1;
            num1=correctAns*num2;
        }
    }

    public int getCorrect(){
        return correctAns;
    }

    public void addScore(){
        Score+=1;
    }

    public int getScore() {return Score;}

    public int[] getNums() {
        int a[] = new int[2];
        a[0]=num1; a[1]=num2;
        return a;
    }

    public int[] getRecords() {
        return records;
    }

    public int getRec() {
        return records[index];
    }

    public void setRec(int rec) {
        records[index] = rec;
    }
}
