package sample;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;


public class BuildMatrix {
    static Button btnOk = new Button("OK");

    public Scene build (int width, int height, boolean symb, String form) {
        HBox contMat = new HBox(7);
        contMat.setAlignment(Pos.CENTER);
        VBox contMainSceneMatrix = new VBox(10, contMat, btnOk);
        contMainSceneMatrix.setAlignment(Pos.CENTER);

        int [][] a = new int [height][width];
        int k = 1; // a = 97
        if (form.equals("Horizontal")) {
            for (int i = 0;i<height;i++){
                for(int j = 0;j<width;j++){
                    a[i][j] = k;
                    k++;
                }
            }
        }
        else if (form.equals("Vertical")) {
            for (int i = 0;i<width;i++){
                for(int j = 0;j<height;j++){
                    a[j][i] = k;
                    k++;
                }
            }
        }
        else if (form.equals("Diagonal")) {
            int i =0; int j =0;
            while (i<width & j<height & a[height-1][width-1]==0) {
                for (;j>=0 & i<width;j--,i++){
                    a[j][i] = k;
                    k++;
                }
                j++; i--;
                if (i<width-1) i++;
                else if (j<height-1) j++;
                else if (j==0 & i==width-1) j++;
                if (a[height-1][width-1]!=0 & i==width-1 & j==height-1) break;
                for (;j<height & i>=0;j++,i--){
                    a[j][i] = k;
                    k++;
                }
                j--; i++;
                if (j<height-1) j++;
                else if (i<width-1) i++;
                else if (i==0 & j==height-1) i++;
            }
        }
        else if (form.equals("Spiral inside")) {
            int i =0; int j =0;
            k=0;
            int bot = 1; int top = height; int left = 0; int righ = width;
            int y = width * height;
            while (y>=k){
                for (;i<righ;i++) {
                    a[j][i] = ++k;
                }
                if (k==y) break;
                i--;
                j++;
                for (;j<top;j++) {
                    a[j][i] = ++k;
                }
                if (k==y) break;
                j--;
                i--;
                for (;i>=left;i--) {
                    a[j][i] = ++k;
                }
                if (k==y) break;
                i++;
                j--;
                for (;j>=bot;j--) {
                    a[j][i] = ++k;
                }
                if (k==y) break;
                j++; i++; bot++; righ--; left++; top--;
            }
        }
        else if (form.equals("Spiral outside")) {
            int i =0; int j =0;
            int y = width * height;
            k=y+1;
            int bot = 1; int top = height; int left = 0; int righ = width;
            while (0<k){
                for (;i<righ;i++) {
                    a[j][i] = --k;
                }
                if (k==1) break;
                i--;
                j++;
                for (;j<top;j++) {
                    a[j][i] = --k;
                }
                if (k==1) break;
                j--;
                i--;
                for (;i>=left;i--) {
                    a[j][i] = --k;
                }
                if (k==1) break;
                i++;
                j--;
                for (;j>=bot;j--) {
                    a[j][i] = --k;
                }
                if (k==1) break;
                j++; i++; bot++; righ--; left++; top--;
            }
        }
        else if (form.equals("Sphere")) {
            int i =0; int j =0;
            int bot = 0; int top = height; int left = 0; int righ = width;
            int y = Math.min(width, height)/2 + Math.min(width, height)%2;
            while (y>=k){
                for (;i<righ;i++) a[j][i] = k;
                i--;
                for (;j<top;j++) a[j][i] = k;
                j--;
                for (;i>=left;i--) a[j][i] = k;
                i++;
                for (;j>=bot;j--) a[j][i] = k;
                j+=2; i++; k++; bot++; righ--; left++; top--;
            }
        }
        if (symb) {
            for (int i = 0;i<width;i++){
                VBox stolb = new VBox();
                contMat.getChildren().add(stolb);
                for(int j = 0;j<height;j++){
                    stolb.getChildren().add(new Label("" + (char)(a[j][i]+96)));
                }
                stolb.setAlignment(Pos.CENTER);
            }
        }
        else {
            for (int i = 0;i<width;i++){
                VBox stolb = new VBox();
                contMat.getChildren().add(stolb);
                for(int j = 0;j<height;j++){
                    stolb.getChildren().add(new Label(a[j][i] + ""));
                }
                stolb.setAlignment(Pos.CENTER);
            }
        }
        Scene Scn = new Scene(contMainSceneMatrix, 600, 500);
        return Scn;
    }
}