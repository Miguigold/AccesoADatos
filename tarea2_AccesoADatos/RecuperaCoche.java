package tarea2_AccesoADatos;

import java.io.FileReader;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class RecuperaCoche {
	public static void main(String[] args) {
		// COnfiguración de XStream
        XStream xs = new XStream(new DomDriver());
        xs.addPermission(AnyTypePermission.ANY);
        xs.alias("coche", Coche.class);

        // Lee el fichero XML
        try (FileReader fr = new FileReader("coche.xml")) {
            Coche coche = (Coche) xs.fromXML(fr);
            System.out.println("Objeto recuperado: " + coche);
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
