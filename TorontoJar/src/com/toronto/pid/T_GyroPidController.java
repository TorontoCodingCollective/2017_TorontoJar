package com.toronto.pid;

import com.toronto.sensors.T_Gyro;

import edu.wpi.first.wpilibj.PIDController;

public class T_GyroPidController extends PIDController {

	private final T_Gyro source;
			
	private double error    = 0.0d;
	private double output   = 0.0d;
	private double integral = 0.0d;
	
	/**
	 * Allocate a PID object with the given constants for P, I, D, using a 50ms period.
	 *
	 * @param p      the proportional coefficient
	 * @param i      the integral coefficient
	 * @param source The PIDSource object that is used to get values
	 * @param output The PIDOutput object that is set to the output percentage
	 */
	public T_GyroPidController(double p, double i, T_Gyro source) {
		super(p, i, 0.0d, 0.0d, source, new T_PidOutput(), kDefaultPeriod);
		this.setInputRange(0.0d, 360.0d);
		this.setContinuous();
		this.source = source;
		integral    = 0.0;
		error       = 0.0d;
	}

	/**
	 * Override the feed forward term to be the standard feed forward value for a position controller.
	 * NOTE:  The FRC code for a feed forward is only used for rate control.
	 *        A feed forward term does not make sense for a gyro PID.
	 */
	protected double calculateFeedForward() { return 0.0d; }

	/**
	 * Set the PID Controller gain parameters. Set the proportional, integral, and differential
	 * coefficients.
	 *
	 * @param p Proportional coefficient
	 * @param i Integral coefficient
	 */
	public synchronized void setPID(double p, double i) {
		setPID(p, i, 0.0d, 0.0d);
	}

	/**
	 * Set the PID Controller gain parameters. Set the proportional, integral, and differential
	 * coefficients.
	 *
	 * @param p Proportional coefficient
	 * @param i Integral coefficient
	 * @param d Differential coefficient
	 */
	public synchronized void setPID(double p, double i, double d) {
		setPID(p, i, d, 0.0d);
	}

	/**
	 * Set the PID Controller gain parameters. Set the proportional, integral, and differential
	 * coefficients.
	 *
	 * @param p Proportional coefficient
	 * @param i Integral coefficient
	 * @param d Differential coefficient
	 * @param f Feed forward coefficient
	 */
	public synchronized void setPID(double p, double i, double d, double f) {
		super.setPID(p, i, 0.0d, 0.0d);
	}
	
	@Override
	public synchronized void disable() {
		super.disable();
		integral = 0.0d;
		error    = 0.0d;
	}
	
	@Override
	public synchronized void enable() {
		super.enable();
		integral = 0.0d;
		error    = 0.0d;
	}
	
	@Override
	public double get() { return output; }
	
	@Override
	public double getError() { return error; }
	
	/** 
	 * This method should be called only once per loop.  The PID calculate 
	 * will set the PID output value which can be returned multiple times using
	 * the get() command.  In the base wpilib code, the calculate routine is called
	 * on another thread and is not exposed externally.  
	 */
	public double calculatePidOutput() {
		
		if (!isEnabled()) { return 0.0d; } 
		
		// Calculate the error
		double setpoint = getSetpoint();
		double angle    = source.pidGet();
		
		error = (setpoint - angle) % 360;
		
		// Normalize the error to +/- 180.
		if (error < -180) { error += 360.0d; }
		if (error > 180)  { error -= 360.0d; }
		
		// Normalize the PID error to +/- 1.0;
		double normalizedError = error / 180.0;
		
		output = normalizedError * getP();
		
		// Clip the proportional output to +/- 1.0
		if (output < -1.0) { output = -1.0; }
		if (output >  1.0) { output = 1.0; }
		
		// Integrate the error if there is a i value
		double i = getI();
		if (i > 0) {
			
			integral += normalizedError;

			// The integral output can often saturate the controller
			// output.  In order to minimize this saturation, calculate
			// the max value for the integral component, and 
			// clip the integration at that point.
			double maxIntegral = (1.0 - Math.abs(output)) / i;

			if (Math.abs(integral) > maxIntegral) {
				integral = Math.signum(integral) * maxIntegral;
			}
			
			output += integral * i;
		}

		return output;
	}

}
