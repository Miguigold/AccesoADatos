package org.goya.dam2.ud6;

import org.springframework.data.annotation.Id;

public class Cliente {
	@Id
	public String id;

	public String nombre;
	public String apellido;

	public Cliente() {
	}

	public Cliente(String nombre, String apellido) {
		this.nombre = nombre;
		this.apellido = apellido;
	}

	@Override
	public String toString() {
		return String.format("Customer[id=%s, nombre='%s', apellido='%s']", id, nombre, apellido);
	}
}
