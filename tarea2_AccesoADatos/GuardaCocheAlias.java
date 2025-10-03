package tarea2_AccesoADatos;

import java.io.FileWriter;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class GuardaCocheAlias {
	public static void main(String[] args) {
        Coche coche = new Coche("Model 3", "Tesla", "1234ABC", "Rojo");

        // Configuración de XStream
        XStream xs = new XStream(new DomDriver());
        xs.addPermission(AnyTypePermission.ANY);

        // Define los alias
        xs.alias("vehiculo", Coche.class);
        xs.aliasField("modeloCoche", Coche.class, "modelo");
        xs.aliasField("fabricante", Coche.class, "marca");
        xs.aliasField("matriculaVehiculo", Coche.class, "matricula");
        xs.aliasField("colorVehiculo", Coche.class, "color");

        // Convierte el objeto a XML
        String xml = xs.toXML(coche);
        System.out.println("XML generado con alias:\n" + xml);

        // Guarda el objeto XML en un fichero
        try (FileWriter fw = new FileWriter("cocheAlias.xml")) {
            fw.write(xml);
            System.out.println("XML guardado en cocheAlias.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}