import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JTabbedPane;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.TextArea;
import javax.swing.JSeparator;


/*
 * Nombre: Nacho Riquelme
 * 		   Jose Manuel Monteagudo Sanchez
 * Fecha: 17/02/2021
 */

public class pruebaGrafica extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	/**
	 * Launch the application.
	 */
	
	//Variables globales
	static Connection conexion = null;
	private JTextField txt;
	Word word;
	private JTextField textField_4;
	static String ruta;
	
	public static void main(String[] args) {
		
		//Conexion a mi BD
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conexion = DriverManager.getConnection("jdbc:mysql://iescierva.net:9306/aad_19?useSSL=false", "aad_19",
					"1234567");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		//Lanza la app grafica
		EventQueue.invokeLater(new Runnable() {
			public void run() {

				try {
					
					pruebaGrafica frame = new pruebaGrafica();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	@SuppressWarnings("deprecation")
	public pruebaGrafica() throws SQLException {
		
		//Medidas del panel		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(1, 0, 0, 0));
		
		//Pestañas
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane);
		
		//Pestaña insertar
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		tabbedPane.addTab("Insertar", null, panel, null);
		panel.setLayout(null);
		
			//Nombre
		JTextPane txtpnNombre = new JTextPane();
		txtpnNombre.setBackground(Color.WHITE);
		txtpnNombre.setBounds(10, 81, 57, 19);
		txtpnNombre.setText("Nombre:");
		panel.add(txtpnNombre);
		
		textField = new JTextField();
		textField.setBounds(95, 81, 200, 19);
		panel.add(textField);
		textField.setColumns(10);
			//Direccion
		JTextPane txtpnDirecin = new JTextPane();
		txtpnDirecin.setText("Direci\u00F3n:");
		txtpnDirecin.setBounds(10, 110, 57, 19);
		panel.add(txtpnDirecin);
		
		textField_1 = new JTextField();
		textField_1.setBounds(95, 110, 200, 19);
		panel.add(textField_1);
		textField_1.setColumns(10);
			//Direccion
		JTextPane txtpnPoblacin = new JTextPane();
		txtpnPoblacin.setText("Poblaci\u00F3n:");
		txtpnPoblacin.setBounds(10, 139, 57, 19);
		panel.add(txtpnPoblacin);
		
		textField_2 = new JTextField();
		textField_2.setBounds(95, 139, 200, 19);
		panel.add(textField_2);
		textField_2.setColumns(10);
			//CodigoPostal
		JTextPane txtpnCp = new JTextPane();
		txtpnCp.setText("CP:");
		txtpnCp.setBounds(10, 168, 57, 19);
		panel.add(txtpnCp);
		
		textField_3 = new JTextField();
		textField_3.setBounds(95, 168, 200, 19);
		panel.add(textField_3);
		textField_3.setColumns(10);
		
		//Boton insertar
		JButton btnNewButton = new JButton("Insertar");
		btnNewButton.addActionListener(new ActionListener() {
			//Lee los datos introducidos y se los pasa a Insertar
			public void actionPerformed(ActionEvent e) {
				String nombre = textField.getText();
				String direccion = textField_1.getText();
				String poblacion = textField_2.getText();
				String cp = textField_3.getText();
				String foto = txt.getText();
						
				try {
					Insertar.insertar(conexion,nombre,direccion,poblacion,cp,foto);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				JOptionPane.showMessageDialog(null, "Inserccion realizada con exito.");
			}
		});
		btnNewButton.setBounds(326, 90, 85, 116);
		panel.add(btnNewButton);
		
		//Ruta foto
		JTextPane txtpnRutaImagen = new JTextPane();
		txtpnRutaImagen.setText("Foto:");
		txtpnRutaImagen.setBounds(10, 197, 57, 19);
		panel.add(txtpnRutaImagen);
		
		JButton btnNewButton_2 = new JButton("Elegir imagen");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Desplegable File Chooser
				txt = new JTextField(30);
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				
				FileNameExtensionFilter imgFilter = new FileNameExtensionFilter("JPG & GIF Images", "jpg", "gif");
				fileChooser.setFileFilter(imgFilter);
				
				int result = fileChooser.showOpenDialog(btnNewButton_2);
				if (result != JFileChooser.CANCEL_OPTION) { }
				
				
				File fileName = fileChooser.getSelectedFile();

				if ((fileName == null) || (fileName.getName().equals(""))) {
				    txt.setText("...");
				} else {
				    txt.setText(fileName.getAbsolutePath());
				}
							
				
			}
		});
		btnNewButton_2.setBounds(95, 195, 200, 21);
		panel.add(btnNewButton_2);
		
		//Imagen de fondo
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setIcon(new ImageIcon("G:\\Java\\Eclipse\\eclipse\\Workspace\\AD_Word\\fondo.jpg"));
		lblNewLabel.setBounds(0, -12, 421, 238);
		panel.add(lblNewLabel);
		
		//Pestaña consultar
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		tabbedPane.addTab("Consultar", null, panel_1, null);
		panel_1.setLayout(null);
		
		//Area de texto para mostrar la consulta
		TextArea textArea = new TextArea();
		textArea.setBounds(0, 63, 421, 163);
		panel_1.add(textArea);
		
		//Boton consultar
		JButton btnNewButton_1 = new JButton("Pulse para consultar la agenda");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					 textArea.setText(""); //Limpiamos pantalla
					 textArea.append(Consultar.consultar(conexion)); //Llamamos a Consultar y lo mostramos en el area
					 
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_1.setBounds(58, 5, 287, 32);
		panel_1.add(btnNewButton_1);
		
		JTextPane txtpnNombre_1 = new JTextPane();
		txtpnNombre_1.setBackground(Color.WHITE);
		txtpnNombre_1.setText("Nombre");
		txtpnNombre_1.setBounds(10, 40, 54, 19);
		panel_1.add(txtpnNombre_1);
		
		JTextPane txtpnDireccin = new JTextPane();
		txtpnDireccin.setText("Dirección");
		txtpnDireccin.setBounds(119, 38, 54, 19);
		panel_1.add(txtpnDireccin);
		
		JTextPane txtpnPoblacin_1 = new JTextPane();
		txtpnPoblacin_1.setText("Población");
		txtpnPoblacin_1.setBounds(249, 38, 60, 19);
		panel_1.add(txtpnPoblacin_1);
		
		JTextPane txtpnCp_1 = new JTextPane();
		txtpnCp_1.setText("CP");
		txtpnCp_1.setBounds(377, 40, 34, 19);
		panel_1.add(txtpnCp_1);
		
		//Pestaña Word
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Word", null, panel_2, null);
		panel_2.setLayout(null);
		
		//Boton para seleccionar la carpeta destino, con File chooser, devuelve la Ruta
		JButton btnNewButton_3 = new JButton("Seleccionar carpeta destino");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				txt = new JTextField(30);
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				
				int result = fileChooser.showOpenDialog(btnNewButton_2);
				if (result != JFileChooser.CANCEL_OPTION) { }
				
				File fileName = fileChooser.getSelectedFile();

				if ((fileName == null) || (fileName.getName().equals(""))) {
				    txt.setText("...");
				} else {
				    txt.setText(fileName.getAbsolutePath());
				}
				
				ruta = txt.getText();
				
			}
		});
		
			
		btnNewButton_3.setBounds(252, 10, 159, 21);
		panel_2.add(btnNewButton_3);
		
		JTextPane txtpnDescargueUnDocx = new JTextPane();
		txtpnDescargueUnDocx.setForeground(Color.BLACK);
		txtpnDescargueUnDocx.setText("Descargue un docx de toda la agenda:");
		txtpnDescargueUnDocx.setBounds(10, 10, 219, 29);
		panel_2.add(txtpnDescargueUnDocx);
		
		//Boton Descargar
		JButton btnNewButton_4 = new JButton("Descargar");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String nombre = "agendaCompleta"; //Nombre de salida del word
				word = new Word(nombre, ruta, conexion); //Se crea un objeto Word con el nombre y ruta donde se guarda
				word.start();	//Se crea
				
			}
		});
		btnNewButton_4.setBounds(252, 46, 111, 21);
		panel_2.add(btnNewButton_4);
		
		JTextPane textPane = new JTextPane();
		textPane.setText("1.");
		textPane.setBounds(235, 12, 16, 19);
		panel_2.add(textPane);
		
		JTextPane textPane_1 = new JTextPane();
		textPane_1.setText("2.");
		textPane_1.setBounds(235, 46, 16, 19);
		panel_2.add(textPane_1);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 113, 401, 13);
		panel_2.add(separator);
		
		//Descargar docx de un alumno
		JTextPane txtpnDescargueUnDocx_1 = new JTextPane();
		txtpnDescargueUnDocx_1.setText("Descargue un docx sobre un alumno:");
		txtpnDescargueUnDocx_1.setBounds(10, 125, 219, 19);
		panel_2.add(txtpnDescargueUnDocx_1);
		
		//Boton para seleccionar destino, con file chooser, devuelve Ruta
		JButton btnNewButton_5 = new JButton("Seleccionar carpeta destino");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				txt = new JTextField(30);
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				
				int result = fileChooser.showOpenDialog(btnNewButton_2);
				if (result != JFileChooser.CANCEL_OPTION) { }
				
				File fileName = fileChooser.getSelectedFile();

				if ((fileName == null) || (fileName.getName().equals(""))) {
				    txt.setText("...");
				} else {
				    txt.setText(fileName.getAbsolutePath());
				}
				
				ruta = txt.getText();

			}
		});
		btnNewButton_5.setBounds(252, 123, 159, 21);
		panel_2.add(btnNewButton_5);
		
		//Boton descargar
		JButton btnNewButton_6 = new JButton("Descargar");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String nombre = textField_4.getText(); //Nombre salida del word
				word = new Word(nombre, ruta, conexion); //Se crea un objeto Word con el nombre y ruta donde se guarda
				word.start(); //Se crea
				
			}
		});
		btnNewButton_6.setBounds(252, 181, 111, 21);
		panel_2.add(btnNewButton_6);
		
		JTextPane textPane_2 = new JTextPane();
		textPane_2.setText("1.");
		textPane_2.setBounds(235, 125, 16, 19);
		panel_2.add(textPane_2);
		
		JTextPane textPane_3 = new JTextPane();
		textPane_3.setText("3.");
		textPane_3.setBounds(235, 183, 16, 19);
		panel_2.add(textPane_3);
		
		JTextPane txtpnNombre_2 = new JTextPane();
		txtpnNombre_2.setText("2. Nombre:");
		txtpnNombre_2.setBounds(235, 154, 70, 19);
		panel_2.add(txtpnNombre_2);
		
		textField_4 = new JTextField();
		textField_4.setBounds(315, 154, 96, 19);
		panel_2.add(textField_4);
		textField_4.setColumns(10);
	}
}