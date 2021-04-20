package sample;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Random;

public class Point {
    private GraphicsContext gc;
    private double [] point;
    private final double point_size = 16;

    Point (GraphicsContext gc){
        this.gc = gc;
        Random rnd = new Random();
        point = new double[]{rnd.nextInt(350-(int)point_size)+75, rnd.nextInt(350-(int)point_size)+75};
    }

    public Rectangle2D getBoundary() {
        return new Rectangle2D(point[0],point[1],
                point_size, point_size);
    }

    void render() {
        gc.setFill(Color.RED);
        gc.fillRect(point[0], point[1], point_size, point_size);
    }
}
