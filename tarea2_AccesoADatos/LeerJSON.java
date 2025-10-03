package tarea2_AccesoADatos;

import java.io.FileReader;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class LeerJSON {
	 public static void main(String[] args) {
	        try {
	            // Lee el JSON y lo convierte en objeto
	            FileReader fr = new FileReader("coche.json");
	            fr.close();
	            JsonObject jsonObject = JsonParser.parseReader(new FileReader("coche.json")).getAsJsonObject();
	        	JsonObject cocheJson = jsonObject.getAsJsonObject("coche");
	        	Coche coche = new Gson().fromJson(cocheJson, Coche.class);
	        	
	            System.out.println("Objeto recuperado desde JSON con Gson:");
	            System.out.println(coche);

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
}