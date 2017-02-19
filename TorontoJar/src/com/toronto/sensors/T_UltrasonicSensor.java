package com.toronto.sensors;

import edu.wpi.first.wpilibj.AnalogInput;

/**
 * This class extends the FRC AnalogInput class to add some additional
 * functionality related to Ultrasonic sensors.  
 * 
 * The MaxBotics series of Ultrasonic sensors uses an analog 
 * input.  The analog input can then be converted into 
 * a distance measurement using a d = mV + b type equation.
 * 
 */
public class T_UltrasonicSensor extends AnalogInput {

	// Cutover voltage - by default, only one equation
	// is used for the sensor.
	private double v40 = 5.0; // voltage at 40in

	double m1 = 79.92;        // default slope inches/volt
	double b1 = 11.32;        // default offset inches
	
	double m2, b2;

	/**
	 * Ultrasonic sensor
	 * @param analogInputPort the analog port the ultrasonic
	 * sensor is connected to.
	 */
	public T_UltrasonicSensor(int analogInputPort) {
		super(analogInputPort);
	}

	/**
	 * The calibrate routine takes the voltage measurement at 
	 * 20in, 40in and 80in and calculates two linear equations
	 * that describe the distance <-> voltage relationship.
	 * <p>
	 * There are two line segments because one segment does not
	 * adequately handle near and far distances.
	 * <p>
	 * The process for calibration is to set the sensor at a 
	 * distance to an object of 20, 40 and 80 inches and then
	 * input those voltage measurements into the calibration routine
	 * 
	 * @param v20 the voltage measured at 20in distance
	 * @param v40 the voltage measured at 40in distance
	 * @param v80 the voltage measured at 80in distance
	 */
	public void calibrate(double v20, double v40, double v80) {

		this.v40 = v40; // crossover voltage between the two equations

		// Calculate m1, b1 for voltages less than v40
		m1 = (40.0 - 20.0) / (v40 - v20);
		b1 =  20.0 - (m1 * v20);

		// Calculate m2, b2 for voltages greater than v40
		m2 = (80 - 40) / (v80 - v40);
		b2 =  80 - (m2 * v80);
	}

	/**
	 * Get the distance measurement in inches from the back
	 * face of the Ultrasonic Sensor
	 * @return double distance in inches
	 */
	public double getDistance() {

		double distance = 0;

		double voltage = super.getVoltage();
		
		if (voltage <= v40) {

			// Calculate the distance based on m1, b1
			distance = m1 * voltage + b1;
		
		} else {
		
			// Calculate the distance based on m2, b2
			distance = m2 * voltage + b2;
		}

		// return the distance
		return distance;
	}

}
