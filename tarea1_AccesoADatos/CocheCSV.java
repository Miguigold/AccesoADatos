package tarea1_AccesoADatos;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.nio.charset.StandardCharsets;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;

public class CocheCSV {
	public static void main(String[] args) {
        // ArrayList de coches
        List<Coche> garaje = new ArrayList<>();
        garaje.add(new Coche("Model 3", "Tesla", "1234ABC", "Rojo"));
        garaje.add(new Coche("Ibiza", "Seat, seat", "5678DEF", "Blanco"));
        garaje.add(new Coche("Golf", "Volkswagen", "9012GHI", "Negro"));

        // Guarda la lista en un CSV con PrintWriter
        try (PrintWriter pw = new PrintWriter(
                new OutputStreamWriter(new FileOutputStream("coches.csv"), StandardCharsets.UTF_8))) {
            for (Coche c : garaje) {
                pw.println(c.toString());
            }
            System.out.println("Garaje guardado en coches.csv");
        } 
        catch (IOException e) {
            e.printStackTrace();
        }

        // Lee la lista desde el CSV con BufferedReader
        List<Coche> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream("coches.csv"), StandardCharsets.UTF_8))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                lista.add(Coche.recuperaCSV(linea));
            }
            System.out.println("Garaje recuperado desde CSV:");
            for (Coche c : lista) {
                System.out.println(c.toString());
            }
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
