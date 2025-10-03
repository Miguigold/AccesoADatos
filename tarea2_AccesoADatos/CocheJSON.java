package tarea2_AccesoADatos;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;

import java.io.FileWriter;

public class CocheJSON {
    public static void main(String[] args) {
        try {
            Coche coche = new Coche("Ibiza", "Seat", "5678DEF", "Blanco");

            // Configuración de XStream para JSON
            XStream xs = new XStream(new JsonHierarchicalStreamDriver());
            xs.addPermission(AnyTypePermission.ANY);
            xs.alias("coche", Coche.class);

            // Convierte el objeto a JSON
            String json = xs.toXML(coche); 
            System.out.println("JSON generado:\n" + json);

            // Guarda en un fichero
            try (FileWriter fw = new FileWriter("coche.json")) {
                fw.write(json);
                System.out.println("Archivo JSON guardado en coche.json");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}