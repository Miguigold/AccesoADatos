package tarea2_AccesoADatos;

/**
 * 
 *  @descrition Clase Libro 
 *	@author Laura
 *  @date 26/3/2015
 *  @version 1.0
 *  @license GPLv3
 */
public class Libro {
	  private int isbn;
	  private String titulo;
	  private String editorial;
	  private int paginas;
	  private String autor;
	  private int copias;

	  
	
    public Libro() {
		super();
	}

	public Libro(int isbn, String titulo, String editorial, int paginas,
			String autor, int copias) {
		super();
		this.isbn = isbn;
		this.titulo = titulo;
		this.editorial = editorial;
		this.paginas = paginas;
		this.autor = autor;
		this.copias = copias;
	}

	public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getPaginas() {
        return paginas;
    }

    public void setPaginas(int paginas) {
        this.paginas = paginas;
    }

    @Override
    public String toString() {
        return "Libro{" + "isbn= " + isbn + ", titulo= " + titulo + ", autor= " + autor + ", editorial= " + editorial + ", paginas= " + paginas + ", copias= " + copias +'}';
    }


    public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public int getCopias() {
		return copias;
	}

	public void setCopias(int copias) {
		this.copias = copias;
	}

	//Representaci�n en array de nuestro objeto Libro
    public Object[] toArray(){
        return new Object[]{isbn,titulo,autor,editorial,paginas,copias};
    }

    
	
	
	
}
