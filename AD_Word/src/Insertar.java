import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/*
 * Nombre: Nacho Riquelme
 * 		   Jose Manuel Monteagudo Sanchez
 * Fecha: 17/02/2021
 */
public class Insertar {
	static Scanner scan = new Scanner(System.in);
	
	//Funcion insertar
	public static void insertar(Connection conexion, String nombre, String direccion, String poblacion, String cp, String foto) throws SQLException {
		//Se crea tabla si no existe
		String tabla = "CREATE TABLE IF NOT EXISTS AGENDA (nombre VARCHAR(25), direccion VARCHAR(30),"
				+ "poblacion VARCHAR(25), cp INT(5), foto MEDIUMBLOB);";
		
		Statement sentenciaCrearTabla = conexion.createStatement();
		
		sentenciaCrearTabla.execute(tabla);
		
		
		try {
			//Se ejecuta la sentencia insertar en la BD
			File archivoFoto = new File(foto);
			FileInputStream imagenBytes = new FileInputStream(archivoFoto);
			String insertarSQL = "INSERT INTO AGENDA (nombre, direccion, poblacion, cp, foto) VALUES ('"+nombre+"', '"+direccion+"', '"+poblacion+"', "+cp+", ?)";
			
			PreparedStatement sentenciaInsertar = conexion.prepareStatement(insertarSQL);
			sentenciaInsertar.setBlob(1, imagenBytes);
			sentenciaInsertar.executeUpdate();
			
			//System.out.println("La inserción ha sido realizada con éxito...");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
}


