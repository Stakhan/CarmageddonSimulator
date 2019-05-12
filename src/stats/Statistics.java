package stats;

import java.util.ArrayList;
import java.util.List;

import engine.Simulation;
import model.SimulationState;

public class Statistics {
	
	private double averageCrossingDurationPedestrian;
	private double averageCrossingDurationCar;
	private double averageWaitingTimePedestrian;
	private double averageWaitingTimeCar;
	
	private List<Double> listWaitingTimeCar;
	private List<Double> listWaitingTimePedestrian;
	private List<Double> listCrossingDurationCar;
	private List<Double> listCrossingDurationPedestrian;
	
	
	private Simulation simulation;
	
	
	public Statistics(Simulation simulation) {
		this.averageCrossingDurationPedestrian = 0;
		this.averageCrossingDurationCar = 0;
		this.averageWaitingTimePedestrian = 0;
		this.averageWaitingTimeCar = 0;
		this.listWaitingTimeCar = new ArrayList<Double>();
		this.listWaitingTimePedestrian = new ArrayList<Double>();
		this.listCrossingDurationCar = new ArrayList<Double>();
		this.listCrossingDurationPedestrian = new ArrayList<Double>();
		
		this.simulation = simulation;
	}
	
	
	/**
	 * Compute the average of a list. If the list is empty, it returns 0.
	 * @param list : the list of all parameters
	 * @return the average of the list. 
	 */
	public double average(List<Double> list) {
		double av = 0;
		for (int i = 0; i < list.size(); i++) {
			av += list.get(i);
		}
		av /= list.size();
		
		return av;
	}
	
	
	/**
	 * Compute the variance of a list
	 * This variance is biased (factor n/(n-1) not take into account)
	 * @param list
	 * @return the variance of a list
	 */
	public double variance(List<Double> list) {
		double var = 0;
		double av = average(list);
		for (int i = 0; i < list.size(); i++) {
			var += Math.pow(list.get(i) - av, 2);
		}
		var /= list.size();
		return var;
	}
	
	public double trunc(double d) {
		return Math.floor(d * 10) / 10;
	}
	
	
	
	// SETTERS

	public List<Double> getListWaitingTimePedestrian() {
		return listWaitingTimePedestrian;
	}


	public void setListWaitingTimePedestrian(List<Double> listWaitingTimePedestrian) {
		this.listWaitingTimePedestrian = listWaitingTimePedestrian;
	}


	public List<Double> getListCrossingDurationCar() {
		return listCrossingDurationCar;
	}


	public void setListCrossingDurationCar(List<Double> listCrossingDurationCar) {
		this.listCrossingDurationCar = listCrossingDurationCar;
	}


	public List<Double> getListCrossingDurationPedestrian() {
		return listCrossingDurationPedestrian;
	}


	public void setListCrossingDurationPedestrian(List<Double> listCrossingDurationPedestrian) {
		this.listCrossingDurationPedestrian = listCrossingDurationPedestrian;
	}


	public void setListWaitingTimeCar(List<Double> listWaitingTimeCar) {
		this.listWaitingTimeCar = listWaitingTimeCar;
	}


	public void setAverageCrossingDurationPedestrian(double averageCrossingDurationPedestrian) {
		this.averageCrossingDurationPedestrian = averageCrossingDurationPedestrian;
	}

	public void setAverageCrossingDurationCar(double averageCrossingDurationCar) {
		this.averageCrossingDurationCar = averageCrossingDurationCar;
	}
	
	public void setAverageWaitingTimePedestrian(double averageWaitingTimePedestrian) {
		this.averageWaitingTimePedestrian = averageWaitingTimePedestrian;
	}

	public void setAverageWaitingTimeCar(double averageWaitingTimeCar) {
		this.averageWaitingTimeCar = averageWaitingTimeCar;
	}

	public void setSimulation(Simulation simulation) {
		this.simulation = simulation;
	}
	
	
	// GETTERS
	
	public List<Double> getListWaitingTimeCar() {
		return listWaitingTimeCar;
	}	
	
	public double getAverageCrossingDurationPedestrian() {
		return averageCrossingDurationPedestrian;
	}
	
	public double getAverageCrossingDurationCar() {
		return averageCrossingDurationCar;
	}
	
	public double getAverageWaitingTimePedestrian() {
		return averageWaitingTimePedestrian;
	}
	
	public double getAverageWaitingTimeCar() {
		return averageWaitingTimeCar;
	}
	
	public Simulation getSimulation() {
		return simulation;
	}
	
	
	@Override
	public String toString() {
		return	"* Statistics *\nAverage Pedestrians : \n CrossingDuration " + trunc(averageCrossingDurationPedestrian) + " WaitingTime " + trunc(averageWaitingTimePedestrian) +
				"\nAverage Cars : \n CrossingDuration " + trunc(averageCrossingDurationCar) + " WaitingTime " + trunc(averageWaitingTimeCar);
		
	}
	
}
