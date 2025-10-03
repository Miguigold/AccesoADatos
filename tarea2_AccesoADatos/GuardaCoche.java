package tarea2_AccesoADatos;

import java.io.FileWriter;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;

public class GuardaCoche {
	public static void main(String[] args) {
        Coche coche = new Coche("Model 3", "Tesla", "1234ABC", "Rojo");

        // Configuración de XStream
        XStream xs = new XStream(new DomDriver());
        xs.addPermission(AnyTypePermission.ANY); 
        xs.alias("coche", Coche.class); 

        // Convierte el objeto a XML
        String xml = xs.toXML(coche);
        System.out.println("XML generado:\n" + xml);

        // Guarda el objeto XML en un fichero
        try (FileWriter fw = new FileWriter("coche.xml")) {
            fw.write(xml);
            System.out.println("XML guardado en coche.xml");
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}