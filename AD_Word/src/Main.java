import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
/*
 * Nombre: Nacho Riquelme
 * 		   Jose Manuel Monteagudo Sanchez
 * Fecha: 17/02/2021
 */

public class Main {
	public static void main(String[] args) throws SQLException {

		Connection conexion = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conexion = DriverManager.getConnection("jdbc:mysql://iescierva.net:9306/aad_19?useSSL=false", "aad_19",
					"1234567");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		
		int opcion;
		Scanner teclado = new Scanner(System.in);

	        
		do{
			
			System.out.println("\n1. Insertar en la base de datos. " + "\n2. Consultar la base de datos."
					+ "\n3. Crear documento .docx de la agenda" + "\n4. Crear documento .docx con la info de un alumno." + "\n0. Salir.");
			opcion = teclado.nextInt();
			
			switch (opcion) {

			//Insertar alumnos
			case 1:
				//Insertar.insertar(conexion);
				break;
				
			//Consultar alumnos
			case 2:
				//Consultar.consultar(conexion);
				break;
				
			//Descargar word con todos los alumnos
			case 3:
				//Word word = new Word("prueba.docx");
				//word.crearDocumento(conexion);
				break;
				
			//Descargar info de un alumno	
			case 4:
				break;
				
			case 0:
				System.out.println("FIN DEL PROGRAMA.");
				break;
			}//Fin switch
			
		}while(opcion!=0); //Fin do-while
		
		conexion.close();
	} //Fin main

} //Fin clase

