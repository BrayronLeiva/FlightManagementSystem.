package Model;

public class Vuelo {
    private String numero;
    private String origen;
    private String destino;
    private int salida;
    private int llegada;
    private boolean dia_siguiente;

    public Vuelo(String numero, String origen, String destino, int salida, int llegada, boolean dia_siguiente) {
        this.numero = numero;
        this.origen = origen;
        this.destino = destino;
        this.salida = salida;
        this.llegada = llegada;
        this.dia_siguiente = dia_siguiente;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public int getSalida() {
        return salida;
    }

    public void setSalida(int salida) {
        this.salida = salida;
    }

    public int getLlegada() {
        return llegada;
    }

    public void setLlegada(int llegada) {
        this.llegada = llegada;
    }

    public boolean isDia_siguiente() {
        return dia_siguiente;
    }

    public void setDia_siguiente(boolean dia_siguiente) {
        this.dia_siguiente = dia_siguiente;
    }

    @Override
    public String toString() {
        return "Vuelo{" +
                "numero='" + numero + '\'' +
                ", origen='" + origen + '\'' +
                ", destino='" + destino + '\'' +
                ", salida=" + salida +
                ", llegada=" + llegada +
                '}';
    }
}
