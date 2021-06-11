import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Menu {

	static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {

		try {
			// Desde Casa
			Class.forName("com.mysql.jdbc.Driver");
			Connection conexion = DriverManager.getConnection("jdbc:mysql://iescierva.net:14306/aad_19", "aad_19",
					"1234567");

			// Desde Clase
			// Class.forName("com.mysql.jdbc.Driver");
			// Connection conexion = DriverManager.getConnection("jdbc:mysql://172.20.254.161/aad_19","aad_19", "1234567");

			eleccion(conexion);
			conexion.close(); // Cerrar conexion
		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void eleccion(Connection conexion) throws SQLException {
		int elec = 1;
		while (elec != 0) {
			System.out.println("-----Esto es un menu de SQL-----");
			System.out.println("Elija una opcion: \n");
			System.out.println("(1) Consultar");
			System.out.println("(2) Insertar");
			System.out.println("(3) Actualizar");
			System.out.println("(4) Eliminar");
			System.out.println("(0) Salir");
			elec = scan.nextInt();

			switch (elec) {
			case 1:
				consultar(conexion);
				break;
			case 2:
				insertar(conexion);
				break;
			case 3:
				actualizar(conexion);
				break;
			case 4:
				eliminar(conexion);
				break;
			case 0:
				break;
			}
		}
	}

	public static void consultar(Connection conexion) throws SQLException  {
		Statement sentencia = conexion.createStatement();
		String consulta = "SELECT * FROM empleados";
		ResultSet resul = sentencia.executeQuery(consulta);
		System.out.printf("emp_no apellido oficio salario dept_no %n");
		System.out.printf("------- --------- ------- -------- -------- %n");
		while (resul.next()) {
			System.out.printf("%d, %s, %s, %d, %d %n", resul.getInt(1), resul.getString(2), resul.getString(3), resul.getInt(4), resul.getInt(5));
		}
		System.out.print("\n\n");
		resul.close();
		sentencia.close();
	}

	public static void insertar(Connection conexion) throws SQLException {
		String apellido,oficio; int emp_no, dept_no, salario;
		Statement sentencia = conexion.createStatement();
		System.out.println("Introduce el emp_no, apellido, oficio, dept_no y salario: ");
		emp_no = scan.nextInt();
		apellido = scan.next();
		oficio = scan.next();
		dept_no = scan.nextInt();
		salario = scan.nextInt();
		String insercion = "INSERT INTO empleados (emp_no, apellido, oficio, dept_no, salario) VALUES ("+emp_no+", '"+apellido+"', '"+oficio+"', "+dept_no+", "+salario+")";
		sentencia.execute(insercion);
		sentencia.close();
		System.out.println("La inserción ha sido realizada con éxito...");
	}

	public static void actualizar(Connection conexion) throws SQLException {
		String campo,valor; int emp;
		System.out.println("Introduce el campo que quieras actualizar, su nuevo valor y de que emp_no: ");
		campo = scan.next();
		valor = scan.next();
		emp = scan.nextInt();
		String actualizacion = "UPDATE empleados SET "+campo+" = '"+valor+"' WHERE emp_no = "+emp+"";
		Statement sentencia = conexion.createStatement();
		sentencia.execute(actualizacion);
		sentencia.close();
		System.out.println("La actualización ha sido realizada con éxito...");
	}

	public static void eliminar(Connection conexion) throws SQLException {
		int emp;
		System.out.println("Introduce el emp_no que quieres eliminar: ");
		emp=scan.nextInt();
		String eliminacion = "DELETE FROM empleados WHERE emp_no = "+emp+"";
		Statement sentencia = conexion.createStatement();
		sentencia.execute(eliminacion);
		sentencia.close();
		System.out.println("La eliminación ha sido realizada con éxito...");
	}
}
