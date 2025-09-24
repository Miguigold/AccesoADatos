package tarea1_AccesoADatos;

public class Coche {
	String modelo;
    String marca;
    String matricula;
    String color;

    public Coche(String modelo, String marca, String matricula, String color) {
        this.modelo = modelo;
        this.marca = marca;
        this.matricula = matricula;
        this.color = color;
    }

    @Override
    public String toString() {
        return modelo + ";" + marca + ";" + matricula + ";" + color;
    }

    public static Coche recuperaCSV(String linea) {
        String[] partes = linea.split(";");
        return new Coche(partes[0], partes[1], partes[2], partes[3]);
    }

}