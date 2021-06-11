import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;

public class LecturaXLS {

	static ArrayList<String> campos = new ArrayList<String>();
	static String sql;

	public static void conexion() throws SQLException {

		Connection conexion = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conexion = DriverManager.getConnection("jdbc:mysql://iescierva.net:9306/aad_19?useSSL=false", "aad_19",
					"1234567");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		Statement sentencia = conexion.createStatement();

		System.out.println(sql);
		sentencia.execute(sql);
	}
	
	
	@SuppressWarnings("deprecation")
	public static void crearTabla(Iterator<Cell> cellIterator, String nombre)
			throws SQLException, ClassNotFoundException {

		sql = "";

		Cell celda;
		ArrayList<String> texto = new ArrayList<String>();


		while (cellIterator.hasNext()) {

			celda = cellIterator.next();

			if (celda != null) {

				switch (celda.getCellType()) {

				case Cell.CELL_TYPE_NUMERIC:
					if (DateUtil.isCellDateFormatted(celda)) {
						texto.add(celda.getDateCellValue() + " DATE"); 

						campos.add(celda.getDateCellValue().toString());
					} else {
						texto.add(celda.getNumericCellValue() + " VARCHAR(10)"); 
						
						campos.add(celda.getNumericCellValue() + "");
					}
					break;

				case Cell.CELL_TYPE_STRING:
					texto.add(celda.getStringCellValue() + " VARCHAR(100)"); 
					campos.add(celda.getStringCellValue());
					break;

				} // end switch

			}

		} // end while

		sql = "CREATE TABLE IF NOT EXISTS " + nombre + " (";

		for (int i = 0; i < texto.size(); i++) {

			if (i == texto.size() - 1) {
				sql += texto.get(i);
			} else {
				sql += texto.get(i) + ", ";
			}
		}
		sql += ");";

	}

	
	@SuppressWarnings("deprecation")
	public static void insertarDatos(Iterator<Cell> cellIterator, ArrayList<String> campos, String nombre)
			throws ClassNotFoundException, SQLException {

		sql = "";

		Cell celda;
		ArrayList<String> texto = new ArrayList<String>();

		int contador = 0;


		while (cellIterator.hasNext()) {

			celda = cellIterator.next();

			if (celda != null) {
				switch (celda.getCellType()) {

				case Cell.CELL_TYPE_NUMERIC:
					if (DateUtil.isCellDateFormatted(celda)) {
						texto.add(celda.getDateCellValue().toString()); 
					} else {
						texto.add(celda.getNumericCellValue() + ""); 						
					}
					break;

				case Cell.CELL_TYPE_STRING:
					texto.add(celda.getStringCellValue()); 
					break;

				case Cell.CELL_TYPE_BOOLEAN:
					texto.add(celda.getBooleanCellValue() + ""); 
					break;

				} 
			}
		} 

		sql += "INSERT INTO " + nombre + " (";

		for (int i = 0; i < campos.size(); i++) {

			if (i == texto.size() - 1 && texto != null) {
				sql += campos.get(i);
			} else {
				sql += campos.get(i) + ", ";
			}
		}

		sql += ") VALUES (";

		for (int i = 0; i < texto.size(); i++) {

			if (i == texto.size() - 1 && texto != null) {
				sql += "'" + texto.get(i) + "'";
			} else {
				sql += "'" + texto.get(i) + "', ";
			}
		}
		sql += ");";
	}
	
	public static void main(String args[]) throws IOException {
		File fichero = new File("C:\\Users\\josem\\Downloads\\prueba.xls"); 
		FileInputStream is = new FileInputStream(fichero); // Crea objeto para leer el fichero
		HSSFWorkbook excel = new HSSFWorkbook(is); // Crea objeto excel

		int sheets = excel.getNumberOfSheets(); // Captura hojas para recorrerlas

		for (int i = 0; i < sheets; i++) {

			HSSFSheet hoja = excel.getSheetAt(i);
			String nombre = excel.getSheetName(i);

			Iterator<Row> linea = hoja.iterator(); // Crea iterator para recorrer filas

			Row row;
			int contador = 0;
			campos.clear();


			while (linea.hasNext()) {

				row = linea.next(); 

				Iterator<Cell> cellIterator = row.cellIterator();

				try {
					if (contador == 0) {
						crearTabla(cellIterator, nombre);
					} else {
						insertarDatos(cellIterator, campos, nombre);
					}
				} catch (ClassNotFoundException | SQLException e) {

					e.printStackTrace();
				}

				contador++;

				try {
					conexion();
				} catch (SQLException e) {

					e.printStackTrace();
				}
			}
		}
		excel.close();
	}
} 