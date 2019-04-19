package display;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;

import model.ConfigureStructure;
import model.SimulationState;

public class Window extends JFrame{
	// on fait heriter notre classe de JFrame (fenetre graphique)
	// pour pouvoir eventuellement personnaliser son comportement (surcharge)
	
	public Window(ConfigureStructure structConfig, SimulationState state){
		this.init(structConfig, state); // on separe le constructeur du code d’initialisation des parametres graphiques (bonne pratique)
	}
	
	private void init(ConfigureStructure structConfig, SimulationState state){
		Panel mainPanel = new Panel(structConfig, state); // on instancie un nouveal objet MyPanel
		this.setContentPane(mainPanel);
		
		this.setTitle("Etat de la simulation"); // titre de la fenetre
		this.setSize(2*structConfig.columnNb, 2*(structConfig.lineNb+15)); // taille de la fenetre. On utilise plutot setPreferredSize si le composant parent
		this.setResizable(false); //On empêche le redimensionnement
		// utilise un LayoutManager.
		this.setLocationRelativeTo(null); // positionnement centre par rapport a l’ecran
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // comportement lors d’un clic sur la croix rouge
		this.setVisible(true); // on la rend visible
	}
	
	
}
