import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTable.XWPFBorderType;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableCell.XWPFVertAlign;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TableRowAlign;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * Nombre: Nacho Riquelme
 * 		   Jose Manuel Monteagudo Sanchez
 * Fecha: 17/02/2021
 */

public class Word extends Thread {

	String nombre;
	String fichero;
	Connection conexion;

	// Constructor
	public Word(String nombre, String fichero, Connection conexion) {

		this.nombre = nombre;
		this.fichero = fichero;
		this.conexion = conexion;
	}

	// Comienza hilo para que el programa pueda seguir funcionando mientras crea documento
	public void run() {

		String agenda, consulta;
		
		ResultSet resultado;
		

		try {
			
			Statement sentencia = conexion.createStatement(); // Crea sentencia

			// Entra para hacer el word completo
			if (nombre.equalsIgnoreCase("agendaCompleta")) {
				
				agenda = "\\Agenda.docx";
				consulta = "SELECT * FROM AGENDA";
				resultado = sentencia.executeQuery(consulta);
			}
			
			// Entra según nombre introducido
			else {
				agenda = "\\AgendaAlumno.docx";
				consulta = "SELECT * FROM AGENDA WHERE nombre='"+nombre+"'";
				resultado = sentencia.executeQuery(consulta);
				
			}
			
			// Llamada a método
			crearDocumento(resultado, agenda);
			
			// Cierre de Statement y ResultSet
			sentencia.close();
			resultado.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
		
		public void crearDocumento(ResultSet resultado, String agenda) {
			
			String nombre, direccion, poblacion;
			int cp;
			Blob foto;
			XWPFDocument documento; // Crea documento

			try {
				
				documento = new XWPFDocument(); 
				FileOutputStream salida = new FileOutputStream(fichero + agenda, true); // Crea o abre documento word
				
				// Bucle que recorre todos los datos (o 1 en caso de haber introducido nombre)
				while (resultado.next()) {

					// Guarda datos desde MySQL en variables
					nombre = resultado.getString(1);
					direccion = resultado.getString(2);
					poblacion = resultado.getString(3);
					cp = resultado.getInt(4);
					foto = resultado.getBlob(5);

					InputStream is = null;
					is = foto.getBinaryStream(); // Lee imagen en stream

					// Crea tabla en el documento
					XWPFTable tabla = documento.createTable();
					tabla.setLeftBorder(XWPFBorderType.NONE, 0, 0, "");
					tabla.setRightBorder(XWPFBorderType.NONE, 0, 0, "");
					tabla.setBottomBorder(XWPFBorderType.NONE, 0, 0, "");
					tabla.setTopBorder(XWPFBorderType.NONE, 0, 0, "");
					tabla.setInsideVBorder(XWPFBorderType.NONE, 0, 0, "");
					tabla.setTableAlignment(TableRowAlign.CENTER);

					XWPFTableRow columna = tabla.getRow(0); // Crea una fila dentro de la tabla

					// Crea celda izquierda en la fila
					XWPFTableCell celdaIzquierda = columna.getCell(0);
					celdaIzquierda.setVerticalAlignment(XWPFVertAlign.CENTER);
					celdaIzquierda.setColor("EEEEEE");

					// Crea celda derecha en la fila
					XWPFTableCell celdaDerecha = columna.createCell();
					celdaDerecha.setVerticalAlignment(XWPFVertAlign.CENTER);

					// Crea párrafo en la celda izquierda para poder escribir
					XWPFParagraph parrafoIzquierdo = celdaIzquierda.addParagraph();
					parrafoIzquierdo.setIndentationLeft(200);
					parrafoIzquierdo.setSpacingAfterLines(100);
					parrafoIzquierdo.setSpacingBeforeLines(100);
					parrafoIzquierdo.setSpacingBetween(1.1);
					parrafoIzquierdo.setAlignment(ParagraphAlignment.CENTER);

					XWPFRun escribirIzquierda = parrafoIzquierdo.createRun();
					escribirIzquierda.setFontSize(20);
					escribirIzquierda.setCharacterSpacing(10);
					escribirIzquierda.setShadow(true);

					// Crea párrafo en la celda derecha para poder escribir
					XWPFParagraph parrafoDerecho = celdaDerecha.addParagraph();
					parrafoDerecho.setAlignment(ParagraphAlignment.LEFT);
					parrafoDerecho.setSpacingBetween(1.5);

					XWPFRun escribirDerecha = parrafoDerecho.createRun();
					escribirDerecha.setFontSize(15);

					// Nombre
					escribirIzquierda.setText(nombre);
					escribirIzquierda.addBreak();

					// Foto
					escribirIzquierda.addPicture(is, XWPFDocument.PICTURE_TYPE_PNG, agenda, Units.toEMU(175),
							Units.toEMU(200));
					escribirIzquierda.addBreak();

					// Dirección
					escribirDerecha.setText("Dirección: " + direccion);
					escribirDerecha.addBreak();

					// Población
					escribirDerecha.setText("Población: " + poblacion);
					escribirDerecha.addBreak();

					// Código Postal
					escribirDerecha.setText("Código Postal: " + cp);
					escribirDerecha.addBreak();

					documento.write(salida); // Escribe en documento
				}
				
				// Cierre de documento y FileOutputStream
				salida.close();
				documento.close();
				
			} catch (InvalidFormatException | SQLException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

		}
}
