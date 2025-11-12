package SimulaBanco;

import java.sql.SQLException;

public class SimulaBanco {

	// Se declaran las constantes: 20 hilos, 10 cuentas y el saldo inicial de cada cuenta que es 10000
	public static final int NÚMERO_DE_CUENTAS = 10;
	public static final int NÚMERO_DE_HILOS = 20;
	public static final int SALDO_INICIAL = 10000;

	public static void main(String[] args) throws InterruptedException, SQLException {
		// Se crea una instancia del objeto de tipo Banco 
		Banco banco = new Banco(NÚMERO_DE_CUENTAS, SALDO_INICIAL);
		
		 // Crea un array de 20 hilos y los lanza para realizar transferencias simultáneas:
        Thread[] hilos = new Thread[NÚMERO_DE_HILOS]; 
        for (int i=0; i<NÚMERO_DE_HILOS;i++) {
        	hilos[i] = new Thread(new HiloTransferencia(banco,i,SALDO_INICIAL));
        	hilos[i].start();
        }
        
        // Esperar a que terminen todos los hilos
        int numHilos = NÚMERO_DE_HILOS;
        while (numHilos>0) {
        	// Comprueba periódicamente el balance general del banco mientras los hilos están activos
        	banco.comprueba();
        	Thread.sleep(1000);
        	numHilos = 0;
	        for (int i=0; i<NÚMERO_DE_HILOS;i++) {
	        	if (hilos[i].isAlive()) {   		
	        		numHilos++;
	        	}
	        }
	        System.out.println("Hilos vivos: " + numHilos);
	        if (numHilos<2) {  
	        	banco.cierraBanco(); // Cuando solo quede un hilo cerramos el banco para evitar bloqueo
	        }
        }

		System.out.println("Terminadas todas las transferencias");

		// Comprobación:
		banco.comprueba();
		// Ciere final:
		banco.cierraConexiónBD();
	}

}
