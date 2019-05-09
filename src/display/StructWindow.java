package display;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import engine.Simulation;
import model.ConfigureStructure;

public class StructWindow extends JFrame implements ActionListener{

	
	public StructWindow(){
		// *** Useful Dimension ***
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		int screenLength = screenSize.width/2;
		int screenHeight = screenSize.height/2;
		
		int buttonLength = 100;
		int buttonHeight = 50;
		
		//Dimension windowSize = new Dimension(simulationLength, simulationHeight);

		//System.out.println(simulation.getColumnNb() + "," + simulation.getLineNb());
		
		//=====================================================================================================
		// *** Creation of main window ***
		this.setTitle("CARMAGEDDON");
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setBounds(0,0,screenLength + buttonLength + 20*2, screenHeight + buttonHeight);
	    
		//---------------------------------------------------------------
		// *** Main Panel ***   
		JPanel content = new JPanel(null);
		
		//---------------------------------------------------------------
		// *** Image Background CARMAGEDDON ! ***
		ImageIcon image = new ImageIcon("images/light_full.png");
		int imageLength = image.getIconWidth();
		int imageHeight = image.getIconWidth();
	    JLabel imageLabel = new JLabel(image);
	    JScrollPane imagePanel = new JScrollPane(imageLabel);
		
		//---------------------------------------------------------------
		// *** Text Area ***
		JLabel textStart = new JLabel("Simulation Size : ");
		
		JTextField textSize = new JTextField(20);

		//----------------------------------------------------------------
		JLabel textPixel = new JLabel("Display Size : ");
		
		JTextField textPixelSize = new JTextField(20);
		
		//----------------------------------------------------------------
		// *** Button Start***
		JButton buttonStart = new JButton("Start");
		buttonStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String sizeString = textSize.getText();
				int size = Integer.parseInt(sizeString);
				
				String sizePixString = textPixelSize.getText();
				int sizePix = Integer.parseInt(sizePixString);

				ConfigureStructure structConfig = new ConfigureStructure(size, sizePix, true);
				Simulation simulation1 = new Simulation(structConfig);
				simulation1.init();

				//Writting simulation state to file
				//simulation1.getState(0).writeToFile("simulation-state1.grid");
				
				
				//Affichage avec Java2d
				new Window(structConfig, simulation1);
				dispose();  // close the JFrame
			}
		});

		// ====================================================================================================
		// *** Pannel Size ***
		
		
		imagePanel.setBounds(50, 50, imageLength, imageHeight);
		//content.add(imagePanel);
		
		textStart.setBounds(50, 50, buttonLength, buttonHeight);
		content.add(textStart);
		
		textSize.setBounds(buttonLength + 60, 50, buttonLength, buttonHeight);
		content.add(textSize);
		
		textPixel.setBounds(50, buttonHeight + 60, buttonLength, buttonHeight);
		content.add(textPixel);
		
		textPixelSize.setBounds(buttonLength + 60, buttonHeight + 60, buttonLength, buttonHeight);
		content.add(textPixelSize);
		
		
		buttonStart.setBounds((buttonLength + 60)*2, 50, buttonLength, buttonHeight*2 + 10);
		content.add(buttonStart);
		
		
		// *** Adding different panels to the main Panel ***
		
	    this.getContentPane().add(content);
	    
	    // *** Display ***

		this.setVisible(true);
		
	}

	
	// BUTTON
	@Override
	public void actionPerformed(ActionEvent e) {
	}
	
	
}