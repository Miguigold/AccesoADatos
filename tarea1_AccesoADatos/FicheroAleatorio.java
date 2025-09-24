package tarea1_AccesoADatos;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class FicheroAleatorio {
	public static void main(String[] args) {
		// Crea el fichero y el array de 20 posiciones
        String nombreFichero = "datos.bin";
        int tamaño = 20;
        int[] array = new int[tamaño];

        // Comprueba si el fichero existe
        File fich = new File(nombreFichero);
        if (fich.exists()) {
            try (DataInputStream dis = new DataInputStream(new FileInputStream(fich))) {
                for (int i = 0; i < tamaño; i++) {
                    array[i] = dis.readInt();
                }
            } 
            catch (IOException e) {
                e.printStackTrace();
            }
        } 
        else {
            // Rellena con 20 ceros
            try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(fich))) {
                for (int i = 0; i < tamaño; i++) {
                    array[i] = 0;
                    dos.writeInt(0);
                }
            } 
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (RandomAccessFile raf = new RandomAccessFile(nombreFichero, "rw");
             Scanner teclado = new Scanner(System.in)) {

            int pos;
            do {
                // Muestra el array actual
                System.out.print("Array actual: ");
                for (int x : array) {
                    System.out.print(x + " ");
                }
                System.out.println();

                // Pide la posición del array y la modifica
                System.out.print("Introduce la posición a modificar (0-19, negativo para salir): ");
                pos = teclado.nextInt();
                if (pos >= 0 && pos < tamaño) {
                    System.out.print("Nuevo valor para la posición " + pos + ": ");
                    int nuevoValor = teclado.nextInt();
                    array[pos] = nuevoValor;
                    // Calcula la el byte exacto, cada int son 4 bytes, multiplicado por la posición del array
                    long bytes = pos * 4L; 
                    // Salta a la posición exacta y lo actualiza en el fichero
                    raf.seek(bytes);       
                    raf.writeInt(nuevoValor);
                }
            } 
            while (pos >= 0);
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Programa terminado.");
    }
}
