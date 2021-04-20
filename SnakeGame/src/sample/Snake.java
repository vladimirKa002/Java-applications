package sample;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Snake {
    private GraphicsContext gc;
    private int size = 10;          // Длина змейка
    private final double bod_s = 8; // Размер блока, из которого состоит змейка
    private int direction = 6;      // Направление движения
    private final ArrayList<double []> body_xy = new ArrayList<>(size); // Массив блоков

    Snake(GraphicsContext gc){
        this.gc = gc;
        int x = 80;
        int y = 250;
        for (int i = 0; i < size; i++) {
            body_xy.add(new double[]{x+=bod_s, y});
        }
    }

    public void move(){
        double [] neck = body_xy.get(body_xy.size()-1);
        body_xy.remove(0);
        if (direction==2) {
            body_xy.add(new double [] {neck[0], neck[1]+bod_s});
        }
        else if (direction==4) {
            body_xy.add(new double [] {neck[0]-bod_s, neck[1]});
        }
        else if (direction==6) {
            body_xy.add(new double [] {neck[0]+bod_s, neck[1]});
        }
        else if (direction==8) {
            body_xy.add(new double [] {neck[0], neck[1]-bod_s});
        }
    }

    public boolean is_intersected(){
        Rectangle2D head = getBoundaryOfPart(body_xy.size()-1);
        for (int i = 0;i<body_xy.size()-1;i++) {
            if (getBoundaryOfPart(i).intersects(head)) return true;
        }
        return false;
    }

    public boolean is_intersected_point(Point point){
        Rectangle2D head = getBoundaryOfPart(body_xy.size()-1);
        return point.getBoundary().intersects(head);
    }

    private Rectangle2D getBoundaryOfPart(int p) {
        double [] pos = body_xy.get(p);
        return new Rectangle2D(pos[0],pos[1],
                bod_s, bod_s);
    }

    public boolean is_out_of_bounds(){
        double [] head_pos = body_xy.get(body_xy.size()-1);
        double left_b = 75;
        double right_b = 425-bod_s;
        double top_b = 75;
        double bottom_b = 425-bod_s;

        if (head_pos[0]>left_b && head_pos[0]<right_b && head_pos[1]>top_b && head_pos[1]<bottom_b) return false;
        return true;
    }

    public void grow(){
        double [] neck = body_xy.get(body_xy.size()-1);
        if (direction==2) {
            body_xy.add(new double [] {neck[0], neck[1]+bod_s});
        }
        else if (direction==4) {
            body_xy.add(new double [] {neck[0]-bod_s, neck[1]});
        }
        else if (direction==6) {
            body_xy.add(new double [] {neck[0]+bod_s, neck[1]});
        }
        else if (direction==8) {
            body_xy.add(new double [] {neck[0], neck[1]-bod_s});
        }
    }

    public void set_dir(int direction){
        if (direction==2 && this.direction==8) {
            return;
        }
        else if (this.direction==2 && direction==8) {
            return;
        }
        else if (this.direction==4 && direction==6) {
            return;
        }
        else if (this.direction==6 && direction==4) {
            return;
        }
        this.direction = direction;
    }

    public void render() {
        gc.setFill(Color.GREEN);
        for (double[] ints : body_xy) {
            gc.fillRect(ints[0], ints[1], bod_s, bod_s);
        }
    }
}
