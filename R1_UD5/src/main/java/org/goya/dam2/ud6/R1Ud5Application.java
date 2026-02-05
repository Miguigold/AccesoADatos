package org.goya.dam2.ud6;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class R1Ud5Application implements CommandLineRunner{
	@Autowired
	private RepositorioCliente repo;

	public static void main(String[] args) {
		SpringApplication.run(R1Ud5Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Clientes encontrados por apellido('Acosta'):");
		System.out.println("--------------------------------");
		for (Cliente cliente : repo.findByApellido("Acosta")) {
			System.out.println(cliente);
		}
		
		
		repo.deleteAll();

		// Guarda un par de clientes
		repo.save(new Cliente("Alice", "Smith"));
		repo.save(new Cliente("Bob", "Smith"));

		// Muestra todos los clientes
		System.out.println("Customers found with findAll():");
		System.out.println("-------------------------------");
		for (Cliente cliente : repo.findAll()) {
			System.out.println(cliente);
		}
		System.out.println();

		// Muestra un cliente concreto
		System.out.println("Customer found with findByFirstName('Alice'):");
		System.out.println("--------------------------------");
		System.out.println(repo.findByNombre("Alice"));

		System.out.println("Customers found with findByLastName('Smith'):");
		System.out.println("--------------------------------");
		for (Cliente cliente : repo.findByApellido("Smith")) {
			System.out.println(cliente);
		}

	}
}
