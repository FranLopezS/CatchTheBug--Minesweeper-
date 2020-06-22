package es.riberadeltajo.buscaminas;

import android.content.Context;
import android.graphics.Color;

import androidx.appcompat.widget.AppCompatButton;

public class MiBoton extends AppCompatButton {
    private int xCoord;
    private int yCoord;

    public MiBoton(Context context, int x, int y) {
        super(context);
        this.xCoord=x;
        this.yCoord=y;
    }

    @Override
    public String toString() {
        return "MiBoton{" +
                "xCoord=" + xCoord +
                ", yCoord=" + yCoord +
                '}';
    }

    public int getxCoord() {
        return xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public void setyCoord(int yCoord) {
        this.yCoord = yCoord;
    }
}
