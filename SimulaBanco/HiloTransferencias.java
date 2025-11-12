package SimulaBanco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

class HiloTransferencia implements Runnable {
	private final static int DIVISOR_CANTIDAD = 50; // para dividir la cantidad inicial para el tope por transferencia
	private final static int ITERACIONES = 1000;    // El bucle for va a iterar 1000 veces
	private final Banco banco;
	private int numHilo;
	private final int cantidadMáxima;

	// Usamos conexión y prepared statements separados en cada hilo:
	private Connection conexión;
	private PreparedStatement sqlMiraFondos;
	private PreparedStatement sqlRetira;
	private PreparedStatement sqlIngresa;
	public boolean[] activas;

	/* Sentencias SQL, la primera compueba si hay fondos suficientes y si es así, la segunda
	actualiza el saldo entre cuentas para simular las transacciones.  */
	static final String SQL_MIRA_FONDOS = "SELECT saldo FROM cuentas WHERE id=?";
	static final String SQL_INGRESA = "UPDATE cuentas SET saldo=saldo+? WHERE id=?";
	// si la comprobación de fondos y la retirada se hacen por separado:
	static final boolean RETIRA_EN_DOS_PASOS = true; 
	static final String SQL_RETIRA = RETIRA_EN_DOS_PASOS ?
			"UPDATE cuentas set saldo=saldo-? WHERE id=?" :
			"UPDATE cuentas SET saldo=saldo-? WHERE id=? AND saldo>=?"; 
	static final boolean TRANSACCIÓN = true;
	// solo tiene sentido en transacciones:
		static final boolean REORDENA_QUERIES = false;	

	// Constructor de HiloTransferencia
	public HiloTransferencia(Banco b, int from, int max) throws SQLException {
		banco = b;
		numHilo = from;
		cantidadMáxima = max;

		// Conexión con la base de datos
		conexión = DriverManager.getConnection("jdbc:mysql://localhost/adat1?allowPublicKeyRetrieval=true", "dam2",
				"asdf.1234");

		// Prepara las sentencias SQL que usará el hilo para realizar las transferencia
		sqlMiraFondos = conexión.prepareStatement(SQL_MIRA_FONDOS);
		sqlRetira = conexión.prepareStatement(SQL_RETIRA);
		sqlIngresa = conexión.prepareStatement(SQL_INGRESA);
	}
	
	// Bucle principal del hilo que realiza transferencias aleatorias entre cuentas
	public void run() {
		Random rnd = new Random();
		String mensajeSalida = "Terminadas las transferencias del hilo " + numHilo;
		for (int i = 0; i < ITERACIONES; i++) {
			// Elije aleatoriamente una cuenta destino hasta dar con una válida:
			int cuentaOrigen, cuentaDestino;
			cuentaOrigen = rnd.nextInt(banco.getNúmeroDeCuentas());
			do { // bucle no infinito porque si solo queda una cuenta deberá llegar a !banco.abierto()
				cuentaDestino = rnd.nextInt(banco.getNúmeroDeCuentas());
			} while (banco.abierto() && ((cuentaDestino == cuentaOrigen)));
			int cantidad = rnd.nextInt(cantidadMáxima / DIVISOR_CANTIDAD);

			if (!banco.abierto()) {
				mensajeSalida = "Saliendo por banco cerrado. Hilo " + numHilo;
				break;
			}
			// Realiza la transferencia entre las dos cuentas seleccionadas
			banco.transfiere(cuentaOrigen, cuentaDestino, cantidad, conexión, sqlMiraFondos, sqlRetira, sqlIngresa,
					RETIRA_EN_DOS_PASOS, TRANSACCIÓN, REORDENA_QUERIES);
		}

		if (mensajeSalida.startsWith("Terminadas"))
			System.out.println(mensajeSalida);
		else
			System.err.println(mensajeSalida);
		try {
			conexión.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
}
