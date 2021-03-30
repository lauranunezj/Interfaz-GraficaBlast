package practicaBlastGUI;
import java.awt.Color;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.BevelBorder;

import blast.BlastController;

public class BlastGUI {
	
	private static final String dataBaseFile = new String("resources/yeast.aa");
	private static final String dataBaseIndexes = new String("resources/yeast.aa.indexs");
	
	
	
	public static void main(String args[]){
		
		BlastController bCnt = new BlastController();
		
		try{
		
		//Creamos la ventana princial
		JFrame ventanaP = new JFrame("BlasGUIMenu");
		ventanaP.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Creamos el panel donde van a estar nuestros parametros.
		JPanel panel1 = new JPanel();
		panel1.setLayout(new FlowLayout());
		panel1.setBackground(Color.PINK);
		panel1.setBorder(BorderFactory.createLineBorder(Color.black));
		
		//Botones para seleccionar si queremos una secuencias de proteinas o de nucleotidos. 
		JRadioButton proteinasButton = new JRadioButton("Proteins sequence");
		JRadioButton nucleoButton = new JRadioButton("Nucleotid sequence");
		ButtonGroup group = new ButtonGroup();
		group.add(proteinasButton);
		group.add(nucleoButton);
		proteinasButton.setBackground(Color.pink);
		nucleoButton.setBackground(Color.pink);
		proteinasButton.setSelected(true);
		
		//Añadimos los botones al panel.
		panel1.add(proteinasButton);
		panel1.add(nucleoButton);
		
		//Texto para que el usuario introduzca el porcentaje de la consulta.
		JLabel etiquetaPorcentaje = new JLabel("Enter the percentage of success (values between 0 and 1):");
		etiquetaPorcentaje.setBorder(BorderFactory.createLineBorder(Color.white));
		JTextField porcentajeCaja = new JTextField("",5);
		
		panel1.add(etiquetaPorcentaje);
		panel1.add(porcentajeCaja);
		
		//Introducir secuencia o elegir alguna. 
		JLabel etiquetaSecuencia = new JLabel("Enter the sequence: ");
		etiquetaSecuencia.setBorder(BorderFactory.createLineBorder(Color.white));
		JComboBox<String> secuenciaCaja = new JComboBox<String>();
		secuenciaCaja.setEditable(true);
		
		secuenciaCaja.addActionListener(new ActionListener() {//evento para añadir los nuevos elementos 
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String nuevasec = secuenciaCaja.getEditor().getItem().toString().toUpperCase();
				secuenciaCaja.addItem(nuevasec);
				for(int i=0;i<secuenciaCaja.getItemCount();i++){
					if((secuenciaCaja.getItemAt(i).equals(nuevasec) && secuenciaCaja.getItemAt(i+1)!=null )) {
						secuenciaCaja.removeItem(nuevasec);
					}
				}		
			}
		});
		
		panel1.add(etiquetaSecuencia);
		panel1.add(secuenciaCaja);
		
		//Boton de busqueda y Area de resultado
		JTextArea areaResultado = new JTextArea(16,55);
		areaResultado.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		
		JButton busqueda= new JButton("Start Search");
		busqueda.setBackground(Color.WHITE);
		busqueda.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				char tipo;
				float porcentaje= Float.parseFloat(porcentajeCaja.getText());
				String seq = secuenciaCaja.getSelectedItem().toString();
				
				if(proteinasButton.isSelected()) {
					tipo='p';
				}else {
					tipo='n';
				}
				try {
					
					if(tipo=='n') {
						areaResultado.setText("No hay ejemplos disponibles de nucleotidos");
					}else {
						areaResultado.setText(bCnt.blastQuery(tipo, dataBaseFile, dataBaseIndexes, porcentaje, seq));
						
					}
					
					
				} catch (Exception e1) {
					
					System.out.println("Error en la llamada: " + e1.toString());
				}
			}
			
		});
		
		panel1.add(busqueda);
		panel1.add(areaResultado);
		
		
		//Barra de desplazamiento.
		JScrollPane barra = new JScrollPane(areaResultado);
		barra.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		barra.setBackground(Color.BLACK);
		panel1.add(barra);
		
		
		//Añadimos el panel a la ventana. 
		ventanaP.add(panel1);
		ventanaP.pack();
		ventanaP.setSize(800, 500);
		ventanaP.setVisible(true);
		
	}catch(Exception exc){
		System.out.println("Error en la llamada: " + exc.toString());
		
		}
	}
}

