package es.riberadeltajo.buscaminas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener,
        MiDialogo1.OnRespuestaListener, MiDialogoSkins.OnRespuestaListener {

    Tableros tablero;
    int minasRestantes;
    int bugActive = R.drawable.mar21;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startGame(8, 8, 10);
    }

    private void startGame(int x, int y, int n_minas) {
        Display display= getWindowManager().getDefaultDisplay();
        Point size= new Point();
        display.getSize(size);
        int width= size.x;
        int height= size.y;

        tablero = new Tableros(x, y);
        minasRestantes = n_minas;

        androidx.gridlayout.widget.GridLayout gridLayout = findViewById(R.id.tablero);
        //GridLayout gridLayout = new GridLayout(this);
        dibujarTablero(gridLayout, width, height);
    }


    private void dibujarTablero(GridLayout gridLayout, int width, int height) {
        int[][] mapa = new int[tablero.getN_botones_ancho()][tablero.getN_botones_alto()];
        gridLayout.removeAllViews(); // Limpiar el grid layout
        gridLayout.setRowCount(tablero.getN_botones_ancho());
        gridLayout.setColumnCount(tablero.getN_botones_alto());
        // Limpiar matriz si está escrita.
        int[][] mapaLimpio = new int[tablero.getN_botones_ancho()][tablero.getN_botones_alto()];
        tablero.setMapa(mapaLimpio);
        // Meter 10 hipotenochas.
        int minasDentro = 0;
        while(minasDentro<minasRestantes){
            int randomX = (int)(Math.random()*(tablero.getN_botones_ancho()-1));
            int randomY = (int)(Math.random()*(tablero.getN_botones_alto()-1));
            if(mapa[randomX][randomY] != -1){
                mapa[randomX][randomY] = -1;
                comprobarAlrededor(randomX, randomY, mapa);
                minasDentro++;
            }
        }
        int cc = 0;
        boolean color = false;
        for(int i=0; i<tablero.getN_botones_alto(); i++){
            for(int p=0; p<tablero.getN_botones_ancho(); p++){
                if(mapa[p][i] == -1){ // Mina
                    MiBoton imgButton = new MiBoton(this, p, i);
                    imgButton.setxCoord(p);
                    imgButton.setyCoord(i);
                    imgButton.setId(cc);
                    imgButton.setOnClickListener(this);
                    imgButton.setOnLongClickListener(this);
                    imgButton.setLayoutParams(new ViewGroup.LayoutParams(
                            (width/tablero.getN_botones_ancho()), (height/tablero.getN_botones_alto())-10
                    ));
                    gridLayout.addView(imgButton, cc);
                } else { // No mina
                    MiBoton btn = new MiBoton(this, p, i);
                    btn.setxCoord(p);
                    btn.setyCoord(i);
                    btn.setId(cc);
                    btn.setOnClickListener(this);
                    btn.setOnLongClickListener(this);
                    btn.setLayoutParams(new ViewGroup.LayoutParams(
                            (width/tablero.getN_botones_ancho()), (height/tablero.getN_botones_alto())-10
                    ));
                    gridLayout.addView(btn, cc);
                }
                if(color)
                    color = false;
                else
                    color = true;
                cc++;
            }
            //System.out.println("");
        }
        tablero.setMapa(mapa);
        /*for(int i=0; i<tablero.getN_botones_ancho(); i++){
            for(int p=0; p<tablero.getN_botones_alto(); p++){
                System.out.print(tablero.getValor(i,p)+"\t\t");
            }
            System.out.println("");
        }*/
    }

    private void comprobarAlrededor(int x, int y, int[][] mapa) {
        try {
            if(mapa[x-1][y+1] != -1)
                mapa[x-1][y+1]++;
        } catch(ArrayIndexOutOfBoundsException e) {

        }

        try {
            if(mapa[x][y+1] != -1)
                mapa[x][y+1]++;
        } catch(ArrayIndexOutOfBoundsException e) {

        }

        try {
            if(mapa[x+1][y+1] != -1)
                mapa[x+1][y+1]++;
        } catch(ArrayIndexOutOfBoundsException e) {

        }

        try {
            if(mapa[x-1][y] != -1)
                mapa[x-1][y]++;
        } catch(ArrayIndexOutOfBoundsException e) {

        }

        try {
            if(mapa[x+1][y] != -1)
                mapa[x+1][y]++;
        } catch(ArrayIndexOutOfBoundsException e) {

        }

        try {
            if(mapa[x-1][y-1] != -1)
                mapa[x-1][y-1]++;
        } catch(ArrayIndexOutOfBoundsException e) {

        }

        try {
            if(mapa[x][y-1] != -1)
                mapa[x][y-1]++;
        } catch(ArrayIndexOutOfBoundsException e) {

        }

        try {
            if(mapa[x+1][y-1] != -1)
                mapa[x+1][y-1]++;
        } catch(ArrayIndexOutOfBoundsException e) {

        }

    }

    private void comprobarValor(int id) {
        try{
            GridLayout gridLayout = findViewById(R.id.tablero);
            int[][] mapa = tablero.getMapa();
            MiBoton btn = gridLayout.findViewById(id);
            if(!btn.getText().equals("0")) {
                int x = btn.getxCoord();
                int y = btn.getyCoord();
                btn.setBackgroundColor(Color.BLUE);
                if(mapa[x][y] == -1) {
                    btn.setBackground(getDrawable(bugActive));
                } else {
                    btn.setText(Integer.toString(mapa[x][y]));
                }
                descubrirAlrededor(mapa, x, y);
            }
        } catch(Exception e) {
            System.err.println("No se ha podido y ya");
        }
    }

    @Override
    public void onClick(View v) {
        MiBoton b = (MiBoton) v;
        comprobarValor(b.getId());
    }

    private void descubrirAlrededor(int[][] mapa, int x, int y) {
        System.out.println(x + " " + y);
        switch (mapa[x][y]) {
            case -1:
                derrota();
                break; //perder
            case 0:
                checkRound(x, y); // comprueba todas las casillas de alrededor.
                break;
            default:
                break; // es un nº del 1 al 9
        }
    }

    private void checkRound(int x, int y) {
        //if(y != tablero.getN_botones_alto()-1)
            comprobarValor(getIdByCoords(x, (y-1))); // muestra el de arriba
        //if(y != 0)
            comprobarValor(getIdByCoords(x, (y+1))); // abajo
        if(x != tablero.getN_botones_ancho()-1)
            comprobarValor(getIdByCoords((x+1), y)); // der
        if(x != 0)
            comprobarValor(getIdByCoords((x-1), y)); // izq
        if(x != 0)
            comprobarValor(getIdByCoords(x-1, y+1)); //izq abajo
        if(x != 0)
            comprobarValor(getIdByCoords(x-1, y-1)); //izq arriba
        if(x != tablero.getN_botones_ancho()-1)
            comprobarValor(getIdByCoords(x+1, y+1)); //der abajo
        if(x != tablero.getN_botones_ancho()-1)
            comprobarValor(getIdByCoords(x+1, y-1)); //der arriba
    }

    private int getIdByCoords(int x, int y) {
        return ((tablero.getN_botones_alto()*y) + x);
    }

    private void derrota() {
        GridLayout gridLayout = findViewById(R.id.tablero);
        for(int y=0; y<tablero.getN_botones_alto(); y++){
            for(int x=0; x<tablero.getN_botones_ancho(); x++){
                int id = getIdByCoords(x, y);
                MiBoton btn = (MiBoton) gridLayout.getChildAt(id);
                btn.setEnabled(false);
                if(tablero.getValor(x, y) == -1){
                    btn.setBackground(getDrawable(bugActive));
                } else {
                    btn.setBackgroundColor(Color.GRAY);
                }

            }
        }
        Toast.makeText(this, "You lose! Try it again.", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onLongClick(View v) {
        MiBoton b = (MiBoton) v;
        b.setBackgroundColor(Color.BLUE);
        b.setBackground(getDrawable(R.drawable.red));
        b.setEnabled(false);
        if(tablero.getValor(b.getxCoord(), b.getyCoord()) == -1){
            minasRestantes--;
            if(minasRestantes == 0)
                victoria();
            else
                Toast.makeText(this, "Good! " + minasRestantes + " bugs left.", Toast.LENGTH_LONG).show();
        } else {
            derrota();
        }
        return true;
    }

    private void victoria() {
        GridLayout gridLayout = findViewById(R.id.tablero);
        for(int y=0; y<tablero.getN_botones_alto(); y++){
            for(int x=0; x<tablero.getN_botones_ancho(); x++){
                int id = getIdByCoords(x, y);
                MiBoton btn = (MiBoton) gridLayout.getChildAt(id);
                btn.setEnabled(false);
            }
        }
        Toast.makeText(this, "Congratulations, you've won!", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_opciones, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.difficult:
                openDifficultMenu();
                break;
            case R.id.bug:
                openSkinsMenu();
                break;
        }
        return true;
    }

    private void openDifficultMenu() {
        MiDialogo1 d = new MiDialogo1(this);
        d.show(getSupportFragmentManager(), "Mi diálogo");
    }

    private void openSkinsMenu() {
        MiDialogoSkins d = new MiDialogoSkins(this);
        d.show(getSupportFragmentManager(), "Mi diálogo");
    }

    @Override
    public void OnRespuesta(int s) {
        switch(s) {
            case 0:
                startGame(8,8, 10);
                break;
            case 1:
                startGame(10,10,13);
                break;
            case 2:
                startGame(12,12,15);
                break;
            case 10:
                bugActive = R.drawable.mar21;
                break;
            case 11:
                bugActive = R.drawable.mar22;
                break;
        }
    }

}
