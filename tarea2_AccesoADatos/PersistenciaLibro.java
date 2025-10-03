package tarea2_AccesoADatos;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.persistence.FilePersistenceStrategy;
import com.thoughtworks.xstream.security.AnyTypePermission;
import com.thoughtworks.xstream.persistence.XmlArrayList;

import java.io.File;
import java.util.List;

public class PersistenciaLibro {
    public static void main(String[] args) {
        try {
            // Es la ruta al directorio Documentos del usuario y le crea la carpeta libros
            String userHome = System.getProperty("user.home");
            File carpeta = new File(userHome + "/Documents/libros");
            carpeta.mkdirs();
           
            // Configuración de XStream
            XStream xs = new XStream(new DomDriver());
            xs.addPermission(AnyTypePermission.ANY);
            xs.alias("libro", Libro.class);
            
            FilePersistenceStrategy fps = new FilePersistenceStrategy(carpeta, xs);
            
            // Creamos la lista y metemos los libros
            @SuppressWarnings("unchecked")
			List<Libro> lista = new XmlArrayList(fps);
            lista.add(new Libro(978860747, "El Quijote", "Coloquio", 1605, "Cervantes", 5847560));
            lista.add(new Libro(784370447, "Cien años de soledad", "Sudamericana", 1967, "Gabriel García Márquez", 1234567));
            lista.add(new Libro(801404926, "La Odisea", "Penguin", -800, "Homero", 7654321));

            System.out.println("Se han guardado " + lista.size() + " libros en la carpeta:");
            System.out.println(carpeta.getAbsolutePath());

            // Recupera recorriendo la lista 
            System.out.println("\nLibros recuperados desde XML:");
            for (Libro l : lista) {
                System.out.println(l);
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}