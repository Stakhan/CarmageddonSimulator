package display;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import engine.Simulation;
import model.ConfigureStructure;

public class Window extends JFrame implements ActionListener{

	
	public Window(ConfigureStructure structConfig, Simulation simulation){
		// *** Useful Dimension ***
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int lengthFactor = (int) (screenSize.height)/simulation.getColumnNb();
		
		int simulationLength = simulation.getColumnNb()*(lengthFactor);
		int simulationHeight = simulation.getLineNb()*(lengthFactor);
		
		int buttonLength = 300;
		int buttonHeight = 80;
		
		//Dimension windowSize = new Dimension(simulationLength, simulationHeight);

		//System.out.println(simulation.getColumnNb() + "," + simulation.getLineNb());
		
		//=====================================================================================================
		// *** Creation of main window ***
		this.setTitle("CARMAGEDDON");
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setBounds(0,0,simulationLength + buttonLength + 20*2, simulationHeight + buttonHeight);
	    
		//---------------------------------------------------------------
		// *** Main Panel ***
		JPanel content = new JPanel(null);
		content.setBackground(Color.WHITE);

		// *** Grid Panel of the simulation ***
		GridPanel gridPanel = new GridPanel(structConfig, simulation);

		// *** Text Area ***
		JTextArea textStats = new JTextArea();
		String displayStats = simulation.getLastState().getStatistics().toString();
		textStats.append(displayStats);
		textStats.setEditable(false);
		textStats.setOpaque(false);
		
		// *** Button ***
		// Adding a button to compute stats
		JButton buttonStats = new JButton("Updtate Stats");
		buttonStats.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String displayStats = simulation.getLastState().getStatistics().toString();
				textStats.setText(displayStats);
				
				// Focus on the mainPanel
				gridPanel.setFocusable(true);
			    gridPanel.requestFocus();
			    gridPanel.addKeyListener(gridPanel); 
			}
		});
		
		
		// Adding a button to compute flows
		JButton buttonFlows = new JButton("Updtate Flows");
		buttonStats.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				// Focus on the mainPanel
				gridPanel.setFocusable(true);
			    gridPanel.requestFocus();
			    gridPanel.addKeyListener(gridPanel); 
			}
		});
		
		
		
		
		
		


		// ====================================================================================================
		// *** Pannel Size | Adding Pannel to the main content ***
		gridPanel.setBounds(0, 0, simulationLength, simulationHeight);
		content.add(gridPanel);

		textStats.setBounds(simulationLength + 20, 0, buttonLength, buttonHeight);
		content.add(textStats);
		
		buttonStats.setBounds(simulationLength + 20, 0 + buttonHeight + 20, buttonLength, buttonHeight);
		content.add(buttonStats);

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