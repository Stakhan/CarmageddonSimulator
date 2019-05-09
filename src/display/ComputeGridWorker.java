package display;

import java.util.concurrent.TimeUnit;

import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import engine.Simulation;

public class ComputeGridWorker extends SwingWorker {
	private GridPanel gridPanel;
	public ComputeGridWorker(GridPanel gridPanel) {
		this.gridPanel = gridPanel;
	}
	@Override
	protected Object doInBackground() throws Exception {
        System.out.println("START SIMULATION");

		while (gridPanel.getContinueRunning()) {
			
//		while (gridPanel.getDisplayState().getStep() < 10) {
			  System.out.println("step "+gridPanel.getDisplayState().getStep());
			  gridPanel.getSimulation().nextState();
			  gridPanel.setDisplayState(gridPanel.getSimulation().getLastState());
			  gridPanel.repaint();
			  try {
				  TimeUnit.MILLISECONDS.sleep(100);
			  } catch (InterruptedException e1) {
				  // TODO Auto-generated catch block
				  e1.printStackTrace();
			  }
		}
		return null;
	}
	
	public void done(){            
        if(SwingUtilities.isEventDispatchThread())
          System.out.println("STOP SIMULATION");
      }

}
