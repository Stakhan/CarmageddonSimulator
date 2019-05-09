package stats;

import java.util.List;

public class ComputeStatistics {

	// Static class

	private ComputeStatistics() {
		
	}
	
	
	
	/**
	 * Compute the average of a list. If the list is empty, it returns 0.
	 * @param list : the list of all parameters
	 * @return the average of the list. 
	 */
	public static double average(List<Double> list) {
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
	public static double variance(List<Double> list) {
		double var = 0;
		double av = average(list);
		for (int i = 0; i < list.size(); i++) {
			var += Math.pow(list.get(i) - av, 2);
		}
		var /= list.size();
		return var;
	}
	
	
	
	
	
	
	
	
}
