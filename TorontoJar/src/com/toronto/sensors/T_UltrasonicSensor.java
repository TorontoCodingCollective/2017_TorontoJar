package com.toronto.sensors;

import edu.wpi.first.wpilibj.AnalogInput;

/**
 * This class extends the FRC AnalogInput class to add some additional
 * functionality. When returning the angle, the T_ultrasonic always returns a
 * value from 0 to 360 degrees.
 * <p>
 * In order to support PID controls, the getError(angle) method can return the
 * error in degrees from the target angle.
 *
 */

public class T_UltrasonicSensor extends AnalogInput {

	double offset = 0.0;
	boolean inverted = false;
	double v20 = 20;
	double v40 = 40;
	double v80 = 80;
	double m1 = 79.92;
	double b1 = 11.32;
	double m2 = 102.4;
	double b2 = 3.24;

	/**
	 * Encoder
	 * 
	 * @param channel
	 *            The analog channel the ultrasonic is connected to.
	 */
	public T_UltrasonicSensor(int analogInputPort) {
		super(analogInputPort);
	}

	public void calibrate(double v20, double v40, double v80) {
		this.v40 = v40; // difference between the two equations
		this.v20 = v20;
		this.v80 = v80;

		// Calculate m1, b1
		m1 = 0;
		b2 = 0;
		m1 = ((40 - 20) / (this.v40 - this.v20));
		b1 = (20 - (m1 * v20));

		// Calculate m2, b2
		m2 = 0;
		b2 = 0;
		m2 = ((80 - 40) / (this.v80 - this.v40));
		b2 = (80 - (m2 * v80));
	}

	public double getDistance() {

		double distance = 0;

		if (super.getVoltage() <= v40) {
			// Calculate the distance based on m1, b1
			distance = (m1 * super.getVoltage() + b1);
		} else {
			// Calculate the distance based on m2, b2
			distance = (m2 * super.getVoltage() + b2);

		}

		// return the distance
		return distance;
	}

}
