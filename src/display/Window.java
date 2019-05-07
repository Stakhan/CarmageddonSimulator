package display;


import java.awt.BorderLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JFrame;

import engine.Simulation;
import model.ConfigureStructure;

public class Window extends JFrame{
	/**
	 * Cette classe crée une fenêtre simple pour l'affichage du panel affichant la simulation
	 * 
	 */
	// on fait heriter notre classe de JFrame (fenetre graphique)
	// pour pouvoir eventuellement personnaliser son comportement (surcharge)
	
	public Window(ConfigureStructure structConfig, Simulation simulation){
		this.init(structConfig, simulation); // on separe le constructeur du code d’initialisation des parametres graphiques (bonne pratique)
	}
	
	private void init(ConfigureStructure structConfig, Simulation simulation){
		
		GridPanel mainPanel = new GridPanel(structConfig, simulation); // on instancie un nouveal objet MyPanel
		
		this.setLayout(new BorderLayout());
	    //Au centre
	    this.getContentPane().add(mainPanel, BorderLayout.CENTER);
	    //Au nord
//	    this.getContentPane().add(new JButton("NORTH"), BorderLayout.NORTH);
	    //Au sud
//	    this.getContentPane().add(new JButton("SOUTH"), BorderLayout.SOUTH);
	    //À l'ouest
//	    this.getContentPane().add(new JButton("WEST"), BorderLayout.WEST);
	    //À l'est
//	    this.getContentPane().add(new JButton("EAST"), BorderLayout.EAST);
		
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
        
        
		this.setTitle("Etat de la simulation"); // titre de la fenetre
		this.setSize(structConfig.hDisplaySize, structConfig.vDisplaySize); // taille de la fenetre. On utilise plutot setPreferredSize si le composant parent
		this.setResizable(true); //On empêche/autorise le redimensionnement
		// utilise un LayoutManager.
		//this.setLocationRelativeTo(null); // positionnement centre par rapport a l’ecran
		
		//Placement de la fenêtre sur la moitié droite
		int x = (int) rect.getMaxX() - this.getWidth();
        int y = 0;
        this.setLocation(x, y);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // comportement lors d’un clic sur la croix rouge
		this.setVisible(true); // on la rend visible
	}
	
	
}
