package tarea2_AccesoADatos;

import java.io.FileReader;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class RecuperaCocheAlias {
	public static void main(String[] args) {
        try {
            // Configuración de XStream
            XStream xs = new XStream(new DomDriver());
            xs.addPermission(AnyTypePermission.ANY);

            // Recupera los alias igual que en el guardado
            xs.alias("vehiculo", Coche.class);
            xs.aliasField("modeloCoche", Coche.class, "modelo");
            xs.aliasField("fabricante", Coche.class, "marca");
            xs.aliasField("matriculaVehiculo", Coche.class, "matricula");
            xs.aliasField("colorVehiculo", Coche.class, "color");

            // Lee el fichero XML
            FileReader fr = new FileReader("cocheAlias.xml");
            Coche coche = (Coche) xs.fromXML(fr);
            fr.close();

            System.out.println("Objeto recuperado:");
            System.out.println(coche);
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}