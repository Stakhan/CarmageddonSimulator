package immobile.lights;

import enumeration.Color;
import immobile.structures.Road;

/**
* Abstract Traffic Light class
*/
public abstract class TrafficLight {
	/**
	* Traffic Light orientation identifies the road
	*/
	protected Road road;
	/**
	* Yellow light duration
	*/
	protected Color currentColor;

	/*
	 * deprecated
	protected int timeGreen;
	protected int timeRed;
	 */

	/**
	* Constructor
	*/
	public TrafficLight(Road road, Color currentColor) { // deprecated: , int timeGreen, int timeRed
		super();
		this.road = road;
		this.currentColor = currentColor;
		/*
		 * deprecated
		this.timeGreen = timeGreen;
		this.timeRed = timeRed;
		 */
	}

	/**
	* Setters
	*/
	public void setOrientation(Road road) {
		this.road = road;
	}
	public void setCurrentColor(Color currentColor) {
		this.currentColor = currentColor;
	}
	/* 
	 * deprecated
	public void setTimeGreen(int timeGreen) {
		this.timeGreen = timeGreen;
	}
	public void setTimeRed(int timeRed) {
		this.timeRed = timeRed;
	}

	public void changeColor() {
		switch (currentColor) {
		case Green: currentColor = Color.Red;
		case Red: currentColor = Color.Green;
		}
	}
	 */
}
