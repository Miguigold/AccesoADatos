package SimulaBanco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Banco {
	
	// Declaración de variables
	private final int saldoInicial;
	private final int númeroDeCuentas;
	private boolean abierto;
	private Connection conexión;

	/* Constructor de Banco. Se pasa por parámetros el número de cuentas y el saldo inicial, también
	se inicializa abierto a true.  */
	public Banco(int numCuentas, int saldoInicial) {
		this.abierto = true;
		this.saldoInicial = saldoInicial;
		this.númeroDeCuentas = numCuentas;

		// Conexión a la base de datos
		try {
			conexión = DriverManager.getConnection("jdbc:mysql://localhost/adat1?allowPublicKeyRetrieval=true", "dam2",
					"asdf.1234");

			// Crea la tabla cuentas:
			Statement sql = conexión.createStatement();
			sql.execute("DROP TABLE IF EXISTS cuentas ");
			sql.execute("create table cuentas(id int primary key, saldo float)");
			// Inserta una cuenta con el saldo inicial
			for (int i = 0; i < numCuentas; i++) {
				sql.execute(String.format("insert into cuentas values(%d,%d)", i, saldoInicial));
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			conexión = null;
			this.abierto = false;
		}
	}
	
	// Ejecuta una transferencia entre dos cuentas aplicando las reglas de transacción y control de saldo
	public void transfiere(int origen, int destino, int cantidad, Connection conexiónHilo,
			PreparedStatement sqlMiraFondos, PreparedStatement sqlRetira, PreparedStatement sqlIngresa,
			boolean retiraEnDosPasos, boolean transacción, boolean reordena) {

		try {
			if (transacción) conexiónHilo.setAutoCommit(false);
			sqlMiraFondos.setInt(1, origen);
			sqlRetira.setFloat(1, cantidad);
			sqlRetira.setInt(2, origen);
			if (!retiraEnDosPasos) sqlRetira.setFloat(3, cantidad);
			sqlIngresa.setFloat(1, cantidad);
			sqlIngresa.setInt(2, destino);
			boolean faltaSaldo = true;
			// Si se retira en dos pasos, primero se consulta el saldo y luego se actualiza
			if (retiraEnDosPasos) {
				ResultSet res = sqlMiraFondos.executeQuery();
				if (res.next() && res.getFloat(1) >= cantidad) {
					// Reordenamos para evitar interbloqueos: 
					if (origen < destino || !transacción || !reordena) { // Orden normal
						sqlRetira.executeUpdate();
						sqlIngresa.executeUpdate();
					} else { // Orden invertido:
						sqlIngresa.executeUpdate();
						sqlRetira.executeUpdate();
					}
					faltaSaldo = false;
				}
			} else {
				if (sqlRetira.executeUpdate() > 0) {
					sqlIngresa.executeUpdate();
					faltaSaldo = false;
				}
			}

			if (faltaSaldo) {
				System.err.printf("No puedo tranferir %d de %d a %d por falta de fondos\n", cantidad, origen, destino);
			}
			// Confirma los cambios si la transacción ha sido correcta
			if (transacción) conexiónHilo.commit();
		} catch (SQLException e) {
			System.err.println("Problema SQL " + e.getMessage());
			// Si ocurre un error, se deshacen los cambios de la transacción
			try {
				conexiónHilo.rollback();
			} catch (SQLException e1) {
				System.err.println("Problema haciendo rollback " + e.getMessage());
			}
		}

		if (transacción) {
			try {
				conexiónHilo.setAutoCommit(true);
			} catch (SQLException e) {
				System.err.println("Problema haciendo autocommit " + e.getMessage());
			}
		}

		try {
			// Comprueba fondos tras la operación para detectar descubiertos:
			ResultSet res2 = sqlMiraFondos.executeQuery();
			if (!res2.next() || res2.getFloat(1) < 0) {
				System.err.println("Descubierto en cuenta " + origen + " saldo: " + res2.getFloat(1));
			}
		} catch (SQLException e) {
			System.err.println("Problema SQL bis " + e.getMessage());
		}

	}

	// Busca cuentas con saldo negativo para detectar posibles descubiertos
	public void comprueba() throws SQLException {
		int saldoTotal = 0;
		Statement sql = conexión.createStatement();
		ResultSet res = sql.executeQuery("SELECT SUM(saldo) FROM cuentas");
		if (res.next()) {
			saldoTotal = (int) res.getFloat(1);
			/* El saldo total debe ser igual que el saldo inicial de cada cuenta por el número de
			cuentas. Si es correcto aparece un mensaje de que el balance es correcto y si no un
			mensaje de que no cuadran las cuentas.  */
			if (saldoTotal != (númeroDeCuentas * saldoInicial)) {
				System.err.println("¡¡¡¡¡No cuadran las cuentas!!!! saldo total " + saldoTotal);
			} else {
				System.out.println("Balance correcto");
			}
		}
		
		res = sql.executeQuery("SELECT id FROM cuentas WHERE saldo<0");
		while (res.next()) {
			System.err.println("DESCUBIERTO en cuenta " + res.getInt(1));
		}
		

		/*
		 * Detallando por cuenta: ResultSet res =
		 * sql.executeQuery("SELECT id,saldo FROM cuentas"); while (res.next()) { int
		 * saldo = (int) res.getFloat(2); saldoTotal += saldo;
		 * System.out.printf("Cuenta %d , saldo %d, parcial %d\n", res.getInt(1),
		 * saldo,* saldoTotal); }
		 */

	} // comprueba

	public int getNúmeroDeCuentas() {
		return númeroDeCuentas;
	}

	boolean abierto() {
		return abierto;
	}

	void cierraBanco() {
		abierto = false;

	}

	// Cerramos la conexión a la base de datos
	void cierraConexiónBD() {
		try {
			conexión.close();
		} catch (SQLException e) {
			System.err.println("Error cerrando conexión de BBDD " + e.getMessage());
		}
	}
}
