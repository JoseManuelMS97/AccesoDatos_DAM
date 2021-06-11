import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * Nombre: Nacho Riquelme
 * 		   Jose Manuel Monteagudo Sanchez
 * Fecha: 17/02/2021
 */

public class Consultar {
	

	public static String consultar(Connection conexion) throws SQLException {
		//Creamos sentencia y consulta
		Statement sentencia = conexion.createStatement();
		String consulta = "SELECT * FROM AGENDA";
		String resultado = "";
		
		ResultSet resul = sentencia.executeQuery(consulta);
		
		//System.out.printf("%-25s %-30s %-25s %-5s %n", "Nombre", "Domicilio", "Población", "CP");
		//System.out.printf("%-25s %-30s %-25s %-5s %n", "------", "---------", "---------", "-----");
		
		//Guardamos en la variable resultado los datos de la BD
		while (resul.next()) {
			resultado += String.format("%-25s %-30s %-25s %-5d %n", resul.getString(1), resul.getString(2), resul.getString(3), resul.getInt(4));
			//System.out.println(resultado);
		}
		
		//System.out.print("\n\n");
		resul.close();
		sentencia.close();
		
		return resultado;
	}
}