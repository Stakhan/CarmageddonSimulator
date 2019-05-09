package display;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

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
	    setBounds(0,0,simulationLength + buttonLength + 20*3, simulationHeight + buttonHeight);
	    
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
		textStats.setOpaque(true);
		
		
		//----------------------------------------------------------------------------
		JLabel flowPedLabel = new JLabel("Flow Pedestrian : ");
		JTextField flowPedUser = new JTextField(20);
		JLabel flowCarLabel = new JLabel("Flow Car : ");
		JTextField flowCarUser = new JTextField(20);
		
		//=============================================================================
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
			}
		});
		//----------------------------------------------------------------------------
		// Adding a button to compute flows
		JButton buttonFlows = new JButton("Updtate Flows");
		buttonFlows.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				// Focus on the mainPanel
				gridPanel.setFocusable(true);
			    gridPanel.requestFocus();
			}
		});
		//----------------------------------------------------------------------------
		// Adding a button to add a random pedestrian
		JButton buttonPedestrian = new JButton("Add Pedestrian");
		buttonPedestrian.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				simulation.getMovingParts().addRandomPedestrian();
				
				// Focus on the mainPanel
				gridPanel.setFocusable(true);
			    gridPanel.requestFocus();
			}
		});
		//----------------------------------------------------------------------------
		// Adding a button to add a random car
		JButton buttonCar = new JButton("Add Car");
		buttonCar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				simulation.getMovingParts().addRandomCar();
				
				// Focus on the mainPanel
				gridPanel.setFocusable(true);
			    gridPanel.requestFocus();
			}
		});
		//----------------------------------------------------------------------------
		// Adding a next button
		JButton buttonNext = new JButton("Next");
		buttonNext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//System.out.println(this.simulation.getListStates().size()+" et "+this.displayState.);
				if (gridPanel.getSimulationState().getStep() < gridPanel.getSimulation().getListStates().size()-1) { //test if next step has already been computed
					gridPanel.setSimulationState(simulation.getState(gridPanel.getSimulationState().getStep()+1));
				}
				else { //if not, compute it
					simulation.nextState();
					gridPanel.setSimulationState(simulation.getLastState());
				}
				repaint();
				System.out.println("step "+gridPanel.getSimulationState().getStep()+": "+simulation.getLastState().getGrid().toString());
				
				// Focus on the mainPanel
				gridPanel.setFocusable(true);
			    gridPanel.requestFocus();
			}
		});
		//-------------------------------------------------------------------------------
		// Adding a previous button
		JButton buttonPrevious = new JButton("Previous");
		buttonPrevious.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//System.out.println(this.simulation.getListStates().size()+" et "+this.displayState.);
				if (gridPanel.getSimulationState().getStep() > 0) { //test if there is a previous state
					gridPanel.setSimulationState(simulation.getState(gridPanel.getSimulationState().getStep()-1));
				}
				repaint();
				System.out.println("step "+gridPanel.getSimulationState().getStep()+": " +
									simulation.getState(gridPanel.getSimulationState().getStep()).getGrid().toString());
				
				// Focus on the mainPanel
				gridPanel.setFocusable(true);
			    gridPanel.requestFocus();
			}
		});
		// Adding a start button
		JButton buttonStart = new JButton("Start");
		buttonStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				// Focus on the mainPanel
				gridPanel.setFocusable(true);
			    gridPanel.requestFocus();
			}
		});
		


		//=============================================================================
		// *** Pannel Size | Adding Pannel to the main content ***
		// Main
		gridPanel.setBounds(0, 0, simulationLength, simulationHeight);
		content.add(gridPanel);

		// Stats
		textStats.setBounds(simulationLength + 20, 0, buttonLength, buttonHeight);
		content.add(textStats);
		
		buttonStats.setBounds(simulationLength + 20, buttonHeight + 20, buttonLength, buttonHeight);
		content.add(buttonStats);

		//---------------------------------------------------------------------------------------------
		// Flows
		flowPedLabel.setBounds(simulationLength + 20, (buttonHeight + 20)*2, (int) buttonLength/2, (int) buttonHeight/2);
		content.add(flowPedLabel);
		
		flowPedUser.setBounds(simulationLength + 20 + (int) buttonLength/2 + 10, (buttonHeight + 20)*2, (int) buttonLength/2, (int) buttonHeight/2);
		content.add(flowPedUser);
		
		flowCarLabel.setBounds(simulationLength + 20, (buttonHeight + 20)*2 + (int) buttonHeight/2 + 10, (int) buttonLength/2, (int) buttonHeight/2);
		content.add(flowCarLabel);
		
		flowCarUser.setBounds(simulationLength + 20 + (int) buttonLength/2 + 10, (buttonHeight + 20)*2 + (int) buttonHeight/2 + 10, (int) buttonLength/2, (int) buttonHeight/2);
		content.add(flowCarUser);
		
		//----------------------------------------------------------------------------------------------
		// Button Ped / Car
		buttonPedestrian.setBounds(simulationLength + 20,
				(buttonHeight + 20)*3, (int) buttonLength/2, (int) buttonHeight/2);
		content.add(buttonPedestrian);
		
		buttonCar.setBounds(simulationLength + 20 + 10 + (int) buttonLength/2,
				(buttonHeight + 20)*3, (int) buttonLength/2, (int) buttonHeight/2);
		content.add(buttonCar);
		
		//-----------------------------------------------------------------------------------------------
		// Next | Pause/Start | Previous
		buttonPrevious.setBounds(simulationLength + 20,
				(buttonHeight + 20)*4, (int) buttonLength/3, (int) buttonHeight/2);
		content.add(buttonPrevious);
		
		buttonNext.setBounds(simulationLength + 20 + 2 * (int) (buttonLength/3) + 2*5,
				(buttonHeight + 20)*4, (int) buttonLength/3, (int) buttonHeight/2);
		content.add(buttonNext);
		
		buttonStart.setBounds(simulationLength + 20 + (int) (buttonLength/3) + 5,
				(buttonHeight + 20)*4, (int) buttonLength/3, (int) buttonHeight/2);
		content.add(buttonStart);
		
		//
		
		
		
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