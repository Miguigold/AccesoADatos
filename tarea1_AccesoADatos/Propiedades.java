package tarea1_AccesoADatos;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Propiedades {
	public static void main(String[] args) {
        // Conjunto de pares clave/valor con el método setProperty
		Properties prop = new Properties();
        prop.setProperty("usuario", "pepe");
        prop.setProperty("email", "pepe@mail.com");
        prop.setProperty("edad", "22");

        try {
            // Guarda el objeto Properties con el método store en un fichero con FileOutputStream
            FileOutputStream fos1 = new FileOutputStream("datos.properties");
            prop.store(fos1, "Ejemplo Properties");
            fos1.close();

            // Guarda el objeto Properties con el método storeToXML en un fichero con FileOutputStream
            FileOutputStream fos2 = new FileOutputStream("datos.xml");
            prop.storeToXML(fos2, "Ejemplo Properties en XML");
            fos2.close();

            // Lee el objeto Properties con el método load en un fichero con FileInputStream
            Properties prop2 = new Properties();
            FileInputStream fis1 = new FileInputStream("datos.properties");
            prop2.load(fis1);
            fis1.close();
            System.out.println("Cargado desde .properties: " + prop2);

            // Lee el objeto Properties con el método loadFromXML en un fichero con FileInputStream
            Properties prop3 = new Properties();
            FileInputStream fis2 = new FileInputStream("datos.xml");
            prop3.loadFromXML(fis2);
            fis2.close();
            System.out.println("Cargado desde .xml: " + prop3);

        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}