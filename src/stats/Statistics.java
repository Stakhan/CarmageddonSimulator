package stats;

import java.util.List;

import model.SimulationState;

public class Statistics {
	
	private double averageCrossingDurationPedestrian;
	private double averageCrossingDurationCar;
	private double averageWaitingTimePedestrian;
	private double averageWaitingTimeCar;
	
	
	private SimulationState simulationState;
	
	
	public Statistics(SimulationState simulationState) {
		this.averageCrossingDurationPedestrian = 0;
		this.averageCrossingDurationCar = 0;
		this.averageWaitingTimePedestrian = 0;
		this.averageWaitingTimeCar = 0;
		this.simulationState = simulationState;
	}
	
	
	public Statistics(SimulationState simulationState, double averageCrossingDurationPedestrian, double averageCrossingDurationCar,
			double averageWaitingTimePedestrian, double averageWaitingTimeCar) {
		
		this.averageCrossingDurationPedestrian = averageCrossingDurationPedestrian;
		this.averageCrossingDurationCar = averageCrossingDurationCar;
		this.averageWaitingTimePedestrian = averageWaitingTimePedestrian;
		this.averageWaitingTimeCar = averageWaitingTimeCar;
		this.simulationState = simulationState;
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
		return Math.floor(d * 1000) / 1000;
	}
	
	
	
	// SETTERS
	
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

	public void setSimulationState(SimulationState simulationState) {
		this.simulationState = simulationState;
	}
	
	
	// GETTERS
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
	
	public SimulationState getSimulationState() {
		return simulationState;
	}
	
	
	@Override
	public String toString() {
		return	"* Statistics *\nAverage Pedestrians : \n CrossingDuration " + trunc(averageCrossingDurationPedestrian) + " WaitingTime " + trunc(averageWaitingTimePedestrian) +
				"\nAverage Cars : \n CrossingDuration " + trunc(averageCrossingDurationCar) + " WaitingTime " + trunc(averageWaitingTimeCar);
		
	}
	
}
