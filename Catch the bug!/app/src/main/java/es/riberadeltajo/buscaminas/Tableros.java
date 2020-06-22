package es.riberadeltajo.buscaminas;

public class Tableros {
    private int n_botones_ancho;
    private int n_botones_alto;
    private int[][] mapa;

    public Tableros(int n_botones_ancho, int n_botones_alto) {
        this.n_botones_ancho = n_botones_ancho;
        this.n_botones_alto = n_botones_alto;
        mapa = new int[n_botones_ancho][n_botones_alto];
    }

    public Tableros() {

    }

    public void setN_botones_ancho(int n_botones_ancho) {
        this.n_botones_ancho = n_botones_ancho;
    }

    public void setN_botones_alto(int n_botones_alto) {
        this.n_botones_alto = n_botones_alto;
    }

    public void setMapa(int[][] mapa) {
        this.mapa = mapa;
    }

    public int getN_botones_ancho() {
        return n_botones_ancho;
    }

    public int getN_botones_alto() {
        return n_botones_alto;
    }

    public int getValor(int x, int y) {
        return mapa[x][y];
    }

    public int[][] getMapa() {
        return mapa;
    }
}
