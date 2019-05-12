package display;

import java.util.concurrent.TimeUnit;

import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import engine.Simulation;
import stats.Statistics;

public class ComputeGridWorker extends SwingWorker {
	private GridPanel gridPanel;
	private Window window;

	public ComputeGridWorker(GridPanel gridPanel) {
		this.gridPanel = gridPanel;
	}
	@Override
	protected Object doInBackground() throws Exception {
		while (gridPanel.getContinueRunning()) {
			try {
				  System.out.println("*** Step : " + gridPanel.getDisplayState().getStep() + " 	***");
				  gridPanel.getSimulation().nextState();
				  gridPanel.setDisplayState(gridPanel.getSimulation().getLastState());
				  gridPanel.repaint();
  
				  
				  try {
					  TimeUnit.MILLISECONDS.sleep(150);
				  } catch (InterruptedException e1) {
					  // TODO Auto-generated catch block
					  e1.printStackTrace();
				  }
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return null;
	}
	
	public void done(){            
        if(SwingUtilities.isEventDispatchThread())
          	System.out.println("*** SIMULATION PAUSED ***\n*** CLICK ON START TO CONTINUE ***");
      }
	
}
