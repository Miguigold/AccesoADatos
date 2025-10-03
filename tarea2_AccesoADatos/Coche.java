package tarea2_AccesoADatos;

import java.io.Serializable;

public class Coche implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String modelo;
	private String marca;
	private String matricula;
	private String color;
    
    public Coche() {}

    public Coche(String modelo, String marca, String matricula, String color) {
        this.modelo = modelo;
        this.marca = marca;
        this.matricula = matricula;
        this.color = color;
    }
    
    public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Override
    public String toString() {
        return modelo + ";" + marca + ";" + matricula + ";" + color;
    }
}