package display;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import engine.Simulation;
import model.ConfigureStructure;

public class Window extends JFrame{
	/**
	 * Cette classe cree une fenetre simple pour l'affichage du panel affichant la simulation
	 * 
	 */
	// on fait heriter notre classe de JFrame (fenetre graphique)
	// pour pouvoir eventuellement personnaliser son comportement (surcharge)
	
	public Window(ConfigureStructure structConfig, Simulation simulation){
		this.init(structConfig, simulation); // on separe le constructeur du code d’initialisation des parametres graphiques (bonne pratique)
	}
	
	private void init(ConfigureStructure structConfig, Simulation simulation){
		
		GridPanel mainPanel = new GridPanel(structConfig, simulation); // on instancie un nouveal objet MyPanel
		JTextArea textPane = new JTextArea();
		textPane.append("test");
		
		this.setLayout(new BorderLayout());
	    //Au centre
	    this.getContentPane().add(mainPanel, BorderLayout.CENTER);

	    this.getContentPane().add(textPane, BorderLayout.EAST);
		

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(screenSize.width/2,0,screenSize.width/2, screenSize.height);
        
		this.setTitle("Etat de la simulation"); // titre de la fenetre
		//this.setSize(structConfig.hDisplaySize, structConfig.vDisplaySize); // taille de la fenetre. On utilise plutot setPreferredSize si le composant parent
		//this.setResizable(true); //On empêche/autorise le redimensionnement

		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // comportement lors d’un clic sur la croix rouge
		this.setVisible(true); // on la rend visible
		
	}
	
	
}
