package org.goya.dam2.ud6;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RepositorioCliente extends MongoRepository<Cliente, String> {
	public Cliente findByNombre(String nombre);

	public List<Cliente> findByApellido(String apellido);
}