package sample;

public class GetData {
    boolean  err = false;
    private double acceler = 0;
    private int speed = 0;
    private int price = 0;
    private int power = 0;
    private String name;
    private String [] result = new String[2];
    String [] getCar (String name){
        this.name = name;
        data();
        if (!err) result[0] = new String(this.name+"\n\r"+"Max. speed: "+ speed + " kph \n\r" +
                "Acceleration (100 kph): "+ acceler + " s \n\r" +
                "Max. power: " + power + " hp \n\r" + "Price: "+ price + " $");
        else {
            result[0] = new String("We don't know this car");
            result[1] = new String("ha");
        }
        return result;
    }
    private void data (){
        if (this.name. equalsIgnoreCase("Porsche 911 Carrera S")){
            this.name = new String("Porsche 911 Carrera S");
            speed  = 308;
            acceler = 3.5;
            price = 128900;
            power = 450;
            err = false;
            result[1] = new String("images/Porsche 911 Carrera S.png");
        }

        else if (this.name. equalsIgnoreCase("Porsche 911 Carrera S Cabriolet")){
            this.name = new String("Porsche 911 Carrera S Cabriolet");
            speed = 308;
            acceler = 3.5;
            price = 128900;
            power = 450;
            err = false;
            result[1] = new String("images/Porsche 911 Carrera S Cabriolet.png");
        }

        else if (this.name. equalsIgnoreCase("Porsche 911 Carrera 4S")){
            this.name = new String("Porsche 911 Carrera 4S");
            speed = 306;
            acceler = 3.4;
            price = 136400;
            power = 450;
            err = false;
            result[1] = new String("images/Porsche 911 Carrera 4S.png");
        }

        else if (this.name. equalsIgnoreCase("Porsche 911 Carrera 4S Cabriolet")){
            this.name = new String("Porsche 911 Carrera 4S Cabriolet");
            speed = 304;
            acceler = 3.4;
            price = 136400;
            power = 450;
            err = false;
            result[1] = new String("images/Porsche 911 Carrera 4S Cabriolet.png");
        }

        else if (this.name. equalsIgnoreCase("Porsche 911 Speedster")){
            this.name = new String("Porsche 911 Speedster");
            speed = 310;
            acceler = 4.0;
            price = 344200;
            power = 510;
            err = false;
            result[1] = new String("images/Porsche 911 Speedster.png");
        }

        else if (this.name. equalsIgnoreCase("Porsche 911 GT3 RS")){
            this.name = new String("Porsche 911 GT3 RS");
            speed = 312;
            acceler=3.2;
            price = 211700;
            power = 520;
            err = false;
            result[1] = new String("images/Porsche 911 GT3 RS.png");
        }

        else if (this.name. equalsIgnoreCase("Porsche 911 GT2 RS")){
            this.name = new String("Porsche 911 GT2 RS");
            speed = 340;
            acceler = 2.8;
            price = 305600;
            power = 700;
            err = false;
            result[1] = new String("images/Porsche 911 GT2 RS.png");
        }

        else if (this.name. equalsIgnoreCase("Porsche 918 Spyder")){
            this.name = new String("Porsche 918 Spyder");
            speed=345;
            acceler = 2.6;
            price =733700;
            power = 887;
            err = false;
            result[1] = new String("images/Porsche 918 Spyder.png");
        }

        else {
            err = true;
        }
    }
}